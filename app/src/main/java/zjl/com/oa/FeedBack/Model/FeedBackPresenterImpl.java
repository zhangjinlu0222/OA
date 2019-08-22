package zjl.com.oa.FeedBack.Model;

import zjl.com.oa.FeedBack.Presenter.IFeedBackListener;
import zjl.com.oa.FeedBack.Presenter.IFeedBackModel;
import zjl.com.oa.FeedBack.Presenter.IFeedBackPresenter;
import zjl.com.oa.FeedBack.Presenter.IFeedBackView;

/**
 * Created by Administrator on 2018/3/13.
 */

public class FeedBackPresenterImpl implements IFeedBackPresenter,IFeedBackListener {
    private IFeedBackView feedBackView;
    private IFeedBackModel feedBackModel;

    public FeedBackPresenterImpl(IFeedBackView feedBackView) {
        this.feedBackView = feedBackView;
        this.feedBackModel = new FeedBackModelImpl();
    }

    @Override
    public void onSucceed() {
        if (feedBackView != null){
            feedBackView.toMainActivity();
        }
        if (feedBackView != null){
            feedBackView.hideProgressBar();
        }
    }

    @Override
    public void onSucceed(String arg) {
        if (feedBackView != null){
            feedBackView.refreshData(arg);
        }
        if (feedBackView != null){
            feedBackView.hideProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onFail(String arg) {

        if (feedBackView != null){
            feedBackView.showFailureMsg(arg);
        }
        if (feedBackView != null){
            feedBackView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {

        if (feedBackView != null){
            feedBackView.relogin();
        }
        if (feedBackView != null){
            feedBackView.hideProgressBar();
        }
    }

    @Override
    public void LookFeedbackResult(String token, String w_con_id) {

        if (feedBackModel != null){
            feedBackModel.LookFeedbackResult(token,w_con_id,this);
        }
        if (feedBackView != null){
            feedBackView.showProgressBar();
        }
    }

    @Override
    public void FeedbackResult(String token, String w_con_id, String w_pot_id, String remark) {

        if (feedBackModel != null){
            feedBackModel.FeedbackResult(token,w_con_id,w_pot_id, remark,this);
        }
        if (feedBackView != null){
            feedBackView.showProgressBar();
        }
    }

    @Override
    public void LookFirstFeedbackResult(String token, String w_con_id) {

        if (feedBackModel != null){
            feedBackModel.LookFirstFeedbackResult(token,w_con_id,this);
        }
        if (feedBackView != null){
            feedBackView.showProgressBar();
        }
    }

    @Override
    public void FirstFeedbackResult(String token, String w_con_id, String w_pot_id, String remark) {

        if (feedBackModel != null){
            feedBackModel.FirstFeedbackResult(token,w_con_id,w_pot_id, remark,this);
        }
        if (feedBackView != null){
            feedBackView.showProgressBar();
        }
    }

    @Override
    public void onDestoryView() {
        if (feedBackView != null){
            feedBackView = null;
        }
    }
}
