package com.shallowan.seckill.controller;

import com.shallowan.seckill.domain.OrderInfo;
import com.shallowan.seckill.domain.SeckillOrder;
import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.redis.RedisService;
import com.shallowan.seckill.result.CodeMsg;
import com.shallowan.seckill.result.Result;
import com.shallowan.seckill.service.GoodsService;
import com.shallowan.seckill.service.OrderService;
import com.shallowan.seckill.service.SeckillService;
import com.shallowan.seckill.vo.GoodsVO;
import com.shallowan.seckill.vo.OrderDetailVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author ShallowAn
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @ApiOperation("订单详情接口")
    @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "Long")
    @GetMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVO> seckill(SeckillUser seckillUser,
                                         @RequestParam("orderId") long orderId) {
        if (seckillUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = order.getGoodsId();
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setOrder(order);
        orderDetailVO.setGoods(goodsVO);

        return Result.success(orderDetailVO);
    }
}
