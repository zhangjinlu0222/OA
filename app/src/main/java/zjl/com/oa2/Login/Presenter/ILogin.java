package zjl.com.oa2.Login.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Response.LoginResponse;

/**
 * Created by Administrator on 2018/2/11.
 */

public interface ILogin {
    @POST(Constant.Login)
    Call<LoginResponse> post(@Body RequestBody body);
    @POST(Constant.Login)
    Call<LoginResponse> UpgradeVersion(@Body RequestBody body);
}
