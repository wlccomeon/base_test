package com.lc.test.anotation.sql;

import java.lang.annotation.*;


/**
 * Column注解
 * @author wlc
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Column {
	String value();
}
