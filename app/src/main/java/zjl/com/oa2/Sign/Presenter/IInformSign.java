package zjl.com.oa2.Sign.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.GetBeginSignResponse;
import zjl.com.oa2.Response.GetSigningResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInformSign {
    @POST(Constant.LookInformSigned)
    Call<GetSigningResponse> post(@Body RequestBody body);
    @POST(Constant.InformSigned)
    Call<ResponseWithNoData> postInformSign(@Body RequestBody body);
}
