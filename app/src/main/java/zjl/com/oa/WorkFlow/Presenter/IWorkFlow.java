package zjl.com.oa.WorkFlow.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.GetWorkFlowResponse;
import zjl.com.oa.Response.PhotoVideoDetailResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlow {
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
}
