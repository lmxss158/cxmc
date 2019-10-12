package com.sgai.cxmc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/8/5 14:43
 * @Version 1.0
 */


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuAcls {

    String[] value() default {};

}
