package com.shallowan.seckill.redis;

/**
 * @author ShallowAn
 */
public class OrderKey extends BasePrefix {
    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSeckillOrderByUidGid = new OrderKey("soug");

}
