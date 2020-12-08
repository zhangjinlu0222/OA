package zjl.com.oa2.RiskSearch.Model;

import zjl.com.oa2.Response.BigDataResponse;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchListener;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchModel;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchPresenter;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchView;

/**
 * Created by Administrator on 2018/3/2.
 */

public class RiskSearchPresenterImpl implements IRiskSearchPresenter,IRiskSearchListener {
    private IRiskSearchView view;
    private IRiskSearchModel model;
    private boolean isShowing = false;

    public RiskSearchPresenterImpl(IRiskSearchView view) {
        this.view = view;
        this.model = new RiskSearchModelImpl();
    }

    @Override
    public void onSucceed(BigDataResponse.Result result) {

        if (view != null){
            isShowing = false;
            view.hideProgress();
        }

        if (view != null){
            view.loadBigDatas(result);
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
        if (view != null){
            view.showFailureMsg(msg);
        }

        if (view != null){
            isShowing = false;
            view.hideProgress();
        }
    }

    @Override
    public void relogin() {
        if (view != null){
            view.relogin();
        }

        if (view != null){
            isShowing = false;
            view.hideProgress();
        }
    }

    @Override
    public void GetBigDatas(String token, String w_con_id, String name, String phone, String identity_id) {
        if (model != null){
            model.GetBigDatas( token,  w_con_id,  name,  phone,  identity_id,this );
        }
    }
    @Override
    public void LookBigDatas(String token, String w_con_id) {
        if (model != null){
            model.LookBigDatas( token,  w_con_id,this );
        }
    }
}
