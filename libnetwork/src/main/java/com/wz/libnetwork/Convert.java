package com.wz.libnetwork;

import java.lang.reflect.Type;

/**
 * @author wangzhen
 * @date 2020/02/02
 */
public interface Convert<T> {
    T convert(String response, Type type);
}
