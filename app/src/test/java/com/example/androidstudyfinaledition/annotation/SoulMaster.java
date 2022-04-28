package com.example.androidstudyfinaledition.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/27 下午7:47
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface SoulMaster {
    String name() default "唐三";
    int age() default 24;
}
