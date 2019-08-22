package zjl.com.oa.EvaluationQuota.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.EvaluationQuotaResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IEvaluationQuota {
    @POST(Constant.LookFirstSureAmount)
    Call<EvaluationQuotaResponse> LookFirstSureAmount(@Body RequestBody body);
    @POST(Constant.SureAmount)
    Call<ResponseWithNoData> SureAmount(@Body RequestBody body);
    @POST(Constant.FirstSureAmount)
    Call<ResponseWithNoData> FirstSureAmount(@Body RequestBody body);
    @POST(Constant.SureAmountReturn)
    Call<ResponseWithNoData> SureAmountReturn(@Body RequestBody body);
}
