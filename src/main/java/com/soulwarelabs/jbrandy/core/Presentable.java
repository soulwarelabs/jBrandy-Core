package com.soulwarelabs.jbrandy.core;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// created 15.11.2014, v1.0.0, gubarev

@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, TYPE})
public @interface Presentable {

    String value() default "";
}
