package zjl.com.oa.ApplicationConfig;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.sharesdk.framework.ShareSDK;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.Login.View.LoginActivity;
import zjl.com.oa.QuestAndSetting.View.QuestAndSetting;
import zjl.com.oa.Utils.AppFrontBackHelper;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Administrator on 2018/3/1.
 */

public class BaseApplication extends Application {
    public static Context context;
    private static final String TAG = "BaseApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                Log.e(TAG,"backToFront");

//                如果是从后台拉起来的，则进入首页
                boolean flag = UserInfo.getInstance(context).getLoginStatus(UserInfo.LOGINSTATUS);

                if (flag && ActivityManager.getsInstance().currentActivity().getClass().equals(LoginActivity.class)){
                    Intent intent = new Intent(context, QuestAndSetting.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                 Log.e(TAG,"frontToBack");

            }
        });

    }
    public static Context getContext(){
        return context;
    }
}
