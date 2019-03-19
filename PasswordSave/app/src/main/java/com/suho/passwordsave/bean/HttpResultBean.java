package com.suho.passwordsave.bean;

/**
 * Created by suho on 2018/1/16.
 * @author suho
 * # （算是属于retrofi封装的一部分）此类是网络请求的外层公共类，因为我们只关心T的数据， error ,可以进行统一的处理
 */

public class HttpResultBean<T> {

    public String error;
    public String status;
    public T msg;

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public T getMsg() {
        return msg;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
