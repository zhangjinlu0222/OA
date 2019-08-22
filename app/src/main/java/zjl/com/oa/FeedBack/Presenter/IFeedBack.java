package zjl.com.oa.FeedBack.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.FeedBackResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IFeedBack {

    @POST(Constant.LookFeedbackResult)
    Call<FeedBackResponse> LookFeedbackResult(@Body RequestBody body);
    @POST(Constant.FeedbackResult)
    Call<ResponseWithNoData> FeedbackResult(@Body RequestBody body);

    @POST(Constant.LookFirstFeedbackResult)
    Call<FeedBackResponse> LookFirstFeedbackResult(@Body RequestBody body);
    @POST(Constant.FirstFeedbackResult)
    Call<ResponseWithNoData> FirstFeedbackResult(@Body RequestBody body);
}
