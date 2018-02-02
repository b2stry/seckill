package com.shallowan.seckill.vo;


import com.shallowan.seckill.domain.OrderInfo;
import lombok.Data;

@Data
public class OrderDetailVO {
    private GoodsVO goods;
    private OrderInfo order;
}
