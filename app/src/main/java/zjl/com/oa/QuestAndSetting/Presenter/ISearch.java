package zjl.com.oa.QuestAndSetting.Presenter;

import retrofit2.Call;
import retrofit2.http.POST;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Response.SearchResponse;

public interface ISearch {
    @POST(Constant.AdvanceSecInfo)
    Call<SearchResponse> AdvanceSecInfo();
}
