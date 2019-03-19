package com.suho.passwordsave.retrofit.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suho.passwordsave.global.Constants;
import com.suho.passwordsave.retrofit.provider.OkHttpProvider;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author suho
 * @date 2018/1/16
 * # 工厂设计模式，创建Service类
 */

public class ServiceFactory {

    private final Gson mGson;
    private OkHttpClient mOkHttpClient;

    private ServiceFactory() {
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        mOkHttpClient = OkHttpProvider.getDefaultOkHttpClient();
    }

    /**利用单例模式，创建这个工厂类*/
    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static ServiceFactory getNoCacheInstance() {
        ServiceFactory factory = SingletonHolder.INSTANCE;
        factory.mOkHttpClient = OkHttpProvider.getOkHttpClient();
        return factory;
    }

    public <S> S createService(Class<S> serviceClass) {
        //利用反射拿到BaseUrl
//        String baseUrl = "";
//        try {
//            Field field = serviceClass.getField("BASE_URL");
//            baseUrl = (String) field.get(serviceClass);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.getMessage();
//            e.printStackTrace();
//        }
        //创建Retrofit，生成代理类
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                //进行OKHttp的自定义设置，可以实现拦截错误日志，进行网络缓存等操作，
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    public <S> S createActiveService(Class<S> serviceClass) {
        //创建Retrofit，生成代理类
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DAY_ACTIVE_URL)
                //进行OKHttp的自定义设置，可以实现拦截错误日志，进行网络缓存等操作，
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

}
