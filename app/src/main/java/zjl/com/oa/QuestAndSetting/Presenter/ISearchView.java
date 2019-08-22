package zjl.com.oa.QuestAndSetting.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.SearchResponse;

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
