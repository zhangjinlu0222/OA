package zjl.com.oa.Login.View;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.Login.Model.LoginPresenterImpl;
import zjl.com.oa.Login.Presenter.ILoginPresenter;
import zjl.com.oa.Login.Presenter.ILoginView;
import zjl.com.oa.QuestAndSetting.View.QuestAndSetting;
import zjl.com.oa.R;
import zjl.com.oa.Utils.TitleBarUtil;
import zjl.com.oa.VersionControl.VersionService;
import zjl.com.oa.VersionControl.VersionUpdateDialog;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;
import static zjl.com.oa.Utils.ZJLInputManagerUtil.hideSoftMethodInputManager;

public class LoginActivity extends BaseActivity implements ILoginView {
    ILoginPresenter loginPresenter;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.login_account)
    EditText loginAccount;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.login_logo)
    ImageView loginLogo;
    @Bind(R.id.project_name)
    TextView projectName;

    private BuildBean dialog;
    private boolean isClickAble;

    public static LoginActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarUtil.setTitleBarHide(this);
        activity = LoginActivity.this;
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(context,this);
        dialog = DialogUIUtils.showLoading(this, "登录中", true, false, false, true);

        upgradeVersion();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (hideSoftMethodInputManager(LoginActivity.this)) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        //禁止重复点击
        if (!netConnected){
            showNetError();
        }else if (isFastDoubleClick(R.id.btn)){
            return;
        }else{
            if (loginPresenter != null){
                loginPresenter.login(getUsername(), getUserPwd());
            }
        }
    }
    @Override
    public String getUsername() {
        return loginAccount.getText().toString().trim();
    }

    @Override
    public String getUserPwd() {
        return loginPassword.getText().toString().trim();
    }

    @Override
    public void showProgressBar() {
        if (dialog != null){
            dialog.show();
        }
    }

    @Override
    public void hideProgressBar() {
        if (dialog != null){
            DialogUIUtils.dismiss(dialog);
        }
    }

    @Override
    public void toMainActivity() {

        this.hideProgressBar();

        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(context, QuestAndSetting.class);
        intent.setComponent(componentName);
        startActivity(intent);
    }

    @Override
    public void showFailureMsg(String msg) {
        this.hideProgressBar();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestoryView() {
        loginPresenter.onDestory();
    }

    @Override
    public void clearData() {
        loginAccount.setText("");
        loginPassword.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearData();
        hideProgressBar();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        UserInfo.getInstance(context).cleanUserInfo();
        //发送广播，关闭所有Activity
        Intent intent = new Intent();
        intent.setAction("zjl.com.oa.Base.baseActivity");
        sendBroadcast(intent);
    }
    private void upgradeVersion() {

        String version = "";

        PackageManager pm = context.getPackageManager();//context为当前Activity上下文
        PackageInfo pi = null;

        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (version.equals("")){
            Toast.makeText(context, "获取版本信息异常", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpParams params = new HttpParams();
        params.put("appType","android");
        params.put("version", version);

        VersionParams.Builder builder = new VersionParams.Builder()
                .setRequestUrl(Constant.BASE_URL + Constant.UpgradeVersion)
                .setRequestParams(params)
                .setRequestMethod(HttpRequestMethod.POSTJSON)
                .setCustomDownloadActivityClass(VersionUpdateDialog.class)
                .setService(VersionService.class);

        AllenChecker.startVersionCheck(this, builder.build());
        activity = this;
    }
}
