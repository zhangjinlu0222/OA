package zjl.com.oa.Quest.Presenter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IQuestList {
//    @POST(Constant.WorkList)
//    Call<QuestListResponse> post(@Body RequestBody token);
//    @POST(Constant.WorkListPage)
//    Call<QuestListResponse> postFilter(@Body RequestBody body);
    @POST(Constant.WrokflowListOrder)
    Call<QuestListResponse> WorkflowListOrder(@Body RequestBody body);
    @POST(Constant.WorkflowListAdvPage)
    Call<QuestListResponse> WorkflowListAdvPage(@Body RequestBody body);
    @POST(Constant.PointOpertState)
    Call<ResponseWithNoData> postPointOpertState(@Body RequestBody body);
    @POST(Constant.CloseFlow)
    Call<ResponseWithNoData> CloseFlow(@Body RequestBody body);
    @POST(Constant.CreateRefinance)
    Call<ResponseWithNoData> CreateRefinance(@Body RequestBody body);
}
