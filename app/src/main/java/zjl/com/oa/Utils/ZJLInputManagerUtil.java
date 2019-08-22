package zjl.com.oa.Utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ZJLInputManagerUtil {
    public static boolean hideSoftMethodInputManager (Activity activity){
        if(null != activity.getCurrentFocus()){
            InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        return false;
    }
}
