package com.shallowan.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author ShallowAn
 */
@Data
public class SeckillGoods {
    private Long id;
    private Long goodsId;
    private Double seckillPrice;
    private Integer stockCount;
    private Date startTime;
    private Date endTime;

    @Override
    public String toString() {
        return "SeckillGoods{" +
                "id=" + id +
                ", goodsid=" + goodsId +
                ", stockCount=" + stockCount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
