package com.skr.mrrdframe.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EventBus 事件注解，用于标识某个方法是 EventBus 方法，并且注明事件
 * 的接收方或发送方。
 *
 * @author hyw
 * @since 2016/12/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Event {
    Class[] from() default {};

    Class[] to() default {};
}
