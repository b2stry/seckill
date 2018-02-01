package com.shallowan.seckill.service;

import com.shallowan.seckill.dao.OrderDao;
import com.shallowan.seckill.domain.OrderInfo;
import com.shallowan.seckill.domain.SeckillOrder;
import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ShallowAn
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, long goodsId) {

        return orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(SeckillUser seckillUser, GoodsVO goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateTime(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(seckillUser.getId());

        long orderId = orderDao.insert(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(orderId);
        seckillOrder.setUserId(seckillUser.getId());
        seckillOrder.setGoodsId(goods.getId());

        orderDao.insertSeckillOrder(seckillOrder);
        return orderInfo;
    }
}
