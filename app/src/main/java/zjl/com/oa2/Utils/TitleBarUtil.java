package zjl.com.oa2.Utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/2/10.
 */

public class TitleBarUtil {
    //设置通知栏背景
    public static  void setTitleBarColor(Activity activity)
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();

//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(Color.rgb(88,190,164));

            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    //设置通知栏背景
    public static  void setTitleBarColor(Activity activity,int color)
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();

//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(color);

            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    public static  void setTitleBarHide(Activity activity)
    {
        //全屏设置，隐藏窗口所有装饰
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
