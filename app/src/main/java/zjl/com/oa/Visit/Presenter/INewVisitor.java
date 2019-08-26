package zjl.com.oa.Visit.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Response.LogoutResponse;
import zjl.com.oa.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitor {
    @POST(Constant.Coming)
    Call<LogoutResponse> Coming(@Body RequestBody body);
    @POST(Constant.Form)
    Call<FormResponse> Form(@Body RequestBody body);
}
