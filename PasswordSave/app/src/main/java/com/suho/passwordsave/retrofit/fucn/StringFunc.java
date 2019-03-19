package com.suho.passwordsave.retrofit.fucn;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * @author suho
 * @date 2018-1-21
 */
public class StringFunc implements Func1<ResponseBody, String> {
    @Override
    public String call(ResponseBody responseBody) {
        String result = null;
        try {
            result = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
