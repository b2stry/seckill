package com.shallowan.seckill.access;

import com.shallowan.seckill.domain.SeckillUser;

/**
 * @author ShallowAn
 */
public class UserContext {
    private static ThreadLocal<SeckillUser> userHolder = new ThreadLocal<>();

    public static void setUser(SeckillUser user) {
        userHolder.set(user);
    }

    public static SeckillUser getUser() {
        return userHolder.get();
    }
}
