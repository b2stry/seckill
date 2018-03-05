package com.shallowan.seckill.controller;

import com.shallowan.seckill.access.AccessLimit;
import com.shallowan.seckill.domain.OrderInfo;
import com.shallowan.seckill.domain.SeckillOrder;
import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.rabbitmq.MQSender;
import com.shallowan.seckill.rabbitmq.SeckillMessage;
import com.shallowan.seckill.redis.*;
import com.shallowan.seckill.result.CodeMsg;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.GoodsService;
import com.shallowan.seckill.service.OrderService;
import com.shallowan.seckill.service.SeckillService;
import com.shallowan.seckill.util.MD5Util;
import com.shallowan.seckill.util.UUIDUtil;
import com.shallowan.seckill.validator.NeedLogin;
import com.shallowan.seckill.vo.GoodsVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShallowAn
 * <p>
 * 减少数据库访问
 * 思路：
 * 1.系统初始化，把商品库存数量加载到Redis
 * 2.收到请求，Redis预减库存，库存不足，直接返回，否则进入3
 * 3.请求入队，立即返回排队中
 * 4.请求出队，生成订单，减少库存
 * 5.客户端轮询，是否秒杀成功
 * <p>
 * 秒杀接口地址隐藏
 * 秒杀开始之前，先去请求接口获取秒杀地址
 * 思路：
 * 1.接口改造，带上PathVariable参数
 * 2.添加生成地址的接口
 * 3.秒杀收到请求，先验证PathVariable
 * <p>
 * 数学公式验证码
 * 点击秒杀之后，先输入验证码，分散用户请求
 * 思路：
 * 1.添加生成验证码的接口
 * 2.在获取秒杀路径的时候，验证验证码
 * 3.ScriptEngine使用
 * 接口防刷
 * 对接口做限流
 * 思路：
 * 用拦截器减少对业务侵入
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOList = goodsService.listGoodsVO();
        if (goodsVOList == null) {
            return;
        }

        for (GoodsVO goodsVO : goodsVOList) {
            redisService.set(GoodsKey.getSeckillGoodsStock, "" + goodsVO.getId(), goodsVO.getStockCount());
            localOverMap.put(goodsVO.getId(), false);
        }
    }

    @ApiOperation("db 数据重置接口")
    @GetMapping("/reset")
    @ResponseBody
    public Result<Boolean> reset() {
        List<GoodsVO> goodsVOList = goodsService.listGoodsVO();
        for (GoodsVO goodsVO : goodsVOList) {
            goodsVO.setStockCount(10);
            redisService.set(GoodsKey.getSeckillGoodsStock, "" + goodsVO.getId(), 10);
            localOverMap.put(goodsVO.getId(), false);
        }
        redisService.delete(OrderKey.getSeckillOrderByUidGid);
        redisService.delete(SeckillKey.isGoodsOver);
        seckillService.reset(goodsVOList);
        return Result.success(true);
    }

//    @PostMapping("/do_seckill")
//    public String seckill(Model model, SeckillUser seckillUser,
//                          @RequestParam("goodsId") long goodsId) {
//        model.addAttribute("user", seckillUser);
//        if (seckillUser == null) {
//            return "login";
//        }
//
//        //判断库存
//        GoodsVO goods = goodsService.getGoodsVOById(goodsId);
//        int stock = goods.getStockCount();
//        if (stock < 1) {
//            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
//            return "seckill_fail";
//        }
//
//        //判断是否已经秒杀到了
//        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(seckillUser.getId(), goodsId);
//        if (order != null) {
//            model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL.getMsg());
//            return "seckill_fail";
//        }
//
//        //减库存 下订单 写入秒杀订单
//        OrderInfo orderInfo = seckillService.seckill(seckillUser, goods);
//        model.addAttribute("orderInfo", orderInfo);
//        model.addAttribute("goods", goods);
//        return "order_detail";
//
//    }

    @ApiOperation("秒杀接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "path", value = "秒杀个人路径", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping("/{path}/seckill")
    @ResponseBody
    @AccessLimit(seconds = 5, maxCount = 5)
    public Result<Integer> seckill(SeckillUser seckillUser,
                                   @RequestParam("goodsId") long goodsId,
                                   @PathVariable("path") String path) {

        //验证path
        boolean check = seckillService.checkPath(seckillUser, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //预减库存
        long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //判断是否已经秒杀到了
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(seckillUser.getId(), goodsId);
        if (seckillOrder != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }

        //入队
        SeckillMessage message = new SeckillMessage();
        message.setSeckillUser(seckillUser);
        message.setGoodsId(goodsId);
        sender.sendSeckillMessage(message);

        //排队中
        return Result.success(0);
    }

    @ApiOperation("秒杀路径获取接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "Integer")
    })
    @GetMapping("/path")
    @ResponseBody
    @AccessLimit(seconds = 5, maxCount = 5)
    public Result<String> getSeckillPath(HttpServletRequest request, SeckillUser seckillUser,
                                         @RequestParam("goodsId") long goodsId,
                                         @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {

        boolean check = seckillService.checkVerifyCode(seckillUser, goodsId, verifyCode);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = seckillService.createSeckillPath(seckillUser, goodsId);
        return Result.success(path);
    }


    /**
     * orderId:成功
     * -1:秒杀失败
     * 0:排队中
     *
     * @param seckillUser
     * @param goodsId
     * @return
     */
    @ApiOperation("轮询秒杀是否成功接口")
    @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, dataType = "Long")
    @GetMapping("/result")
    @ResponseBody
    @AccessLimit(seconds = 5, maxCount = 5)
    public Result<Long> result(SeckillUser seckillUser,
                               @RequestParam("goodsId") long goodsId) {
        long result = seckillService.getSeckillResult(seckillUser.getId(), goodsId);
        return Result.success(result);
    }

    @ApiOperation("获取验证码接口")
    @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, dataType = "Long")
    @GetMapping("/verifyCode")
    @ResponseBody
    @AccessLimit(seconds = 5, maxCount = 5)
    public Result<String> getVerifyCode(HttpServletResponse response, SeckillUser seckillUser,
                                        @RequestParam("goodsId") long goodsId) {
        response.setContentType("application/json;charset=UTF-8");
        BufferedImage image = seckillService.createVerifyCode(seckillUser, goodsId);
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
    }
}
