package com.suho.passwordsave.global;

/**
 *
 * @author Gwt
 * @date 2018/1/25
 */

public class KeyStore {
    /**用户id*/
    public final static String UserId = "user_id";

    //sp中的key
    public static String SHARED_PREFERENCES_USER_ID = "sp_user_id";
    public static String SHARED_PREFERENCES_TOKEN = "sp_token";
    public static String SHARED_PREFERENCES_HEAD_IMG = "sp_head_img";
    public static String SHARED_PREFERENCES_USER_NICKNAME = "sp_user_nickname";
    public static String SHARED_PREFERENCES_USER_NAME = "sp_user_name";
    public static String SHARED_PREFERENCES_USER_PHONE = "sp_user_phone";
    public static String SHARED_PREFERENCES_USER_REGION = "sp_user_region";
    public static String SHARED_PREFERENCES_USER_ADDRESS = "sp_user_address";
    public static String SHARED_PREFERENCES_USER_IS_BIND_COUURSE = "sp_user_is_bind";
    public static String SHARED_PREFERENCES_USER_TEACHER = "sp_user_teacher";
    public static String SHARED_PREFERENCES_USER_EM_NAEM = "sp_user_huan_xin_name";
    public static String SHARED_PREFERENCES_USER_EM_PWD = "sp_user_huan_xin_pwd";
    public static String SHARE_PREFERENCES_WRITE_SETTINGS = "sp_write_settings";
    public static String SHARE_PREFERENCES_IS_ADMIN = "sp_is_admin";
    public static String SHARE_PREFERENCES_IS_PARTNER = "sp_is_partner";
    /**默认项目ID*/
    public static String SHARED_PREFERENCES_USER_BIND_COUURSE_ID = "sp_user_bind_id";
    public static String SHARED_PREFERENCES_USER_REPORT_COURSE_ID = "sp_user_report_id";
    public static String SHARED_PREFERENCES_PHONE_DEVICE_TOKEN = "sp_user_device_token";

    /**时间增量，可能用不到*/
    public static String SHARED_PREFERENCES_DELTA_T = "sp_time_difference";
    public static String SHARED_PREFERENCES_CURRENT_TIME = "sp_time_difference";
    public static String SHARED_PREFERENCES_WECHAT_UNICODE = "sp_wechat_unicode";
    /**购买相关的参数*/
    public static String SHARED_PREFERENCES_BUY_TYPE = "sp_buy_type";
    public static String SHARED_PREFERENCES_BUY_INFO = "sp_buy_info";

    public static final String KEY_TOKEN = "cbf5604e94f364ed";
    public static  final String IV_TOKEN = "106d46f29f9ea389";

    public static final String NET_VIDEO_CACHE_ALLOW = "net_video_cache_allow";
    public static final String VIDEO_CACHE_ALLOW = "video_cache_allow";

    /**默认项目*/
    public static String Default_Project = "default_project";
    /**默认项目名称*/
    public static String Default_Project_Name = "default_project_name";

    public static String PAY_TYPE = "pay_type";
    /**记录我的通知红点状态*/
    public static String MY_NOTICE = "my_notice";
    /**记录我的资讯通知红点状态*/
    public static String MY_INFO = "my_info";
    /**记录我的是否显示红点*/
    public static String MY_MINE = "my_mine";
    /**记录是否是加密过的视频*/
    public static String IS_ENTRY_VIDEO = "is_entry_video";

    public static String MY_INFO_CONTENT = "my_info_content";

    public static String LIVE_INTEGRATION_PAGE = "live_integration_page";

    /**TODO:suho,2018-01-08,有关群组聊天的*/
    public static String CHAT_GROUP_MUTE = "chat_group_mute";

}
