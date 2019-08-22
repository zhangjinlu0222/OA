package zjl.com.oa.QuestAndSetting.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ModelImpl;
import zjl.com.oa.QuestAndSetting.Presenter.ISearch;
import zjl.com.oa.QuestAndSetting.Presenter.ISearchListener;
import zjl.com.oa.QuestAndSetting.Presenter.ISearchModel;
import zjl.com.oa.Response.SearchResponse;

public class ISearchModelImpl extends ModelImpl implements ISearchModel{

    @Override
    public void AdvanceSecInfo(ISearchListener listener) {

        ISearch service = retrofit.create(ISearch.class);

        Call<SearchResponse> call = service.AdvanceSecInfo();

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    SearchResponse result = response.body();
                    if (result != null && result.getCode() == Constant.Succeed){
                        listener.onSucceed(result.getResult());
                        return;
                    }
                    if (result != null && result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                        return;
                    }

                    listener.onFail(result.getMessage());
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
