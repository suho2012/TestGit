package com.suho.passwordsave.global;

import android.app.Application;
import android.content.Context;

/**
 *
 * @author suho
 * @date 2019/1/21
 */

public class PasswordSaveApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static Context getInstance() {
        return mContext;
    }

}
