package zjl.com.oa2.Visit.Model;

import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LookInterviewResponse;
import zjl.com.oa2.Visit.Presenter.INewVisitorListener;
import zjl.com.oa2.Visit.Presenter.INewVisitorModel;
import zjl.com.oa2.Visit.Presenter.INewVisitorPresenter;
import zjl.com.oa2.Visit.Presenter.INewVisitorView;

/**
 * Created by Administrator on 2018/3/2.
 */

public class NewVisitorPresenterImpl implements INewVisitorPresenter,INewVisitorListener {
    private INewVisitorModel mModel;
    private INewVisitorView mView;

    public NewVisitorPresenterImpl(INewVisitorView inewVisitorView) {
        this.mView = inewVisitorView;
        mModel = new NewVisitormodelImpl();
    }

    @Override
    public void newVisitor(String token,String name,int from,int product_type_name,String remark,String comment,String w_con_id,String w_pot_id) {
        if ("".equals(name)){
            if (mView != null){
                mView.showFailureMsg("请输入客户姓名");
            }
            return;
        }
        if (product_type_name <= 0){
            if (mView != null){
                mView.showFailureMsg("请选择业务类型");
            }
            return;
        }
        if (from <= 0){
            if (mView != null){
                mView.showFailureMsg("请选择客户来源");
            }
            return;
        }
        if ("".equals(remark)){
            if (mView != null){
                mView.showFailureMsg("请输入点位说明");
            }
            return;
        }
        if (mModel != null){
            mModel.newVisitor(token,name,from, product_type_name,remark,comment,w_con_id,w_pot_id,this);
        }

        if (mView != null){
            mView.showProgress();
        }
    }

    @Override
    public void onDestoryView() {
        if (mView != null){
            mView = null;
        }
    }

    @Override
    public void onSucceed() {
        if (mView != null){
            mView.toMainActivity();
        }
        if (mView != null){
            mView.hideProgress();
        }
    }

    @Override
    public void onFail(String msg) {

        if (mView != null){
            mView.showFailureMsg(msg);
        }
        if (mView != null){
            mView.hideProgress();
        }
    }

    @Override
    public void relogin() {
        if (mView != null){
            mView.relogin();
        }
        if (mView != null){
            mView.hideProgress();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void Form(String token, String workflow_content_id, String wk_point_id) {
        if (mView != null){
            mModel.Form( token,  workflow_content_id, wk_point_id,this);
        }
        if (mView != null){
            mView.showProgress();
        }
    }

    @Override
    public void onSucceed(FormResponse.Result result) {
        if (mView != null){
            mView.loadForms(result);
            mView.hideProgress();
        }
    }
}
