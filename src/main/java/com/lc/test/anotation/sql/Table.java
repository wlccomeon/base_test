package com.lc.test.anotation.sql;

import java.lang.annotation.*;

/**
 * Table注解
 * @author wlc
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Table {
	String value();
}
