package zjl.com.oa2.Sign.Model;

import java.util.List;

import zjl.com.oa2.Adapter.SigningAdapter;
import zjl.com.oa2.Sign.Presenter.IInformSignListener;
import zjl.com.oa2.Sign.Presenter.IInformSignModel;
import zjl.com.oa2.Sign.Presenter.IInformSignPresenter;
import zjl.com.oa2.Sign.Presenter.IInformSignView;
import zjl.com.oa2.Response.GetSigningResponse;

import static zjl.com.oa2.Utils.DateCheck.isValidDate;

/**
 * Created by Administrator on 2018/3/5.
 */

public class InformSignPresenterImpl implements IInformSignListener,IInformSignPresenter {
    private IInformSignView informSignView;
    private IInformSignModel informSignModel;
    private SigningAdapter adapter;
    private String wk_point_id;

    public InformSignPresenterImpl(IInformSignView informSignView,SigningAdapter adapter) {
        this.informSignModel = new InformSignModelImpl();
        this.informSignView = informSignView;
        this.adapter = adapter;
    }

    @Override
    public void getInformSign(String token, String workflow_content_id) {

        if (informSignModel != null ){
            informSignModel.getInformSign(token,workflow_content_id,this);
        }

        if (informSignView != null){
            informSignView.showProgressBar();
        }
    }

    @Override
    public void postInformSign(String token,String workflow_content_id,String wk_point_id,
                               String service_fee,String pontage,
                               String contract_date,String remark) {
        if ("".equals(service_fee.trim())){
            if (informSignView != null){
                informSignView.showFailureMsg("请输入服务费");
            }
            return;
        }
        if ("".equals(pontage.trim())){
            if (informSignView != null){
                informSignView.showFailureMsg("请输入过桥费");
            }
            return;
        }
        if ("".equals(contract_date.trim())){
            if (informSignView != null){
                informSignView.showFailureMsg("请输入合同日期");
            }
            return;
        }else if (!isValidDate(contract_date)){
            if (informSignView != null){
                informSignView.showFailureMsg("合同日期格式为YYYY-MM-DD");
            }
            return;
        }

        if (informSignModel != null ){
            this.wk_point_id = wk_point_id;
            informSignModel.postInformSign( token, workflow_content_id, wk_point_id,
                     service_fee, pontage,
                     contract_date, remark,this);
        }

        if (informSignView != null){
            informSignView.showProgressBar();
        }
    }

    @Override
    public String getPontage() {
        return adapter.getPontage();
    }


    @Override
    public String getServiceFee(){
        return adapter.getServiceFee();
    }

    @Override
    public String getContractDate(){
        return adapter.getContractDate();
    }
    @Override
    public void onDestory() {

    }

    @Override
    public void onSucceed() {
        if (informSignView != null){
            informSignView.toMainActivity();
            informSignView.hideProgressBar();
            
            if (wk_point_id.equals("13")){
                informSignView.saveOperationState("1");
            }
        }
    }

    @Override
    public void onSucceed(List<GetSigningResponse.Result.Data> data) {

        if (informSignView != null){
            informSignView.refreshData(data);
        }

        if (informSignView != null){
            informSignView.hideProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onFail(String arg) {

        if (informSignView != null){
            informSignView.showFailureMsg(arg);
        }

        if (informSignView != null){
            informSignView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {

        if (informSignView != null){
            informSignView.relogin();
        }

        if (informSignView != null){
            informSignView.hideProgressBar();
        }
    }
}
