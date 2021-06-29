package com.wintecfinal;

import android.os.Environment;

import java.io.File;

public class Constant {
    public static final String FOLDER_NAME = ".wintec";
    public static final String CACHE_DIR = ".wintec/Cache";

    public static final String TMP_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + FOLDER_NAME + "/tmp";

    public static final String PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "" + FOLDER_NAME;

    public static final String FOLDER_RIDEINN_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "wintec";


    public static final String API_KEY = "123";

    public static final String TEST_MERCHANT_ID = "tEPoOR39624058211083";
    public static final String TEST_MERCHANT_KEY = "9272i5_D!eeezd@1";

    public static final String LIVE_MERCHANT_ID = "gYBadc77373303632943";
    public static final String LIVE_MERCHANT_KEY = "9272i5_D!eeezd@1";

    public static final String FIREBASE_TOKEN = "123";

    public static final String USER_LATITUDE = "lat";
    public static final String USER_LONGITUDE = "longi";

    public static final String LOGIN_INFO = "login_info";
    public static final String SIGNUP_RES = "signup_res";
    public static final String MASTER_DATA = "master_data";
    public static final String COUNTRY = "country";

    public static final String ROLE = "role";
    public static final int ROLE_SELLER = 1;
    public static final int ROLE_BUYER = 2;
    public static final int ROLE_GENERAL = 3;

    public static final String FINISH_ACTIVITY = "finish_activity";

    public static final String PROFILE = "profile";

    public static final int ANDROID_TYPE = 1;

    public static final int TIMEOUT = 5 * 60 * 1000;

    //
    public static final String LOCATION_UPDATED = "location_updated";

    public static final int REQ_CODE_SETTING = 555;

    public static final String TAG_ADD = "add";
    public static final String TAG_CANCEL = "cancel";
    public static final String TAG_REMOVE = "remove";
    public static final String TAG_REJECT = "reject";
    public static final String TAG_BLOCK = "block";
    public static final String TAG_UNBLOCK = "unblock";
    public static final String TAG_ACCEPT = "accept";

    public static final String EVENT = "event";
    public static final String EVENT_CHAT_SEND_MSG = "sendMessage";
    public static final String EVENT_USER_STATUS = "userStatus";
    public static final String EVENT_CHAT_GET_CHAT_MESSAGES = "get_chat_messages";
    public static final String EVENT_CHAT_GET_LIST_CHAT_USER = "get_list_chat_user";
    public static final String EVENT_UPDATE_ONESIGNALID = "update_onesignalid";
    public static final String EVENT_CHAT_GET_CHAT_ID = "get_chat_id";
    public static final String EVENT_CHAT_JOIN_ROOM = "join_room";
    public static final String EVENT_CHAT_ROOM_USER_LEFT = "room_user_left";

    public static final String DATE_FORMAT_SERVER = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_SERVER_ = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String EVENT_USER_UPDATE = "event_user_update";


}
