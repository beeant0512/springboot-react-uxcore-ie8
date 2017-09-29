package com.xstudio.aop.dbchange;

import java.lang.annotation.*;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/29
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DBChangeLog {

    /**
     * 表名
     * @return String
     */
    String table();
}
