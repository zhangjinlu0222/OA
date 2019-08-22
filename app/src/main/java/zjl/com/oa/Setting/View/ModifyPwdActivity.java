package zjl.com.oa.Setting.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dou361.dialogui.DialogUIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.R;
import zjl.com.oa.Setting.Model.SettingPresenterImpl;
import zjl.com.oa.Setting.Presenter.ISettingView;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class ModifyPwdActivity extends BaseActivity implements ISettingView{

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.et_old_pwd)
    EditText etOldPwd;
    @Bind(R.id.et_new_pwd)
    EditText etNewPwd;
    @Bind(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;
    @Bind(R.id.btn_save)
    Button btnSave;
    private SettingPresenterImpl settingPresenter;
    private String token,oldPassword,newPassword,newPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_fix);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        settingPresenter = new SettingPresenterImpl(this);
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
    }

    @OnClick({R.id.ig_back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.btn_save:
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.btn_save)){
                    return;
                }else if (checkData(etOldPwd.getText().toString().trim(),
                        etNewPwd.getText().toString().trim(),
                        etNewPwdAgain.getText().toString().trim())){
                    settingPresenter.ModifyPwd(token,etOldPwd.getText().toString().trim(),etNewPwd.getText().toString().trim());
                }
                break;
        }
    }

    private boolean checkData(String arg,String arg1,String arg2){
        if (arg == null || arg.length() >20 || arg.length() < 6){
            showFailureMsg("旧密码长度为6-20位");
            return false;
        }
        if (arg1 == null || arg1.length() >20 || arg1.length() < 6){
            showFailureMsg("新密码长度应为6-20位");
            return false;
        }
        if (arg2 == null || arg2.length() >20 || arg2.length() < 6){
            showFailureMsg("新密码长度应为6-20位");
            return false;
        }
        if (arg1.equals(arg2)){
            return true;
        }else{
            this.showFailureMsg("两次输入新密码不同，请重新输入");
            return false;
        }
    }

    @Override
    public void showProgressBar() {
        if (submitDialog != null){
            submitDialog.show();
        }
    }

    @Override
    public void hideProgressBar() {
        if (submitDialog != null){
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    @Override
    public void onDestory() {
        settingPresenter.onDestory();
    }
    @Override
    public void refreshToken(String arg) {
        if (arg != null && arg.length() > 0){
            UserInfo.getInstance(context).setUserInfo(UserInfo.TOKEN,arg);
        }else{
            showFailureMsg("获取Token异常");
        }
    }
}
