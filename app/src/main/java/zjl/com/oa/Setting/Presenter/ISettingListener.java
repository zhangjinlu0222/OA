package zjl.com.oa.Setting.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.UserInfoResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ISettingListener extends IBaseListener{
    void onSucceed(String arg);
    void onSucceed(UserInfoResponse.Data result);
}
