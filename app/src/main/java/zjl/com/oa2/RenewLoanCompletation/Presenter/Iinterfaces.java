package zjl.com.oa2.RenewLoanCompletation.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;

public interface Iinterfaces {

    @POST(Constant.RefinanceFinishFlow)
    Call<ResponseWithNoData> RefinanceFinishFlow(@Body RequestBody body);
}
