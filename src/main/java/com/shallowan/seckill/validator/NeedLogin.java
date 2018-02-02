package com.shallowan.seckill.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ShallowAn
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface NeedLogin {
}
