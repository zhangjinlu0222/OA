package zjl.com.oa.BusinessFeedBack.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.BusFirstFeedBackResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IBusFeedBack {

//    @POST(Constant.LookSureAmount)
//    Call<BusFeedBackResponse> LookSureAmount(@Body RequestBody body);
    @POST(Constant.LookFirstSureAmount)
    Call<BusFirstFeedBackResponse> LookFirstSureAmount(@Body RequestBody body);

    @POST(Constant.BusFeedback)
    Call<ResponseWithNoData> BusFeedback(@Body RequestBody body);

    @POST(Constant.BusFirstFeedback)
    Call<ResponseWithNoData> BusFirstFeedback(@Body RequestBody body);

//    @POST(Constant.FirstFeedbackResult)
//    Call<ResponseWithNoData> FirstFeedbackResult(@Body RequestBody body);
}
