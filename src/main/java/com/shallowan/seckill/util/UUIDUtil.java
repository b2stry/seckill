package com.shallowan.seckill.util;

import java.util.UUID;

/**
 * @author ShallowAn
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
