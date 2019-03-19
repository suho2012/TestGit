package com.suho.passwordsave.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 *
 * @author peihua
 * @date 2017/11/10
 */

public class PermissionUtil {
    /**
     * 动态申请权限，当检查已经拥有所有权限是返回true
     * @param activity
     * @param permissionList
     * @param requestCode
     * @return
     */
    public static boolean requestPermissions(Activity activity, String[] permissionList, int requestCode ){
        boolean isOwnAll = true;
        for (int i = 0; i < permissionList.length; i++) {
            if (ContextCompat.checkSelfPermission(activity,
                    permissionList[i])
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{permissionList[i]},
                        requestCode);
                isOwnAll = false;
            }else{

            }
        }
        return isOwnAll;
    }
}

