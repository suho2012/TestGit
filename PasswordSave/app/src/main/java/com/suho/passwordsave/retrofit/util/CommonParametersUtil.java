package com.suho.passwordsave.retrofit.util;

import com.suho.passwordsave.global.KeyStore;
import com.suho.passwordsave.utils.SharedPrefsUtil;
import com.suho.passwordsave.utils.TimeUtil;
import com.suho.passwordsave.utils.UIUtils;
import com.suho.passwordsave.utils.VersionUtil;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by suho on 2018/1/29.
 * @author suho
 * #公共参数
 */

public class CommonParametersUtil {

    /**
     * 添加公共参数
     * @param oldRequest
     * @return
     */
    public static Request addParam(Request oldRequest) {
        SharedPrefsUtil.putValue(UIUtils.getContext(), KeyStore.SHARED_PREFERENCES_CURRENT_TIME,
                "" + TimeUtil.getCurrentTime());
        HttpUrl.Builder builder = oldRequest.url()
                .newBuilder()
                .setEncodedQueryParameter("devicetype", "android")
                .setEncodedQueryParameter("version",
                        VersionUtil.getVersionName(UIUtils.getContext()))
                .setEncodedQueryParameter("uid",
                        SharedPrefsUtil.getValue(UIUtils.getContext(), KeyStore.SHARED_PREFERENCES_USER_ID,""))
                .setEncodedQueryParameter(" timestamp",
                        SharedPrefsUtil.getValue(UIUtils.getContext(), KeyStore.SHARED_PREFERENCES_CURRENT_TIME,""));

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();

        return newRequest;
    }

    public static Request addSign(Request oldRequest,String sign) {
        HttpUrl.Builder builder = oldRequest.url()
                .newBuilder()
                .setEncodedQueryParameter("sign", sign);

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();

        return newRequest;
    }

}
