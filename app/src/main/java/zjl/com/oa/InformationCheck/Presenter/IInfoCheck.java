package zjl.com.oa.InformationCheck.Presenter;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInfoCheck {
    @POST(Constant.InfoCheck)
    @Multipart
    Call<ResponseWithNoData> post(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
          @Part("workflow_content_id") RequestBody workflow_content_id,
          @Part("persion_court") RequestBody persion_court,
          @Part("credit") RequestBody credit,
          @Part("car_break_rules") RequestBody car_break_rules,
          @Part("insurance") RequestBody insurance,
          @Part("legal_person") RequestBody legal_person,
          @Part("wk_point_id") RequestBody wk_point_id,
          @Part List<MultipartBody.Part> files,
          @Part("remark") RequestBody remark);

    @POST(Constant.HRRefuseProject)
    Call<ResponseWithNoData> endWorkFlow(@Body RequestBody body);
    @POST(Constant.Form)
    Call<FormResponse> Form(@Body RequestBody body);
}
