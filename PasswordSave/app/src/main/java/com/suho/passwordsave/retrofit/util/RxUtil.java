package com.suho.passwordsave.retrofit.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * @author suho
 * @date 2017/12/11
 * # 利用RxJava进行线程变换操作，用来保持链式编程，
 */
public class RxUtil {

    /**
     * 普通的线程变换操作，网络请求在io线程，数据返回结果在主线程
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T,T> defaultSchedulers() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };

    }

    /**
     * io文件线程操作的变化
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T,T> allIo() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
            }
        };

    }

}
