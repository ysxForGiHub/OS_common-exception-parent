package com.qsy.os.common.exception.annotation;

import com.qsy.os.common.exception.ExceptionSourceEnum;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ExceptionSource {

    ExceptionSourceEnum value();
}
