package zjl.com.oa2.Login.Presenter;

import zjl.com.oa2.Response.LoginResponse;

/**
 * Created by Administrator on 2018/2/11.
 */

public interface ILoginListener {
    void onNameError(String msg);
    void onPwdError(String msg);
    void onSucceed(LoginResponse.Data data);
    void onFail();
    void onFail(String msg);
}
