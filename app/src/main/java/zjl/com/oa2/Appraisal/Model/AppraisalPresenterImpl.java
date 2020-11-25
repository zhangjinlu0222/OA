package zjl.com.oa2.Appraisal.Model;

import zjl.com.oa2.Appraisal.Presenter.IAppraisalListener;
import zjl.com.oa2.Appraisal.Presenter.IAppraisalPresenter;
import zjl.com.oa2.Appraisal.Presenter.IAppraisalView;
import zjl.com.oa2.Response.AppraisalResponse;

public class AppraisalPresenterImpl implements IAppraisalPresenter,IAppraisalListener{
    private AppraisalModelImpl appraisalModel;
    private IAppraisalView appraisalView;

    public AppraisalPresenterImpl( IAppraisalView appraisalView) {
        this.appraisalModel = new AppraisalModelImpl();
        this.appraisalView = appraisalView;
    }

    @Override
    public void QueryCarAssess(String token, String car_branch, String car_model, int car_year,
                               int page_count, int order) {
            if (car_branch.length() <= 0
                    && car_model.length() <= 0
                    && Integer.toString(car_year).length() <= 0){
                appraisalView.showFailureMsg("估价信息错误，请重新选择估价信息");
                return;
            }

            if (appraisalModel != null){
                appraisalModel.QueryCarAssess(token,car_branch,car_model,car_year,page_count,order,this);
            }

            if (appraisalView != null){
                appraisalView.showProgress();
            }
    }

    @Override
    public void onSucceed(AppraisalResponse.Result result) {
        if (appraisalView != null){
            appraisalView.hideProgress();
            appraisalView.refreshData(result);
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

        if (appraisalView != null){
            appraisalView.hideProgress();
            appraisalView.onFailure(msg);
        }
    }

    @Override
    public void relogin() {

        if (appraisalView != null){
            appraisalView.hideProgress();
            appraisalView.relogin();
        }
    }
}
