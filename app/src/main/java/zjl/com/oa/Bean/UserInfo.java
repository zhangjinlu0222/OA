package zjl.com.oa.Bean;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class UserInfo {

    private static SharedPreferences mSharedPreferences;
    private static UserInfo mPreferenceUtils;
    private static android.content.SharedPreferences.Editor editor;

    public static String TOKEN = "token";
    public static String ROLE_ID = "role_id";
    public static String TRUE_NAME = "true_name";
    public static String DEP_NAME = "dep_name";
    public static String USER_ID = "user_id";
    public static String PHONE = "phone";
    public static String LOGINSTATUS = "loginstatus";
    public static String AUTOUPDATE = "main_list_auto_update";
    public static String OPERATION = "operation_wk_pot_id";
    public static String OPERATIONSTATE = "operation_state";
    public static String SCHEDULEFLAG = "schedule_flag";

    private UserInfo(Context cxt)
    {
        mSharedPreferences = cxt.getSharedPreferences("local_userinfo", 0);
    }

    public static UserInfo getInstance(Context cxt)
    {
        if(mPreferenceUtils == null)
            mPreferenceUtils = new UserInfo(cxt);
        editor = mSharedPreferences.edit();
        return mPreferenceUtils;
    }

    public void setUserInfo(String str_name, String str_value)
    {
        editor.putString(str_name, str_value);
        editor.apply();
    }

    public void setLoginStatus(String str_name, boolean str_value)
    {
        editor.putBoolean(str_name, str_value);
        editor.apply();
    }
    public boolean getLoginStatus(String str_name)
    {
        return mSharedPreferences.getBoolean(str_name, false);
    }
    public void setUserInfo(String str_name, int str_value)
    {
        editor.putInt(str_name, str_value);
        editor.apply();
    }
    public void setUserInfo(String str_name, Set<String> str_value)
    {
        editor.putStringSet(str_name, str_value);
        editor.apply();
    }

    public void setUserInfo(String str_name, boolean str_value)
    {
        editor.putBoolean(str_name, str_value);
        editor.apply();
    }

    public String getUserInfo(String str_name)
    {
        return mSharedPreferences.getString(str_name, "");
    }
    public Set<String> getUserInfoRoleId(String str_name)
    {
        return mSharedPreferences.getStringSet(str_name, new HashSet<>());
    }

    public void cleanUserInfo()
    {
        editor.clear().apply();
    }
}
