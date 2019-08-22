package zjl.com.oa.Appraisal.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.AppraisalResponse;

public interface IAppraisalView extends IBaseView{

    void showProgress();
    void hideProgress();

    void onFailure(String msg);
    void refreshData(AppraisalResponse.Result result);
}
