package com.wz.libnavannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author wangzhen
 * @date 2020/02/01
 */
@Target(ElementType.TYPE)
public @interface ActivityDestination {

    String pageUrl();

    boolean needLogin() default false;

    boolean asStarter() default false;
}
