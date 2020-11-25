package zjl.com.oa2.Setting.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.UserInfoResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ISettingView extends IBaseView{
    void showProgressBar();
    void hideProgressBar();
    void onDestory();
    void refreshToken(String arg);
    void saveUserInfo(UserInfoResponse.Data data);
}
