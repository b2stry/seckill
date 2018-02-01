package com.shallowan.seckill.domain;

import lombok.Data;

/**
 * @author ShallowAn
 */
@Data
public class SeckillOrder {
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;

    @Override
    public String toString() {
        return "SeckillOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                '}';
    }
}
