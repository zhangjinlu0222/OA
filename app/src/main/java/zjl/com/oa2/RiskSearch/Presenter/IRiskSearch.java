package zjl.com.oa2.RiskSearch.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.BigDataResponse;
import zjl.com.oa2.Response.GPSResponse;
import zjl.com.oa2.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IRiskSearch {
    @POST(Constant.GetBigDatas)
    Call<BigDataResponse> GetBigDatas(@Body RequestBody body);
    @POST(Constant.LookBigDatas)
    Call<BigDataResponse> LookBigDatas(@Body RequestBody body);
}
