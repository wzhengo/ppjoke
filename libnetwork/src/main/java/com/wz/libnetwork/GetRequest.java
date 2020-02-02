package com.wz.libnetwork;

/**
 * @author wangzhen
 * @date 2020/02/01
 */
public class GetRequest<T> extends Request<T, GetRequest> {

    public GetRequest(String url) {
        super(url);
    }

    @Override
    protected okhttp3.Request generateRequest(okhttp3.Request.Builder builder) {
        return builder.get().url(UrlCreator.createUrlFromParams(mUrl, params)).build();
    }
}
