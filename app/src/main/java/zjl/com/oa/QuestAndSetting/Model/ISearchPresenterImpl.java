package zjl.com.oa.QuestAndSetting.Model;

import zjl.com.oa.QuestAndSetting.Presenter.ISearchListener;
import zjl.com.oa.QuestAndSetting.Presenter.ISearchModel;
import zjl.com.oa.QuestAndSetting.Presenter.ISearchPresenter;
import zjl.com.oa.QuestAndSetting.Presenter.ISearchView;
import zjl.com.oa.Response.SearchResponse;

public class ISearchPresenterImpl implements ISearchListener,ISearchPresenter{
    private ISearchView iSearchView;
    private ISearchModel iSearchModel;

    public ISearchPresenterImpl(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
        this.iSearchModel = new ISearchModelImpl();
    }


    @Override
    public void onSucceed(SearchResponse.Result result) {
        if (iSearchView != null){
            iSearchView.loadTypeStatus(result);
            iSearchView.hideLoading();
        }
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    public void relogin() {
        if (iSearchView != null){
            iSearchView.relogin();
        }
    }

    @Override
    public void AdvanceSecInfo() {
        if (iSearchModel != null){
            iSearchModel.AdvanceSecInfo(this);
        }
        if (iSearchView != null){
            iSearchView.showLoading();
        }
    }
}
