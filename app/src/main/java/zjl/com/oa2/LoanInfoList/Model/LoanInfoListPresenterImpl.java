package zjl.com.oa2.LoanInfoList.Model;

import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoListListener;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoListPresenter;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoListView;
import zjl.com.oa2.Response.LoanInfosResponse;

public class LoanInfoListPresenterImpl implements ILoanInfoListPresenter,ILoanInfoListListener{
    private LoanInfoListModelImpl loaninfolistModel;
    private ILoanInfoListView loaninfolistView;

    public LoanInfoListPresenterImpl(ILoanInfoListView loaninfolistView) {
        this.loaninfolistModel = new LoanInfoListModelImpl();
        this.loaninfolistView = loaninfolistView;
    }

    @Override
    public void onSucceed(LoanInfosResponse.Result result) {
        if (loaninfolistView != null){
            loaninfolistView.hideProgress();
            loaninfolistView.loadLoanInfos(result);
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed() {
    }

    @Override
    public void onFail(String msg) {

        if (loaninfolistView != null){
            loaninfolistView.hideProgress();
            loaninfolistView.onFailure(msg);
        }
    }

    @Override
    public void relogin() {

        if (loaninfolistView != null){
            loaninfolistView.hideProgress();
            loaninfolistView.relogin();
        }
    }

    @Override
    public void LoanInfoList(String token, int page_count, String start_date, String end_date, String name, String flag,int order) {
        if (loaninfolistModel != null ){
            loaninfolistModel.LoanInfoList(token,page_count,start_date,end_date,name,flag,order,this);
        }

        if (loaninfolistView != null){
            loaninfolistView.showProgress();
        }
    }
}
