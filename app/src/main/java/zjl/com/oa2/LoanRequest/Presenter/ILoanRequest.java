package zjl.com.oa2.LoanRequest.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.GetLoanRequestResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public interface ILoanRequest {
    @POST(Constant.LookLoanApplication)
    Call<GetLoanRequestResponse> lookLoanApplication(@Body RequestBody body);
    @POST(Constant.LoanApplication)
    Call<ResponseWithNoData> loanApplication(@Body RequestBody body);
}
