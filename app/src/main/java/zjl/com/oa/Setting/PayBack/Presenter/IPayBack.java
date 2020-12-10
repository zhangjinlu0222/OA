package zjl.com.oa.Setting.PayBack.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseResponse;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.LogoutResponse;
import zjl.com.oa.Response.ModifyPwdResponse;
import zjl.com.oa.Response.SearchNameResponse;

/**
 * Created by Administrator on 2018/2/11.
 */

public interface IPayBack {
    @POST(Constant.SearchName)
    Call<SearchNameResponse> SearchName(@Body RequestBody body);
    @POST(Constant.SearchCarType)
    Call<SearchNameResponse> SearchCarType(@Body RequestBody body);
    @POST(Constant.UpdateReturnSchedule)
    Call<ResponseWithNoData> UpdateReturnSchedule(@Body RequestBody body);
}
