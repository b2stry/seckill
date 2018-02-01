package com.shallowan.seckill.service;

import com.shallowan.seckill.dao.GoodsDao;
import com.shallowan.seckill.domain.Goods;
import com.shallowan.seckill.domain.OrderInfo;
import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ShallowAn
 */
@Service
public class SeckillService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderInfo seckill(SeckillUser seckillUser, GoodsVO goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);

        //order_info seckill_order
        return orderService.createOrder(seckillUser, goods);

    }
}
