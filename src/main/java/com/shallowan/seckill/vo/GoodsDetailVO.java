package com.shallowan.seckill.vo;

import com.shallowan.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * @author ShallowAn
 */
@Data
public class GoodsDetailVO {

    private int seckillStatus = 0;
    private int remainSeconds = 0;
    private GoodsVO goods;
    private SeckillUser seckillUser;

}
