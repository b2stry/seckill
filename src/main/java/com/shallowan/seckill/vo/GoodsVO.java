package com.shallowan.seckill.vo;

import com.shallowan.seckill.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author ShallowAn
 */
@Data
public class GoodsVO extends Goods {

    private Double seckillPrice;
    private Integer stockCount;
    private Date startTime;
    private Date endTime;

}
