package zjl.com.oa.Visit.Model;

import zjl.com.oa.Response.LookInterviewResponse;
import zjl.com.oa.Visit.Presenter.INewVisitorListener;
import zjl.com.oa.Visit.Presenter.INewVisitorModel;
import zjl.com.oa.Visit.Presenter.INewVisitorPresenter;
import zjl.com.oa.Visit.Presenter.INewVisitorView;

/**
 * Created by Administrator on 2018/3/2.
 */

public class NewVisitorPresenterImpl implements INewVisitorPresenter,INewVisitorListener {
    private INewVisitorModel newVisitorModel;
    private INewVisitorView newVisitorView;

    public NewVisitorPresenterImpl(INewVisitorView inewVisitorView) {
        this.newVisitorView = inewVisitorView;
        newVisitorModel = new NewVisitormodelImpl();
    }

    @Override
    public void newVisitor(String token,String name,int from,int product_type_name,String remark,String comment,String w_con_id,String w_pot_id) {
        if ("".equals(name)){
            if (newVisitorView != null){
                newVisitorView.showFailureMsg("请输入客户姓名");
            }
            return;
        }
        if (product_type_name <= 0){
            if (newVisitorView != null){
                newVisitorView.showFailureMsg("请选择业务类型");
            }
            return;
        }
        if (from <= 0){
            if (newVisitorView != null){
                newVisitorView.showFailureMsg("请选择客户来源");
            }
            return;
        }
        if ("".equals(remark)){
            if (newVisitorView != null){
                newVisitorView.showFailureMsg("请输入点位说明");
            }
            return;
        }
        if (newVisitorModel != null){
            newVisitorModel.newVisitor(token,name,from, product_type_name,remark,comment,w_con_id,w_pot_id,this);
        }

        if (newVisitorView != null){
            newVisitorView.showProgressBar();
        }
    }

    @Override
    public void onDestoryView() {
        if (newVisitorView != null){
            newVisitorView = null;
        }
    }

    @Override
    public void onSucceed() {
        if (newVisitorView != null){
            newVisitorView.toMainActivity();
        }
        if (newVisitorView != null){
            newVisitorView.hideProgressBar();
        }
    }

    @Override
    public void onFail(String msg) {

        if (newVisitorView != null){
            newVisitorView.showFailureMsg(msg);
        }
        if (newVisitorView != null){
            newVisitorView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {
        if (newVisitorView != null){
            newVisitorView.relogin();
        }
        if (newVisitorView != null){
            newVisitorView.hideProgressBar();
        }
    }
    @Override
    public void LookInterview(String token, String w_con_id) {

        if (newVisitorModel != null){
            newVisitorModel.LookInterview( token,  w_con_id, this);
        }
        if (newVisitorView != null){
            newVisitorView.showProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed(LookInterviewResponse.Result result) {

        if (newVisitorView != null){
            newVisitorView.refreshData(result);
        }

        if (newVisitorView != null){
            newVisitorView.hideProgressBar();
        }
    }
}
