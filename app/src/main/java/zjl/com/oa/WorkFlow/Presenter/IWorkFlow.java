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
    @POST(Constant.Flowprocess)
    Call<GetWorkFlowResponse> getWorkFlow(@Body RequestBody body);
    @POST(Constant.Detail)
    Call<GetWorkFlowResponse> getWorkDetail(@Body RequestBody body);
    @POST(Constant.PointEdit)
    Call<ResponseWithNoData> postPointEdit(@Body RequestBody body);
    @POST(Constant.PhotoVideoDetail)
    Call<PhotoVideoDetailResponse> getPhotoVideoDetail(@Body RequestBody body);
    @POST(Constant.EndWorkflow)
    Call<ResponseWithNoData> endWorkFlow(@Body RequestBody body);
    @POST(Constant.RecoverWorkflow)
    Call<ResponseWithNoData> recoverWorkflow(@Body RequestBody body);
}
