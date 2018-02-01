package com.shallowan.seckill.redis;

/**
 * @author ShallowAn
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
