package com.suho.passwordsave.retrofit.service;

import com.bj12345.incometax.bean.AdvertisingBean;
import com.bj12345.incometax.bean.HttpResultBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 * @author suho
 * @date 2018/1/17
 */

public interface ProjectService {


    @GET("AppWeb-Geshui-EnterAd")
    Observable<HttpResultBean<AdvertisingBean>> getAdavtering(@Query("userid") String userid
            , @Query("deviceToken") String deviceToken);
}
