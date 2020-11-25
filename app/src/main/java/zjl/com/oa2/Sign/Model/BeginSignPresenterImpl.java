package zjl.com.oa2.Sign.Model;

import zjl.com.oa2.Response.GetBeginSignResponse;
import zjl.com.oa2.Sign.Presenter.IBeginSignListener;
import zjl.com.oa2.Sign.Presenter.IBeginSignModel;
import zjl.com.oa2.Sign.Presenter.IBeginSignPresenter;
import zjl.com.oa2.Sign.Presenter.IBeginSignView;

/**
 * Created by Administrator on 2018/3/5.
 */

public class BeginSignPresenterImpl implements IBeginSignListener,IBeginSignPresenter {
    private IBeginSignView BeginSignView;
    private IBeginSignModel BeginSignModel;

    public BeginSignPresenterImpl(IBeginSignView BeginSignView) {
        this.BeginSignModel = new BeginSignModelImpl();
        this.BeginSignView = BeginSignView;
    }

    @Override
    public void getBeginSign(String token, String workflow_content_id) {

        if (BeginSignModel != null ){
            BeginSignModel.getBeginSign(token,workflow_content_id,this);
        }
        if (BeginSignView != null){
            BeginSignView.showProgressBar();
        }
    }
    @Override
    public void postBeginSign(String token, String workflow_content_id, String work_point_id) {

        if (BeginSignModel != null ){
            BeginSignModel.postBeginSign(token,workflow_content_id,work_point_id,this);
        }

        if (BeginSignView != null){
            BeginSignView.showProgressBar();
        }
    }

    @Override
    public void onDestory() {
        if (this.BeginSignView != null){
            this.BeginSignView = null;
        }
    }

    @Override
    public void onSucceed() {
        if (BeginSignView != null){
            BeginSignView.toMainActivity();
        }
        if (BeginSignView != null){
            BeginSignView.hideProgressBar();
        }
    }

    @Override
    public void onSucceed(GetBeginSignResponse.Result data) {

        if (BeginSignView != null){
            BeginSignView.refreshData(data);
        }
        if (BeginSignView != null){
            BeginSignView.hideProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onFail(String arg) {

        if (BeginSignView != null){
            BeginSignView.showFailureMsg(arg);
        }
        if (BeginSignView != null){
            BeginSignView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {

        if (BeginSignView != null){
            BeginSignView.relogin();
        }
        if (BeginSignView != null){
            BeginSignView.hideProgressBar();
        }
    }
}
