package com.suho.passwordsave.retrofit.subscriber;

import android.text.TextUtils;
import com.suho.passwordsave.bean.HttpResultBean;
import com.suho.passwordsave.utils.MLog;

import rx.Subscriber;

/**
 *
 * @author suho
 * @date 2018/1/23
 */

public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResultBean<T>> {
    public final String STATUS_SUCCESS = "1";
    private final String TAG = "HttpResultSubscriber";
    private final String STATUS_IDENTIFICATION = "8";
    private String errorCode = "1";

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        MLog.e("" + e.toString() +"\nerrorCode= "+ errorCode + "\nmessage=" + e.getMessage());
        if (TextUtils.equals(STATUS_IDENTIFICATION,errorCode)) {
            //认证失败的时候，直接跳转到登录界面，
//            ActivityUtils.getInstance().finishAll();
//            Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
//            intent.setFlags(FLAG_ACTIVITY_NEW_TASK );
//            UIUtils.getContext().startActivity(intent);
        }
        /*ActivityUtils.getInstance().finishAll();
        Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK );
        UIUtils.getContext().startActivity(intent);*/
        onMistake(e);
    }

    @Override
    public void onNext(HttpResultBean<T> tHttpResultBean) {
        if (STATUS_SUCCESS.equals(tHttpResultBean.getStatus())) {
            onSuccess(tHttpResultBean.getMsg());
        } else {
            errorCode = tHttpResultBean.getStatus();
            if (TextUtils.equals(STATUS_IDENTIFICATION,errorCode)) {
                //认证失败的时候，直接跳转到登录界面，
//                ActivityUtils.getInstance().finishAll();
//                Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK );
//                UIUtils.getContext().startActivity(intent);
            }
            MLog.e(TAG, tHttpResultBean.getError());
            onFailure(tHttpResultBean.getStatus(),tHttpResultBean.getError());
//            onFailure(tHttpResultBean.getError());
//             TODO:suho,2018-01-16,统一对异常进行处理，
//            onMistake(new Throwable("error=" + tHttpResultBean.getError()));
        }
    }

    public abstract void onSuccess(T t);

    public abstract void onMistake(Throwable e);

    public abstract void onFailure(String status,String msg);

//    public abstract void onFailure(String msg);
}
