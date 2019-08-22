package zjl.com.oa.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;

import zjl.com.oa.ApplicationConfig.ActivityManager;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.Login.View.LoginActivity;
import zjl.com.oa.Utils.NetworkUtils;
import zjl.com.oa.Utils.TitleBarUtil;

public class BaseActivity extends AppCompatActivity implements IBaseView{

    public Context context ;
    public boolean netConnected;//false 无网络链接，true 有网络链接

    private myReceiver receiver;
    private NetStateChange netStateChange;
    public BuildBean submitDialog;
    public static final String TAG = "BaseActivity";

    /**上传类型，1代表正常上传，2代表追加上传*/
    public static final String UPLOAD_TYPE_NORMAL = "1";
    public static final String UPLOAD_TYPE_ADD = "2";

    /**上传状态，1代表开始上传，2代表之后上传*/
    public static final String request_start_flag = "1";
    public static final String request_end_flag = "2";


    /*
     * false,clear and upload
     * true, add and upload
     */
    public boolean uploadType = false;
    /*
     * step into activity type,bohui or "";
     */
    public String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        netConnected = NetworkUtils.isNetworkConnected(context);

        /**
         * TitleBar 设置格式*/
        TitleBarUtil.setTitleBarColor(this);

        submitDialog = DialogUIUtils.showLoading(this,"提交中",true,false,false,true);

        receiver = new myReceiver();
        IntentFilter intentFilter = new IntentFilter("zjl.com.oa.Base.baseActivity");
        registerReceiver(receiver, intentFilter);

        netStateChange = new NetStateChange();
        IntentFilter intentFilter2 = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netStateChange, intentFilter2);

        ActivityManager.getsInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        netConnected = NetworkUtils.isNetworkConnected(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityManager.getsInstance().removeActivity(this);

        unregisterReceiver(receiver);
        unregisterReceiver(netStateChange);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void relogin() {
        Toast.makeText(context, Constant.Action_Login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void toMainActivity() {
        setResult(RESULT_OK);
        this.finish();
    }

    public void saveOperation(String wk_pot_id) {
        if (wk_pot_id != null && wk_pot_id.length() > 0){
            UserInfo.getInstance(context).setUserInfo(UserInfo.OPERATION, wk_pot_id);
        }
    }

    @Override
    public void saveOperationState(String state){
        if (state != null && state.length() > 0){
            UserInfo.getInstance(context).setUserInfo(UserInfo.OPERATIONSTATE, state);
        }
    }

    @Override
    public void showFailureMsg(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isUploadTypeAdd() {
        if ("bohui".equals(type) && uploadType){
            return true;
        }
        return false;
    }

    public class myReceiver extends BroadcastReceiver {

        public void onReceive(Context arg0, Intent intent) {
            //接收发送过来的广播内容
                finish();//销毁BaseActivity
        }
    }

    public class NetStateChange extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // 如果相等的话就说明网络状态发生了变化
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                int netWorkState = NetworkUtils.getNetWorkState(context);
                if (netWorkState == -1){
                    netConnected = false;
                }else if (netWorkState == 0 || netWorkState == 1){
                    netConnected = true;
                }
            }
        }
    }

    public void showNetError(){
        this.showFailureMsg("认真的讲，先连上网络吧~");
    }


}
