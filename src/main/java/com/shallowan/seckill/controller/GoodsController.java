package com.shallowan.seckill.controller;

import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.redis.GoodsKey;
import com.shallowan.seckill.redis.RedisService;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.GoodsService;
import com.shallowan.seckill.vo.GoodsDetailVO;
import com.shallowan.seckill.vo.GoodsVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShallowAn
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 获取商品列表
     * <p>
     * 5000 * 10
     * 加入缓存前：
     * QPS：1267 load:15 mysql
     * <p>
     * 加入缓存后：
     * QPS：2884 load:5
     *
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */
    @ApiOperation("获取商品列表")
    @GetMapping(value = "/to_list", produces = "text/html")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "SeckillUser")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, SeckillUser user) {

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        model.addAttribute("user", user);
        List<GoodsVO> goodsVOList = goodsService.listGoodsVO();
        model.addAttribute("goodsList", goodsVOList);
//      return "goods_list";


        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }

        return html;
    }

    @ApiOperation("单个商品详情  返回json")
    @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVO> detail(SeckillUser user,
                                        @PathVariable("goodsId") long goodsId) {

        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);

        long startAt = goodsVO.getStartTime().getTime();
        long endAt = goodsVO.getEndTime().getTime();

        Map<String, Integer> map = checkTime(startAt, endAt);

        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setGoods(goodsVO);
        goodsDetailVO.setSeckillUser(user);
        goodsDetailVO.setRemainSeconds(map.get("remainSeconds"));
        goodsDetailVO.setSeckillStatus(map.get("seckillStatus"));

        return Result.success(goodsDetailVO);
    }

    private Map<String, Integer> checkTime(long startAt, long endAt) {
        Map<String, Integer> map = new HashMap<>();

        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) {
            //秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        map.put("seckillStatus", seckillStatus);
        map.put("remainSeconds", remainSeconds);
        return map;

    }


    @ApiOperation("单个商品详情  返回html")
    @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, SeckillUser user,
                         @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        model.addAttribute("goods", goodsVO);

        long startAt = goodsVO.getStartTime().getTime();
        long endAt = goodsVO.getEndTime().getTime();

        Map<String, Integer> map = checkTime(startAt, endAt);

        model.addAttribute("seckillStatus", map.get("seckillStatus"));
        model.addAttribute("remainSeconds", map.get("remainSeconds"));
//      return "goods_detail";


        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }

        return html;
    }
}


