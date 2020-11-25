package zjl.com.oa2.Meeting.Model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import zjl.com.oa2.Meeting.Presenter.IEnterOrderView;
import zjl.com.oa2.Meeting.Presenter.IMettingListener;
import zjl.com.oa2.Meeting.Presenter.IMettingModel;
import zjl.com.oa2.Meeting.Presenter.IMettingPresenter;
import zjl.com.oa2.Meeting.Presenter.IMettingView;
import zjl.com.oa2.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public class MettingPresenterImpl implements IMettingListener,IMettingPresenter{
    private IMettingModel mettingModel;
    private IEnterOrderView enterOrderView;
    private IMettingView mettingView;

    public MettingPresenterImpl(IMettingView mettingView) {
        this.mettingView = mettingView;
        this.mettingModel = new MettingModelImpl();
    }

    public MettingPresenterImpl(IEnterOrderView enterOrderView) {
        this.mettingModel = new MettingModelImpl();
        this.enterOrderView = enterOrderView;
    }

    @Override
    public void onSucceed() {
        if (enterOrderView != null){
            enterOrderView.toMainActivity();
        }
        if (enterOrderView != null){
            enterOrderView.hideProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }
    @Override
    public void onFail(String arg) {
        if (mettingView != null){
            mettingView.showFailureMsg(arg);
            mettingView.hideProgressBar();
        }
        if (enterOrderView != null){
            enterOrderView.showFailureMsg(arg);
            enterOrderView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {
        if (mettingView != null){
            mettingView.relogin();
            mettingView.hideProgressBar();
        }
        if (enterOrderView != null){
            enterOrderView.relogin();
            enterOrderView.hideProgressBar();
        }
    }

    @Override
    public void onEndWKSucceed() {
        if (mettingView != null){
            mettingView.toMainActivity();
        }
        if (mettingView != null){
            mettingView.hideProgressBar();
        }
    }

    @Override
    public void onEndWKFail(String arg) {
        if (mettingView != null){
            mettingView.showFailureMsg(arg);
        }
        if (mettingView != null){
            mettingView.hideProgressBar();
        }
    }

    @Override
    public void onSucceed(LookInterviewResponse.Result result) {

        if (mettingView != null){
            mettingView.refreshData(result);
            mettingView.hideProgressBar();
        }
    }

    @Override
    public void LookInterview(String token, String w_con_id) {

        if (mettingModel != null){
            mettingModel.LookInterview( token,  w_con_id, this);
        }
        if (mettingView != null){
            mettingView.showProgressBar();
        }
    }

    @Override
    public void endWorkFlow(String token, int workflow_content_id, int wk_point_id,String remark) {
        if (mettingModel != null){
            mettingModel.endWorkFlow( token,  workflow_content_id, wk_point_id,remark, this);
        }
        if (mettingView != null){
            mettingView.showProgressBar();
        }
    }

    @Override
    public void Interview(String token, int workflow_content_id, int wk_point_id,

                          List<LocalMedia> photos, String remark,
//                   List<LocalMedia> photos, String product_type_name, String remark,

                          String customer_name, String identity, String customer_phone,
                          String address, String bank_code, String bank_name,

                          String purpose,
//                   double loan_rate, String purpose, int loan_length, String return_amount_method,
                          String car_license, String car_registration, String car_engine_no, String car_vin) {
//        if ("".equals(customer_name)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入客户姓名");
//            }
//            return;
//        }
//        if ("".equals(identity)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入身份证号");
//            }
//            return;
//        }
//        if (!IDCardUtil.IDCardValidate(identity)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请验证身份证号是否正确");
//            }
//            return;
//        }
//        if ("".equals(customer_phone)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入手机号");
//            }
//            return;
//        }
//        if (!PhoneUtils.isChinaPhoneLegal(customer_phone)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请验证手机号是否正确");
//            }
//            return;
//        }
//        if ("".equals(address)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入现住址");
//            }
//            return;
//        }
//        if ("".equals(bank_code)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入开户行");
//            }
//            return;
//        }
//        if ("".equals(bank_name)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入银行卡号");
//            }
//            return;
//        }
//        if (0.00 == loan_rate){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入贷款利率");
//            }
//            return;
//        }
//        if ("".equals(purpose)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入贷款用途");
//            }
//            return;
//        }
//        if (0 == loan_length){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入贷款期限");
//            }
//            return;
//        }
//        if ("".equals(return_amount_method)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请选择还款方式");
//            }
//            return;
//        }
//        if ("".equals(car_license)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入车辆牌照");
//            }
//            return;
//        }
//        if ("".equals(car_registration)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入车辆登记号");
//            }
//            return;
//        }
//        if ("".equals(car_engine_no)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入车辆发动机号");
//            }
//            return;
//        }
//        if ("".equals(car_vin)){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("请输入车架号");
//            }
//            return;
//        }
//        if (photos.size() <= 0){
//            if (enterOrderView != null){
//                enterOrderView.showFailureMsg("面签表未选择");
//            }
//            return;
//        }
        if (mettingModel != null){
            mettingModel.Interview(
                     token,  workflow_content_id,  wk_point_id,

                     photos,  remark,
//                   photos, product_type_name,remark,

                     customer_name,  identity,  customer_phone,
                     address,  bank_code,  bank_name,

                     purpose,
//                    loan_rate,  purpose,  loan_length,  return_amount_method,
                     car_license,  car_registration,  car_engine_no,  car_vin,
                    this);
        }

        if (enterOrderView != null){
            enterOrderView.showProgressBar();
        }
    }

    @Override
    public void InputInfo(String token, int w_con_id, int w_pot_id,

                          String customer_name, String identity, String customer_phone,
                          String address, String bank_code, String bank_name,

                          String purpose,
//                          double loan_rate, String purpose, int loan_length, String return_amount_method,
                          String car_license, String car_registration, String car_engine_no, String car_vin,

                          String remark) {

        if (mettingModel != null){
            mettingModel.InputInfo( token,  w_con_id, w_pot_id,
                    customer_name,  identity,  customer_phone,address,  bank_code,bank_name,
                    purpose,
//                    loan_rate,  purpose,  loan_length,  return_amount_method,
                    car_license, car_registration,  car_engine_no, car_vin, remark,
                    this);
        }

        if (enterOrderView != null){
            enterOrderView.showProgressBar();
        }
    }

    @Override
    public void onDestory() {
        if (enterOrderView != null){
            enterOrderView = null;
        }

        if (mettingView != null){
            mettingView = null;
        }
    }
}
