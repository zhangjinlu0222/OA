package zjl.com.oa2.Setting.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Response.LogoutResponse;
import zjl.com.oa2.Response.ModifyPwdResponse;
import zjl.com.oa2.Response.UserInfoResponse;

/**
 * Created by Administrator on 2018/2/11.
 */

public interface ISetting {
    @POST(Constant.LoginOut)
    Call<LogoutResponse> post(@Body RequestBody token);
    @POST(Constant.ModifyPwd)
    Call<ModifyPwdResponse> ModifyPwd(@Body RequestBody token);
    @POST(Constant.GetUserInfo)
    Call<UserInfoResponse> GetUserInfo(@Body RequestBody token);
}
