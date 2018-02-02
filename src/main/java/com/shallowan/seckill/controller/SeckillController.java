package com.shallowan.seckill.controller;

import com.shallowan.seckill.domain.OrderInfo;
import com.shallowan.seckill.domain.SeckillOrder;
import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.exception.GlobalException;
import com.shallowan.seckill.result.CodeMsg;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.GoodsService;
import com.shallowan.seckill.service.OrderService;
import com.shallowan.seckill.service.SeckillService;
import com.shallowan.seckill.validator.NeedLogin;
import com.shallowan.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ShallowAn
 * <p>
 * 减少数据库访问思路：
 * 1.系统初始化，把商品库存数量加载到Redis
 * 2.收到请求，Redis预减库存，库存不足，直接返回，否则进入3
 * 3.请求入队，立即返回排队中
 * 4.请求出队，生成订单，减少库存
 * 5.客户端轮询，是否秒杀成功
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/do_seckill")
    public String seckill(Model model, SeckillUser seckillUser,
                          @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", seckillUser);
        if (seckillUser == null) {
            return "login";
        }

        //判断库存
        GoodsVO goods = goodsService.getGoodsVOById(goodsId);
        int stock = goods.getStockCount();
        if (stock < 1) {
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill_fail";
        }

        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(seckillUser.getId(), goodsId);
        if (order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL.getMsg());
            return "seckill_fail";
        }

        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = seckillService.seckill(seckillUser, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";

    }

    @PostMapping("/seckill")
    @ResponseBody
    @NeedLogin
    public Result<OrderInfo> seckill(SeckillUser seckillUser,
                                     @RequestParam("goodsId") long goodsId) {
        System.out.println(seckillUser);
        //判断库存
        GoodsVO goods = goodsService.getGoodsVOById(goodsId);
        int stock = goods.getStockCount();
        if (stock < 1) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(seckillUser.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }

        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = seckillService.seckill(seckillUser, goods);
        return Result.success(orderInfo);
    }
}
