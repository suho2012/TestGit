package com.suho.passwordsave.utils;

import com.suho.passwordsave.global.KeyStore;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by suho on 2018/1/29.
 * @author suho
 * # 主要用来生成sign,
 */

public class TokenUtil {


    /**
     * 创建sign
     * @param url
     * @return
     */
    public static String createSign(String url) {
        //获取当前系统时间
        //test.jinpeiwang.cn/allapi-User-edituser&&uid=60&&timestamp=12345678&&token=74378975987
        String spliteUrl = "";
        MLog.e("url=" + url);
        if (url.contains("http") || url.contains("https")) {
            if (url.startsWith("https")) {
                spliteUrl = url.substring("https://".length(),url.indexOf("?"));
            }else if (url.startsWith("http")) {
                spliteUrl = url.substring("http://".length(),url.indexOf("?"));
            }
            MLog.e("spliteUrl=" + spliteUrl);
            spliteUrl = spliteUrl
                    + "&uid=" + SharedPrefsUtil.getValue(UIUtils.getContext(), KeyStore.SHARED_PREFERENCES_USER_ID,"")
                    + "&timestamp=" + SharedPrefsUtil.getValue(UIUtils.getContext(),KeyStore.SHARED_PREFERENCES_CURRENT_TIME,"")
                    + "&token=" + SharedPrefsUtil.getValue(UIUtils.getContext(),KeyStore.SHARED_PREFERENCES_TOKEN,"");
        }
        MLog.e("uid=" + SharedPrefsUtil.getValue(UIUtils.getContext(), KeyStore.SHARED_PREFERENCES_USER_ID,"")
                + "\ntoken = " + SharedPrefsUtil.getValue(UIUtils.getContext(),KeyStore.SHARED_PREFERENCES_TOKEN,"")
                + "\nspliteUrl=" +spliteUrl);
        return "" + createMd5(spliteUrl).substring(6,16);
    }

    /**
     * MD5加密
     * @param oldCode
     * @return
     */
    public static String createMd5(String oldCode) {

        String result = oldCode;
        if(oldCode != null) {
            //or "SHA-1"
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            md.update(oldCode.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            //31位string
            while(result.length() < 32) {
                result = "0" + result;
            }
        }
        return result;
    }

    /**
     * token AES解密
     * @param secretToken
     * @return
     */
    public static String analysisToken(String secretToken) {

        return AESEntryUtil.decrypt(secretToken,KeyStore.KEY_TOKEN,KeyStore.IV_TOKEN);
    }

}
