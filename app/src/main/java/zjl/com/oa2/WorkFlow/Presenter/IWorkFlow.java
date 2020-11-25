package zjl.com.oa2.WorkFlow.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.Response.GetWorkFlowResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.LookContractResponse;
import zjl.com.oa2.Response.PhotoVideoDetailResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlow {

    @POST(Constant.LookLoanDetail)
    Call<LoanDetailResponse> LookLoanDetail(@Body RequestBody body);
    @POST(Constant.FlowProcess)
    Call<GetWorkFlowResponse> getWorkFlow(@Body RequestBody body);
    @POST(Constant.HRProjectDetail)
    Call<GetWorkFlowResponse> getWorkDetail(@Body RequestBody body);
    @POST(Constant.HRRejection)
    Call<ResponseWithNoData> HRRejection(@Body RequestBody body);
    @POST(Constant.PhotoVideoDetail)
    Call<PhotoVideoDetailResponse> getPhotoVideoDetail(@Body RequestBody body);
    @POST(Constant.HRRefuseProject)
    Call<ResponseWithNoData> endWorkFlow(@Body RequestBody body);
    @POST(Constant.HRRecoverProject)
    Call<ResponseWithNoData> recoverWorkflow(@Body RequestBody body);
    @POST(Constant.LookRefinanceContract)
    Call<LookContractResponse> LookRefinanceContract(@Body RequestBody body);
}
