package com.suho.passwordsave.retrofit.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by suho on 2018/4/3.
 */
public interface CommonService {

    @GET
    Observable<ResponseBody> loadString(@Url String url);

    @GET
    @Streaming
    Observable<ResponseBody> download(@Header("RANGE") String start, @Header("Referer") String referer, @Url String url);

    @GET
    @Streaming
    Observable<ResponseBody> downloadSsl(@Url String url);
}
