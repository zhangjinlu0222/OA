package zjl.com.oa2.Meeting.Presenter;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IMetting {
    @POST(Constant.Interview)
    @Multipart
    Call<ResponseWithNoData> post(@Part("token") RequestBody token,
                                  @Part MultipartBody.Part file1,
                                  @Part MultipartBody.Part file2,
                                  @Part("remark") RequestBody remark,
                                  @Part("product_type_name") RequestBody product_type_name,
                                  @Part("workflow_content_id") RequestBody workflow_content_id,
                                  @Part("wk_point_id") RequestBody wk_point_id);

    @POST(Constant.Interview)
    @Multipart
    Call<ResponseWithNoData> Interview(@Part("token") RequestBody token,
                                       @Part("workflow_content_id") RequestBody workflow_content_id,
                                       @Part("wk_point_id") RequestBody wk_point_id,
                                  @Part List<MultipartBody.Part> photos,
                                  @Part("remark") RequestBody remark,
                                  @Part("customer_name") RequestBody customer_name,
                                  @Part("identity") RequestBody identity,
                                       @Part("customer_phone") RequestBody customer_phone,
                                       @Part("address") RequestBody address,
                                       @Part("bank_code") RequestBody bank_code,
                                  @Part("bank_name") RequestBody bank_name,
                                  @Part("purpose") RequestBody purpose,
                                  @Part("car_license") RequestBody car_license,
                                  @Part("car_registration") RequestBody car_registration,
                                  @Part("car_engine_no") RequestBody car_engine_no,
                                  @Part("car_vin") RequestBody car_vin);
    @POST(Constant.InputInfo)
    Call<ResponseWithNoData> InputInfo(@Body RequestBody body);

    @POST(Constant.HRRefuseProject)
    Call<ResponseWithNoData> endWorkFlow(@Body RequestBody body);
    @POST(Constant.LookInterview)
    Call<LookInterviewResponse> LookInterview(@Body RequestBody body);
}
