package zjl.com.oa2.Setting.Presenter;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ISettingPresenter {
    void logout(String token);
    void getUserInfo(String token);
    void ModifyPwd(String token,String oldPwd,String newPwd);
    void onDestory();
}
