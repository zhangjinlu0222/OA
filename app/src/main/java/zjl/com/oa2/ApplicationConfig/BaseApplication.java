package zjl.com.oa2.ApplicationConfig;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import cn.sharesdk.framework.ShareSDK;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.Login.View.LoginActivity;
import zjl.com.oa2.QuestAndSetting.View.QuestAndSetting;
import zjl.com.oa2.Utils.AppFrontBackHelper;

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

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        // 默认本地个性化地图初始化方法
        SDKInitializer.initialize(this);

        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }
    public static Context getContext(){
        return context;
    }
}
