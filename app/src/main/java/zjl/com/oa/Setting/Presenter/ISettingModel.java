package zjl.com.oa.Setting.Presenter;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ISettingModel {
    void logout(String token,ISettingListener listener);
    void ModifyPwd(String token,String oldPwd,String newPwd,ISettingListener listener);
}
