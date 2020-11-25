package zjl.com.oa2.Setting.PayBack.Model;

import zjl.com.oa2.Response.SearchNameResponse;
import zjl.com.oa2.Setting.PayBack.Presenter.IPayBackListener;
import zjl.com.oa2.Setting.PayBack.Presenter.IPayBackModel;
import zjl.com.oa2.Setting.PayBack.Presenter.IPayBackPresenter;
import zjl.com.oa2.Setting.PayBack.Presenter.IPayBackView;

/**
 * Created by Administrator on 2018/3/1.
 */

public class PayBackPresenterImpl implements IPayBackPresenter,IPayBackListener {
    private IPayBackModel iPayBackModel;
    private IPayBackView iPayBackView;

    public PayBackPresenterImpl(IPayBackView IPayBackView) {
        this.iPayBackModel = new PayBackModelImpl();
        this.iPayBackView = IPayBackView;
    }
    @Override
    public void SearchName(String token, String name) {

        if ("".equals(name)){
            onFail("请输入姓名");
            return;
        }

        if (iPayBackModel != null){
            iPayBackModel.SearchName(token,name,this);
        }
        if (iPayBackView != null){
            iPayBackView.showProgressBar();
        }
    }

    @Override
    public void SearchCarType(String token, String w_con_id) {

        if ("".equals(w_con_id)){
            onFail("信息异常，请重新选择");
            return;
        }

        if (iPayBackModel != null){
            iPayBackModel.SearchCarType(token,w_con_id,this);
        }
        if (iPayBackView != null){
            iPayBackView.showProgressBar();
        }
    }

    @Override
    public void UpdateReturnSchedule(String token, String amount,String date,String schedule_id) {

        if ("".equals(amount)){
            onFail("请输入实际还款金额");
            return;
        }

        if ("".equals(date)){
            onFail("请选择实际还款日期");
            return;
        }
        if (schedule_id == null || "".equals(schedule_id)){
            onFail("请选择车型");
            return;
        }

        if (iPayBackModel != null){
            iPayBackModel.UpdateReturnSchedule(token,amount,date,schedule_id,this);
        }
        if (iPayBackView != null){
            iPayBackView.showProgressBar();
        }
    }

    @Override
    public void onDestory() {
        if (iPayBackView != null){
            iPayBackView = null;
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed(String arg) {
        if (iPayBackView != null){
            iPayBackView.showFailureMsg(arg);
            iPayBackView.toMainActivity();
            iPayBackView.hideProgressBar();
        }
    }
    @Override
    public void onSucceed() {
        if (iPayBackView != null){
            iPayBackView.toMainActivity();
        }
        if (iPayBackView != null){
            iPayBackView.hideProgressBar();
        }
    }

    @Override
    public void onSucceed(SearchNameResponse response) {
        if (iPayBackView != null){
            iPayBackView.updateDataWithResponse(response);
        }
        if (iPayBackView != null){
            iPayBackView.hideProgressBar();
        }
    }

    @Override
    public void onFail(String msg) {

        if (iPayBackView != null){
            iPayBackView.showFailureMsg(msg);
        }
        if (iPayBackView != null){
            iPayBackView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {
        if (iPayBackView != null){
            iPayBackView.relogin();
            iPayBackView.hideProgressBar();
        }
    }
}
