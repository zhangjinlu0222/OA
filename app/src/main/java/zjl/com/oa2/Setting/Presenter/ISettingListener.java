package zjl.com.oa2.Setting.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.UserInfoResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ISettingListener extends IBaseListener{
    void onSucceed(String arg);
    void onSucceed(UserInfoResponse.Data result);
}
