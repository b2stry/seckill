package com.shallowan.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author ShallowAn
 */
@Data
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createTime;
    private Date payTime;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", deliveryAddrId=" + deliveryAddrId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                ", goodsPrice=" + goodsPrice +
                ", orderChannel=" + orderChannel +
                ", status=" + status +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                '}';
    }
}
