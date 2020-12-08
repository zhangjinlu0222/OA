package zjl.com.oa2.LoanInfoList.Presenter;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Response.LoanInfosResponse;

public interface ILoanInfoList {

    @POST(Constant.LoanInfoList)
    Call<LoanInfosResponse> LoanInfoList(@Body RequestBody body);
}
