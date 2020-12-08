package zjl.com.oa2.LoanInfoList.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.AppraisalResponse;
import zjl.com.oa2.Response.LoanInfosResponse;

public interface ILoanInfoListView extends IBaseView{

    void showProgress();
    void hideProgress();

    void onFailure(String msg);
    void loadLoanInfos(LoanInfosResponse.Result result);
}
