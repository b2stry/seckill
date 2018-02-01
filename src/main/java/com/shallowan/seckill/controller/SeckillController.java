package com.shallowan.seckill.controller;

import com.shallowan.seckill.domain.OrderInfo;
import com.shallowan.seckill.domain.SeckillOrder;
import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.result.CodeMsg;
import com.shallowan.seckill.service.GoodsService;
import com.shallowan.seckill.service.OrderService;
import com.shallowan.seckill.service.SeckillService;
import com.shallowan.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ShallowAn
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
}
