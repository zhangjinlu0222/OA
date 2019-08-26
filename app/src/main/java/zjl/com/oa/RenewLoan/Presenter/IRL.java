package zjl.com.oa.RenewLoan.Presenter;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRL {

    @POST(Constant.BusFeedback)
    Call<ResponseWithNoData> BusFeedback(@Body RequestBody body);

    @POST(Constant.BusFirstFeedback)
    Call<ResponseWithNoData> FirstFeedback(@Body RequestBody body);
    @POST(Constant.PleDgeAssessTwo)
    @Multipart
    Call<ResponseWithNoData> PleDgeAssess(
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
    @POST(Constant.Coming)
    Call<ResponseWithNoData> Coming(@Body RequestBody body);
    @POST(Constant.ApplyforRefinance)
    Call<ResponseWithNoData> ApplyforRefinance(@Body RequestBody body);
    @POST(Constant.CarPhoto)
    @Multipart
    Call<ResponseWithNoData> post(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("remark") RequestBody remark,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part("type_id") RequestBody type_id,
            @Part("loan_length") RequestBody loan_length,
            @Part List<MultipartBody.Part> filesBody);
    @POST(Constant.FirstSureAmount)
    Call<ResponseWithNoData> FirstSureAmount(@Body RequestBody body);
    @POST(Constant.SureAmount)
    Call<ResponseWithNoData> SureAmount(@Body RequestBody body);
    @POST(Constant.SureAmountReturn)
    Call<ResponseWithNoData> SureAmountReturn(@Body RequestBody body);
    @POST(Constant.InputInfo)
    Call<ResponseWithNoData> InputInfo(@Body RequestBody body);

    @POST(Constant.UploadCarPhoto)
    @Multipart
    Call<ResponseWithNoData> UploadCarPhoto(
        @Header("request_end_flag") String request_end_flag ,
        @Part("type") RequestBody typebody,
        @Part("token") RequestBody token,
        @Part("workflow_content_id") RequestBody workflow_content_id,
        @Part("remark") RequestBody remark,
        @Part("wk_point_id") RequestBody wk_point_id,
        @Part("type_id") RequestBody type_id,
        @Part List<MultipartBody.Part> filesBody);

    @POST(Constant.ContractDetail)
    Call<ResponseWithNoData> ContractDetail(@Body RequestBody body);
    @POST(Constant.AuditRefinance)
    Call<ResponseWithNoData> AuditRefinance(@Body RequestBody body);
    @POST(Constant.AssessRefinance)
    Call<ResponseWithNoData> AssessRefinance(@Body RequestBody body);
    @POST(Constant.InfoCheckRefinanceTwo)
    @Multipart
    Call<ResponseWithNoData> InfoCheckRefinance(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part("persion_court") RequestBody persion_court_body,
            @Part("car_break_rules") RequestBody car_break_rules_body,
            @Part("insurance") RequestBody insurance_body,
            @Part("remark") RequestBody remark,
            @Part List<MultipartBody.Part> filesBody);
    @POST(Constant.Form)
    Call<FormResponse> Form(@Body RequestBody body);

}
