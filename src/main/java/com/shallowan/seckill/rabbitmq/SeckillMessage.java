package com.shallowan.seckill.rabbitmq;

import com.shallowan.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * @author ShallowAn
 */
@Data
public class SeckillMessage {
    private SeckillUser seckillUser;
    private long goodsId;

    @Override
    public String toString() {
        return "SeckillMessage{" +
                "seckillUser=" + seckillUser +
                ", goodsId=" + goodsId +
                '}';
    }
}
