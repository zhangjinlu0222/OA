package zjl.com.oa2.RenewLoanCompletation.Model;

import zjl.com.oa2.RenewLoanCompletation.Presenter.IListener;
import zjl.com.oa2.RenewLoanCompletation.Presenter.IModel;
import zjl.com.oa2.RenewLoanCompletation.Presenter.IPresenter;
import zjl.com.oa2.RenewLoanCompletation.Presenter.IView;
import zjl.com.oa2.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public class PresenterImpl implements IPresenter,IListener {

    private IView iView;
    private IModel iModel;

    public PresenterImpl(IView view) {
        this.iView = view;
        this.iModel = new ModelImp();
    }

    @Override
    public void onSucceed() {
        if (iView != null){
            iView.hideProgress();
            iView.toMainActivity();
        }
    }

    @Override
    public void onFail(String msg) {

        if (iView != null){
            iView.hideProgress();
            iView.showFailureMsg(msg);
        }
    }

    @Override
    public void relogin() {

        if (iView != null) {
            iView.hideProgress();
            iView.relogin();
        }
    }

    @Override
    public void onFail() {
        this.onFail("操作失败，请重试");
    }

    @Override
    public void RefinanceFinishFlow(String token, String w_con_id, String w_pot_id) {
        if (iModel != null){
            iModel.RefinanceFinishFlow(token,w_con_id ,w_pot_id ,this );
        }}
}
