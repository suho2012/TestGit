package com.suho.passwordsave.retrofit.provider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.util.Preconditions;
import android.text.TextUtils;
import android.util.Log;

import com.suho.passwordsave.global.Constants;
import com.suho.passwordsave.global.PasswordSaveApplication;
import com.suho.passwordsave.retrofit.util.CommonParametersUtil;
import com.suho.passwordsave.utils.MLog;
import com.suho.passwordsave.utils.NetworkUtil;
import com.suho.passwordsave.utils.SSLHelper;
import com.suho.passwordsave.utils.TokenUtil;
import com.suho.passwordsave.utils.UIUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 *
 * @author suho
 * @date 2018/1/16
 */

public class OkHttpProvider {

    private final static long DEFAULT_CONNECT_TIMEOUT = 10;
    private final static long DEFAULT_WRITE_TIMEOUT = 30;
    private final static long DEFAULT_READ_TIMEOUT = 30;
    private static String TAG = OkHttpProvider.class.getSimpleName();

    public static OkHttpClient getDefaultOkHttpClient() {
        return getOkHttpClient(new CacheControlInterceptor());
    }

    public static OkHttpClient getOkHttpClient() {
        return getOkHttpClient(new FromNetWorkControlInterceptor());
    }

    private static OkHttpClient getOkHttpClient(Interceptor cacheControl) {
        //定制OkHttp
        //                .sslSocketFactory(SSLHelper.getDafaultSSLCerfitivate())
        OkHttpClient.Builder httpClientBuilder =
                null;
        try {
            httpClientBuilder = new OkHttpClient
                    .Builder();
//                    .sslSocketFactory(SSLHelper.getSingleSSLCertifcation(UIUtils.getContext().getAssets().open("JPWServer1.cer")))
//                    .hostnameVerifier(new UnSafeHostNameVerifier());
        } catch (Exception e) {
            //TODO:suho,2018-11-23,如果抛出异常，说明证书不对，需要进行下载证书操作，然后，进行更新证书，操作
            e.printStackTrace();
        }
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
        File httpCacheDirectory = new File(PasswordSaveApplication.getInstance().getCacheDir(), "OkHttpCache");
        httpClientBuilder.cache(new Cache(httpCacheDirectory, 100 * 1024 * 1024));
        //设置拦截器
        httpClientBuilder.addInterceptor(new TokenInterceptor());
        httpClientBuilder.addInterceptor(new UserAgentInterceptor("Android Device"));
        httpClientBuilder.addInterceptor(new LoggingInterceptor());
        httpClientBuilder.addInterceptor(cacheControl);
        httpClientBuilder.addNetworkInterceptor(cacheControl);
        return httpClientBuilder.build();
    }

    public static OkHttpClient getSSLCertificateOkHttpClient() {
        return getSSLOkHttpClient(new CacheControlInterceptor());
    }

    private static OkHttpClient getSSLOkHttpClient(Interceptor cacheControl) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
        File httpCacheDirectory = new File(PasswordSaveApplication.getInstance().getCacheDir(), "OkHttpCache");
        httpClientBuilder.cache(new Cache(httpCacheDirectory, 100 * 1024 * 1024));
        //设置拦截器
        httpClientBuilder.addInterceptor(new TokenInterceptor());
        httpClientBuilder.addInterceptor(new UserAgentInterceptor("Android Device"));
        httpClientBuilder.addInterceptor(new LoggingInterceptor());
        httpClientBuilder.addInterceptor(cacheControl);
        httpClientBuilder.addNetworkInterceptor(cacheControl);
        return httpClientBuilder.build();
    }

    /**
     * 没有网络的情况下就从缓存中取
     * 有网络的情况则从网络获取
     */
    private static class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isConnected(PasswordSaveApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (NetworkUtil.isConnected(PasswordSaveApplication.getInstance())) {
                //默认缓存两个小时
                int maxAge = 60 * 60 * 2;
                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=" + maxAge;
                }
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", cacheControl)
                        .build();

            } else {
                int maxStale = 60 * 60 * 24 * 30;
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    /**
     * 强制从网络获取数据
     */
    private static class FromNetWorkControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();

            Response response = chain.proceed(request);

            return response;
        }
    }


    private static class UserAgentInterceptor implements Interceptor {
        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        @SuppressLint("RestrictedApi")
        UserAgentInterceptor(String userAgentHeaderValue) {
            this.userAgentHeaderValue = Preconditions.checkNotNull(userAgentHeaderValue, "userAgentHeaderValue = null");
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request originalRequest = chain.request();
            final Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    private static class LoggingInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//
//            long t1 = System.nanoTime();
//            MLog.e(TAG, String.format(Locale.CHINA, "Sending request %s on %s%n%s",
//                    request.url(), chain.connection(), request.headers()));
//
//            Response response = chain.proceed(request);
//
//            long t2 = System.nanoTime();
//            MLog.e(TAG, String.format(Locale.CHINA, "Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//
//            return response;
//        }

        final static String TAG = "httplog";
        private CharSequence STATUS_IDENTIFICATION = "8";

        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间

            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    Log.d(TAG,String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
                            request.url(), chain.connection(), request.headers(), sb.toString()));
                }
            } else {
                Log.d(TAG,String.format("发送请求 %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
            }
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d(TAG,
                    String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6d,
                            response.headers()
                    ));
            try {
                if (responseBody.string() != null & !TextUtils.isEmpty(responseBody.string())) {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    String status = (String) jsonObject.get("status");
                    if (TextUtils.equals(STATUS_IDENTIFICATION,status)) {
                        //认证失败的时候，直接跳转到登录界面，
//                        ActivityUtils.getInstance().finishAll();
//                        Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
//                        UIUtils.getContext().startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    /**
     * API鉴权interceptor
     */

    private static class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //测试用，一下几行可删除
            //保存UID 、token
//            SharedPrefsUtil.putValue(UIUtils.getContext(), KeyStore.SHARED_PREFERENCES_USER_ID,"");
            //需要拼接sign,还可以添加固定的参数，区分post和get请求
            Request request = chain.request();
            //先添加公共参数
            Request  addRequest = CommonParametersUtil.addParam(request);
            //取出请求的URL,生成sign
            String url = addRequest.url().toString();
            //把sign在添加到参数里面，生成一个新的请求，然后继续请求
            Request signRequest = CommonParametersUtil.addSign(addRequest, TokenUtil.createSign(url));
            return chain.proceed(signRequest);
        }
    }

    /**
     * 是否开启https认证，或者是说 安全访问还是非安全访问，根据自己的情况判断
     */
    private static class UnSafeHostNameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            //可以根据条件进行判断哪些需要开启；
            MLog.e(TAG,"HOST NAME = " + hostname);
            if (Constants.BASE_URL.startsWith("https")) {
                return true;
            }else {
                return false;
            }
        }
    }

}
