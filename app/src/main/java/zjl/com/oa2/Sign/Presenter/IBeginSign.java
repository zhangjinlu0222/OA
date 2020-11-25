package zjl.com.oa2.Sign.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.GetBeginSignResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IBeginSign {
    @POST(Constant.LookBeginSigned)
    Call<GetBeginSignResponse> get(@Body RequestBody body);
    @POST(Constant.BeginSigned)
    Call<ResponseWithNoData> post(@Body RequestBody body);
}
