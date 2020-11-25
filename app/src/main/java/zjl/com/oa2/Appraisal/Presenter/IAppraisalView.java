package zjl.com.oa2.Appraisal.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.AppraisalResponse;

public interface IAppraisalView extends IBaseView{

    void showProgress();
    void hideProgress();

    void onFailure(String msg);
    void refreshData(AppraisalResponse.Result result);
}
