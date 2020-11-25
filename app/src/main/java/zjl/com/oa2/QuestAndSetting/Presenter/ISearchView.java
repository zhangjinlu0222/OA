package zjl.com.oa2.QuestAndSetting.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.SearchResponse;

public interface ISearchView extends IBaseView{

    String getSearchName();
    String getStartDate();
    String getEndDate();
    String getSearchType();
    String getSearchStatus();
    void clearSearchOptions();
    void loadTypeStatus(SearchResponse.Result result);
    void showLoading();
    void hideLoading();
}
