package zjl.com.oa2.Setting.Model;

import zjl.com.oa2.Response.UserInfoResponse;
import zjl.com.oa2.Setting.Presenter.ISettingModel;
import zjl.com.oa2.Setting.Presenter.ISettingPresenter;
import zjl.com.oa2.Setting.Presenter.ISettingListener;
import zjl.com.oa2.Setting.Presenter.ISettingView;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SettingPresenterImpl implements ISettingPresenter,ISettingListener {
    private ISettingModel iSettingModel;
    private ISettingView iSettingView;

    public SettingPresenterImpl(ISettingView ISettingView) {
        this.iSettingModel = new SettingModelImpl();
        this.iSettingView = ISettingView;
    }

    @Override
    public void logout(String token) {
        if (iSettingModel != null){
            iSettingModel.logout(token,this);
        }
    }
    @Override
    public void getUserInfo(String token) {
        if (iSettingModel != null){
            iSettingModel.getUserInfo(token,this);
        }
    }

    @Override
    public void ModifyPwd(String token, String oldPwd, String newPwd) {

        if ("".equals(oldPwd)){
            onFail("请输入旧密码");
            return;
        }
        if ("".equals(newPwd)){
            onFail("请输入新密码");
            return;
        }
        if (oldPwd.equals(newPwd)){
            onFail("新密码不能与旧密码相同");
            return;
        }

        if (iSettingModel != null){
            iSettingModel.ModifyPwd(token,oldPwd,newPwd,this);
        }
        if (iSettingView != null){
            iSettingView.showProgressBar();
        }
    }

    @Override
    public void onDestory() {
        if (iSettingView != null){
            iSettingView = null;
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed() {
        if (iSettingView != null){
            iSettingView.toMainActivity();
        }
        if (iSettingView != null){
            iSettingView.hideProgressBar();
        }
    }
    @Override
    public void onSucceed(String arg) {
        if (iSettingView != null){
            iSettingView.refreshToken(arg);
            iSettingView.toMainActivity();
        }
        if (iSettingView != null){
            iSettingView.hideProgressBar();
        }
    }

    @Override
    public void onSucceed(UserInfoResponse.Data data){
        if (iSettingView != null){
            iSettingView.saveUserInfo(data);
            iSettingView.hideProgressBar();
        }
    }

    @Override
    public void onFail(String msg) {

        if (iSettingView != null){
            iSettingView.showFailureMsg(msg);
        }
        if (iSettingView != null){
            iSettingView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {
        if (iSettingView != null){
            iSettingView.relogin();
        }
        if (iSettingView != null){
            iSettingView.hideProgressBar();
        }
    }
}
