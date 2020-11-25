package zjl.com.oa2.Evaluation.Presenter;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IEvaluation {
    @POST(Constant.Assess)
    @Multipart
    Call<ResponseWithNoData> post(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
              @Part("car_year") RequestBody car_year,
              @Part("car_type") RequestBody car_type,
              @Part("car_style") RequestBody car_style,
              @Part("remark") RequestBody remark,
              @Part("workflow_content_id") RequestBody workflow_content_id,
              @Part("wk_point_id") RequestBody wk_point_id,
              @Part List<MultipartBody.Part> files);
    @POST(Constant.PleDgeAssessTwo)
    @Multipart
    Call<ResponseWithNoData> postPledgeAssess(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
              @Part("car_year") RequestBody car_year,
              @Part("car_type") RequestBody car_type,
              @Part("car_style") RequestBody car_style,
              @Part("milage") RequestBody milage,
              @Part("remark") RequestBody remark,
              @Part("market_amount") RequestBody market_amount,
              @Part("take_amount") RequestBody take_amount,
              @Part("workflow_content_id") RequestBody workflow_content_id,
              @Part("wk_point_id") RequestBody wk_point_id,
              @Part List<MultipartBody.Part> files);

    @POST(Constant.Form)
    Call<FormResponse> Form(@Body RequestBody body);
}
