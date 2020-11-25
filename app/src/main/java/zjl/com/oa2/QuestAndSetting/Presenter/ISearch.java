package zjl.com.oa2.QuestAndSetting.Presenter;

import retrofit2.Call;
import retrofit2.http.POST;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Response.SearchResponse;

public interface ISearch {
    @POST(Constant.AdvanceSecInfo)
    Call<SearchResponse> AdvanceSecInfo();
}
