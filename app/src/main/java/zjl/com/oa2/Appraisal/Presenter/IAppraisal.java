package zjl.com.oa2.Appraisal.Presenter;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.AppraisalResponse;

public interface IAppraisal {

    @POST(Constant.QueryCarAssess)
    Call<AppraisalResponse> QueryCarAssess(@Body RequestBody body);
}
