package zjl.com.oa2.LoanRequest.Model;

import java.util.List;

import zjl.com.oa2.LoanRequest.Presenter.ILoanRequestListener;
import zjl.com.oa2.LoanRequest.Presenter.ILoanRequestModel;
import zjl.com.oa2.LoanRequest.Presenter.ILoanRequestPresenter;
import zjl.com.oa2.LoanRequest.Presenter.ILoanRequestView;
import zjl.com.oa2.Response.GetLoanRequestResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public class LoanRequestPresenterImpl implements ILoanRequestPresenter,ILoanRequestListener{
    private ILoanRequestView loanRequestView;
    private ILoanRequestModel loanRequestModel;
    private String wk_point_id;

    public LoanRequestPresenterImpl(ILoanRequestView loanRequestView) {
        this.loanRequestView = loanRequestView;
        this.loanRequestModel = new LoanRequestModelImpl();
    }

    @Override
    public void onSucceed() {
        if (loanRequestView != null){
            loanRequestView.toMainActivity();
            loanRequestView.hideProgressBar();

            if (wk_point_id.equals("20")){
                loanRequestView.saveOperationState("1");
            }
        }
    }

    @Override
    public void onSucceed(List<GetLoanRequestResponse.Result.Section> data) {
        if (loanRequestView != null){
            loanRequestView.refreshData(data);
        }
        if (loanRequestView != null){
            loanRequestView.hideProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onFail(String arg) {

        if (loanRequestView != null){
            loanRequestView.showFailureMsg(arg);
        }
        if (loanRequestView != null){
            loanRequestView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {

        if (loanRequestView != null){
            loanRequestView.relogin();
        }
        if (loanRequestView != null){
            loanRequestView.hideProgressBar();
        }
    }

    @Override
    public void lookLoanApplication(String token, String workflow_content_id) {
        if (loanRequestModel != null){
            loanRequestModel.lookLoanApplication(token,workflow_content_id,this);
        }

        if (loanRequestView != null){
            loanRequestView.showProgressBar();
        }
    }

    @Override
    public void loanApplication(String token, String w_con_id, String w_pot_id, String remark) {

        if (loanRequestModel != null){
            this.wk_point_id = w_pot_id;
            loanRequestModel.loanApplication(token,w_con_id,w_pot_id,remark,this);
        }
        if (loanRequestView != null){
            loanRequestView.showProgressBar();
        }
    }
}
