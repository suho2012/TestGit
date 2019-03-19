package com.suho.passwordsave.retrofit.fucn;




import com.suho.passwordsave.bean.HttpResultBean;

import rx.functions.Func1;

/**
 * @author suho
 * @date 2018-1-21
 */
public class ResultFunc<T> implements Func1<String, HttpResultBean<T>> {
    @Override
    public HttpResultBean<T> call(String s) {
        return null;
    }
   /* @Override
    public HttpResultBean<T> call(String result) {
        JsonConvert<HttpResultBean<T>> convert = new JsonConvert<HttpResultBean<T>>() {
        };
        return convert.parseData(result);
    }*/
}
