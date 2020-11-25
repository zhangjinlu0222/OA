package zjl.com.oa2.ApplicationConfig;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

public class ActivityManager {
    private static final String TAG = "ActivityManager";
    public static Stack<Activity> sActivityStack;
    public static ActivityManager sInstance;

    public ActivityManager() {
    }

    public static ActivityManager getsInstance(){
        if (sInstance == null){
            sInstance = new ActivityManager();
        }
        return sInstance;
    }

    /**
     * add Activity to Stack
     */
    public static void addActivity(Activity activity){

        if (sActivityStack == null){
            sActivityStack = new Stack<Activity>();
        }
        sActivityStack.add(activity);
    }

    /**
     * remove Activity from Stack
     */
    public static void removeActivity(Activity activity){
        if (sActivityStack == null){
            return;
        }
        sActivityStack.remove(activity);
    }

    /**
     * current Activity from Stack
     */
    public static Activity currentActivity(){
        if (sActivityStack == null){
            return null;
        }
        return sActivityStack.lastElement();
    }

    /*
    * finish current Activity from Stack
    */

    public static void finishActivity(){
        if (sActivityStack != null){
            Activity activity = sActivityStack.lastElement();
            finishActivity(activity);
        }
    }
    /*
    * finish Activity from stack with activity
    */
    public static void finishActivity(Activity activity) {
        if (activity != null){
            sActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    * finish Activity from stack with class name
    */
    public static void finishActivity(Class<?> cls) {
        if (sActivityStack != null){
            for (Activity activity:sActivityStack){
                if (activity.getClass().equals(cls)){
                    finishActivity(activity);
                }
            }
        }
    }

    /*
    * finish  all activity
    */
    public static void finishAllActivity() {
        if (sActivityStack != null){
            for (Activity activity:sActivityStack){
                activity.finish();
            }
            sActivityStack.clear();
        }
    }

    /*
    * exit app
    */
    public static void exit(Context ctx){
        exit(ctx,true);
    }

    /*
    * exit app with options
    */
    public static void exit(Context ctx, boolean isClearAble){
        try {
            finishAllActivity();

            if (ctx != null){

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
