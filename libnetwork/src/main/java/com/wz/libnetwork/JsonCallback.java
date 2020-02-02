package com.wz.libnetwork;

/**
 * @author wangzhen
 * @date 2020/02/01
 */
public abstract class JsonCallback<T> {

    public void onSuccess(ApiResponse<T> response) {

    }

    public void onError(ApiResponse<T> response) {

    }

    public void onCacheSuccess(ApiResponse<T> response) {

    }
}
