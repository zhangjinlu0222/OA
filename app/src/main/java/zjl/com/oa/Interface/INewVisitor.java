package zjl.com.oa.Interface;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Response.LogoutResponse;
import zjl.com.oa.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitor {
    @POST(Constant.Coming)
    Call<LogoutResponse> post(@Body RequestBody body);
    @POST(Constant.LookInterview)
    Call<LookInterviewResponse> LookInterview(@Body RequestBody body);
}
