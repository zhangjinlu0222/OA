package zjl.com.oa2.BusinessFeedBack.Model;

import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBackListener;
import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBackModel;
import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBackPresenter;
import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBackView;
import zjl.com.oa2.Response.BusFirstFeedBackResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public class BusFeedBackPresenterImpl implements IBusFeedBackPresenter,IBusFeedBackListener {
    private IBusFeedBackView BusFeedBackView;
    private IBusFeedBackModel BusFeedBackModel;

    public BusFeedBackPresenterImpl(IBusFeedBackView evaluationQuotaView) {
        this.BusFeedBackView = evaluationQuotaView;
        this.BusFeedBackModel = new BusFeedBackModelImpl();
    }

    @Override
    public void LookFirstSureAmount(String token, String w_con_id) {

        if (BusFeedBackModel != null){
            BusFeedBackModel.LookFirstSureAmount(token,w_con_id,this);
        }
        if (BusFeedBackView != null){
            BusFeedBackView.showProgressBar();
        }
    }

    @Override
    public void BusFeedback(String token, String w_con_id, String w_pot_id, int loan_length, float loan_rate,
                            String return_amount_method,String remark) {
        if (loan_length == 0){
            this.onFail("请输入贷款期限");
            return;
        }
        if (loan_rate <= 0){
            this.onFail("请输入贷款利率");
            return;
        }
        if (return_amount_method == null || "".equals(return_amount_method)){
            this.onFail("请选择还款方式");
            return;
        }

        if (BusFeedBackModel != null){
            BusFeedBackModel.BusFeedback( token,  w_con_id,  w_pot_id,  loan_length,  loan_rate,
             return_amount_method, remark,this);
        }
        if (BusFeedBackView != null){
            BusFeedBackView.showProgressBar();
        }
    }

    @Override
    public void BusFirstFeedback(String token, String w_con_id, String w_pot_id, String remark) {

        if (BusFeedBackModel != null){
            BusFeedBackModel.BusFirstFeedback(token,w_con_id,w_pot_id,remark,this);
        }
        if (BusFeedBackView != null){
            BusFeedBackView.showProgressBar();
        }
    }

    @Override
    public void onDestoryView() {
        if (BusFeedBackView != null){
            BusFeedBackView = null;
        }
    }

    @Override
    public void onSucceed() {
        if (BusFeedBackView != null){
            BusFeedBackView.toMainActivity();
            BusFeedBackView.hideProgressBar();
        }
    }

    @Override
    public void onFail(String msg) {
        if (BusFeedBackView != null){
            BusFeedBackView.showFailureMsg(msg);
            BusFeedBackView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {

        if (BusFeedBackView != null){
            BusFeedBackView.relogin();
            BusFeedBackView.hideProgressBar();
        }
    }

    @Override
    public void onSucceed(BusFirstFeedBackResponse.Result result) {
        if (BusFeedBackView != null){
            BusFeedBackView.refreshData(result);
            BusFeedBackView.hideProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }
}
