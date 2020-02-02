package com.wz.libnetwork;

/**
 * @author wangzhen
 * @date 2020/02/01
 */
public class ApiResponse<T> {

    public boolean success;
    public int status;
    public String message;
    public T body;
}
