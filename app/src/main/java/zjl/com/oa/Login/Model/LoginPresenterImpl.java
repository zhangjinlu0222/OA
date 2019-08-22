package zjl.com.oa.Login.Model;

import android.content.Context;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.Login.Presenter.ILoginListener;
import zjl.com.oa.Login.Presenter.ILoginModel;
import zjl.com.oa.Login.Presenter.ILoginPresenter;
import zjl.com.oa.Login.Presenter.ILoginView;
import zjl.com.oa.R;
import zjl.com.oa.Response.LoginResponse;

/**
 * Created by Administrator on 2018/2/11.
 */

public class LoginPresenterImpl  implements ILoginPresenter,ILoginListener {
    private ILoginModel loginModel;
    private ILoginView iLoginView;
    private Context context;

    public LoginPresenterImpl(Context ctx,ILoginView iloginView){
        context = ctx;
        this.iLoginView = iloginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void login(String userName,String password) {
        if (userName == null || "".equals(userName)){
            onNameError(context.getString(R.string.pls_input) + context.getString(R.string.account));
            return;
        }
        if (password == null || "".equals(password)){
            onPwdError(context.getString(R.string.pls_input) + context.getString(R.string.password));
            return;
        }

        if (loginModel != null){
            loginModel.login(userName,password,this);
            iLoginView.showProgressBar();
        }

        //清除本地数据
        UserInfo.getInstance(context).cleanUserInfo();
    }

    @Override
    public void onDestory() {
        if (iLoginView != null){
            iLoginView = null;
        }
    }

    @Override
    public void onNameError(String msg) {
        iLoginView.showFailureMsg(msg);
    }

    @Override
    public void onPwdError(String msg) {
        iLoginView.showFailureMsg(msg);
    }

    @Override
    public void onSucceed(LoginResponse.Data data) {

        List<String> rolelist = Arrays.asList(data.getRole_id().trim().split(","));
        Set<String> roleset = new HashSet<>();
        roleset.addAll(rolelist);

        UserInfo.getInstance(context).setUserInfo(UserInfo.TOKEN,data.getToken());
        UserInfo.getInstance(context).setUserInfo(UserInfo.ROLE_ID,roleset);
        UserInfo.getInstance(context).setUserInfo(UserInfo.TRUE_NAME,data.getTrue_name());
        UserInfo.getInstance(context).setUserInfo(UserInfo.PHONE, data.getPhone());
        UserInfo.getInstance(context).setUserInfo(UserInfo.USER_ID,data.getUser_id());
        UserInfo.getInstance(context).setUserInfo(UserInfo.DEP_NAME,data.getDep_name());
        UserInfo.getInstance(context).setLoginStatus(UserInfo.LOGINSTATUS, true);

        UserInfo.getInstance(context).setUserInfo(UserInfo.AUTOUPDATE,"0");
        iLoginView.toMainActivity();
        iLoginView.hideProgressBar();
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onFail(String msg) {
        if (iLoginView != null){
            iLoginView.showFailureMsg(msg);
            iLoginView.hideProgressBar();
        }
    }
}
