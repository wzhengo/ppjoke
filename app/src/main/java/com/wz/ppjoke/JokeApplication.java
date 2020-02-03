package com.wz.ppjoke;

import android.app.Application;

import com.wz.libnetwork.ApiService;

/**
 * @author wangzhen
 * @date 2020/02/03
 */
public class JokeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiService.init("http://123.56.232.18:8080/serverdemo", null);
    }
}
