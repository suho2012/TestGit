package com.suho.passwordsave.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

/**
 *
 * @author dph
 * @date 2017/8/21
 */

public class VersionUtil {

    public static void downloadApk(Context context,String apkUrl){
        if (!TextUtils.isEmpty(apkUrl)&&apkUrl.startsWith("http")){
            Uri uri = Uri.parse(apkUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }else {
            Toast.makeText(context,"下载地址错误",Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 获取版本名称
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        String version = "1.0.0";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }
    /**
     * 获得版本号
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getVersionCode(Context context){
        //获得软件版本号
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionCode;
    }
}
