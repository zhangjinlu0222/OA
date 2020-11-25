package zjl.com.oa2.TransferVoucher.Presenter;

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
import zjl.com.oa2.Response.GetTransferVoucherResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface ITransferVoucher {

    @POST(Constant.LookFirstCharge)
    Call<GetTransferVoucherResponse> getTransferVoucher(@Body RequestBody body);

    @POST(Constant.UploadCarPhoto)
    @Multipart
    Call<ResponseWithNoData> post(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
          @Part("remark") RequestBody remark,
          @Part("workflow_content_id") RequestBody workflow_content_id,
          @Part("wk_point_id") RequestBody wk_point_id,
          @Part("type_id") RequestBody type_id,
          @Part List<MultipartBody.Part> filesBody);
}
