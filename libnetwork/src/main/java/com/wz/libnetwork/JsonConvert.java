package com.wz.libnetwork;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

/**
 * @author wangzhen
 * @date 2020/02/02
 */
public class JsonConvert implements Convert {

    @Override
    public Object convert(String response, Type type) {
        final JSONObject jsonObject = JSON.parseObject(response);
        final JSONObject data = jsonObject.getJSONObject("data");
        if (data != null) {
            final Object data1 = data.get("data");
            return JSON.parseObject(data1.toString(), type);
        }
        return null;
    }
}
