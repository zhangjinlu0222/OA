package zjl.com.oa.RenewLoan.Model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zjl.com.oa.RenewLoan.Presenter.IRLListener;
import zjl.com.oa.RenewLoan.Presenter.IRLModel;
import zjl.com.oa.RenewLoan.Presenter.IRLPresenter;
import zjl.com.oa.RenewLoan.Presenter.IRLView;
import zjl.com.oa.RenewLoan.View.RenewLoanActivity;
import zjl.com.oa.Response.FormResponse;

import static zjl.com.oa.Base.BaseActivity.UPLOAD_TYPE_ADD;
import static zjl.com.oa.Base.BaseActivity.UPLOAD_TYPE_NORMAL;
import static zjl.com.oa.Base.BaseActivity.request_end_flag;
import static zjl.com.oa.Utils.DateCheck.isValidDate;

/**
 * Created by Administrator on 2018/3/5.
 */

public class RLPresenterImpl implements IRLPresenter,IRLListener {

    private IRLView irlView;
    private IRLModel irlModel;
    private String token, type_id , remark,persion_court,car_break_rules,insurance;
    private int wk_point_id,workflow_content_id;
    private List<LocalMedia> files = new ArrayList<>();
    private boolean isUploading = false;
    private String loan_length;
    //评估报告需要字段
    private int car_year;
    private String car_type,car_style,milage,market_amount,take_amount;

    public RLPresenterImpl(IRLView view) {
        this.irlView = view;
        this.irlModel = new RLModelImpl();
    }

    @Override
    public void onSucceed() {
        if (irlView != null && !isUploading){
            irlView.hideProgress();
            irlView.toMainActivity();
            if (wk_point_id == 27){
                irlView.saveOperationState("1");
            }
        }else{
            switch (wk_point_id){
                case 4:
                    irlModel.CarPhoto( request_end_flag, UPLOAD_TYPE_ADD,token,remark,
                            workflow_content_id, wk_point_id ,
                            files.subList(files.size() / 2,files.size()),
                            type_id,loan_length,this);//车辆照片
                    break;
                case 5:
                    irlModel.PleDgeAssess( request_end_flag, UPLOAD_TYPE_ADD,
                            token,car_year,car_type,car_style,milage,
                            remark,market_amount,take_amount,workflow_content_id,wk_point_id,
                            files.subList(files.size() / 2,files.size()),this);//评估报告
                    break;
                case 8://实地考察
                case 15://签约
                case 18://抵押登记
                case 21://首扣通知
                case 26:
                    irlModel.UploadCarPhoto( request_end_flag, UPLOAD_TYPE_ADD, token,  workflow_content_id,  remark,  wk_point_id,
                            type_id,files.subList(files.size() / 2,files.size()),this);//展期费续贷
                    break;
                case 23:
                    irlModel.InfoCheckRefinance( request_end_flag , UPLOAD_TYPE_ADD, token,  workflow_content_id,
                            wk_point_id, persion_court,  car_break_rules,
                            insurance, remark,files.subList(files.size() / 2,files.size()),this);
                    break;
            }
        }
        this.isUploading = false;
    }

    @Override
    public void onFail(String msg) {

        if (irlView != null){
            irlView.hideProgress();
            irlView.showFailureMsg(msg);
        }
        this.isUploading = false;
    }

    @Override
    public void relogin() {

        if (irlView != null){
            irlView.hideProgress();
            irlView.relogin();
        }
        this.isUploading = false;
    }

    @Override
    public void onFail() {
        this.onFail("操作失败，请重试");
    }


    @Override
    public void loanApplication(String token, int w_con_id, int w_pot_id, String remark) {

        if (irlModel != null){
            irlModel.loanApplication(token,w_con_id,w_pot_id,remark,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void InformSigned(String token,String workflow_content_id,String wk_point_id,
                               String service_fee,String pontage,
                               String contract_date,String remark) {
        if ("".equals(service_fee.trim())){
            if (irlView != null){
                irlView.showFailureMsg("请输入服务费");
            }
            return;
        }
        if ("".equals(pontage.trim())){
            if (irlView != null){
                irlView.showFailureMsg("请输入过桥费");
            }
            return;
        }
        if ("".equals(contract_date.trim())){
            if (irlView != null){
                irlView.showFailureMsg("请输入合同日期");
            }
            return;
        }else if (!isValidDate(contract_date)){
            if (irlView != null){
                irlView.showFailureMsg("合同日期格式为YYYY-MM-DD");
            }
            return;
        }

        if (irlModel != null ){
            this.wk_point_id = Integer.parseInt(wk_point_id);
            irlModel.InformSigned( token, workflow_content_id, wk_point_id,
                    service_fee, pontage,
                    contract_date, remark,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void BusFeedback(String token, String w_con_id, String w_pot_id, int loan_length, String  loan_rate, String return_amount_method, String remark) {
        if (loan_length == 0){
            this.onFail("请输入贷款期限");
            return;
        }
        if (loan_rate.length() <= 0){
            this.onFail("请输入贷款利率");
            return;
        }
        if (return_amount_method == null || "".equals(return_amount_method)){
            this.onFail("请选择还款方式");
            return;
        }

        if (irlModel != null){
            irlModel.BusFeedback( token,  w_con_id,  w_pot_id,  loan_length,  loan_rate,
                    return_amount_method, remark,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void FirstFeedback(String token, String w_con_id, String w_pot_id, String remark) {
        if (irlModel != null){
            irlModel.FirstFeedback(token,w_con_id,w_pot_id,remark,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void PleDgeAssess(String request_end_flag, String uploadType, String token, int car_year,
                             String car_type, String car_style, String milage, String remark,
                             String market_amount, String take_amount, int workflow_content_id,
                             int wk_point_id, List<LocalMedia> files) {

        if (files.size() <=0 && !irlView.isUploadTypeAdd()){
            irlView.showFailureMsg("请选择上传文件");
            return;
        }

        if (car_year <= 0){
            irlView.showFailureMsg("请输入车辆年份");
            return;
        }

        if ("".equals(car_type)){
            irlView.showFailureMsg("请输入车辆品牌");
            return;
        }

        if ("".equals(car_style)){
            irlView.showFailureMsg("请输入车辆型号");
            return;
        }
        if ("".equals(milage)){
            irlView.showFailureMsg("请输入公里数");
            return;
        }

        if ("".equals(take_amount)){
            irlView.showFailureMsg("请输入收车价");
            return;
        }

        if ("".equals(market_amount)){
            irlView.showFailureMsg("请输入市场价");
            return;
        }

        if (irlModel != null){
            if (files.size() <= 1){
                irlModel.PleDgeAssess( request_end_flag, uploadType,
                        token,car_year,car_type,car_style,milage,
                        remark,market_amount,take_amount,
                        workflow_content_id,wk_point_id,files,this);

                this.isUploading = false;
            }else{
                irlModel.PleDgeAssess( request_end_flag, uploadType,
                        token,car_year,car_type,car_style,milage,
                        remark,market_amount,take_amount,
                        workflow_content_id,wk_point_id,files.subList(0,files.size() / 2),this);

                this.isUploading = true;

            }
        }

        if (irlView != null){
            irlView.showProgress();
        }

        this.token = token;
        this.car_year = car_year;
        this.car_type = car_type;
        this.car_style = car_style;
        this.milage = milage;
        this.remark = remark;
        this.market_amount = market_amount;
        this.take_amount = take_amount;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;
        this.files.addAll(files);
    }

    @Override
    public void Coming(HashMap<String, Object> map) {
        if (irlModel != null){
            irlModel.Coming(map,this );
            irlView.showProgress();
        }
    }

    @Override
    public void ApplyforRefinance(String token, String w_con_id, String w_pot_id,
                                  String loan_length, String remark) {
        if ( loan_length == null || "".equals(loan_length)){
            if (irlView != null){
                irlView.showFailureMsg("请输入借款周期");
            }
            return;
        }
//        if ( remark == null || "".equals(remark)){
//            if (irlView != null){
//                irlView.showFailureMsg("请输入备注");
//            }
//            return;
//        }

        if (irlModel != null){
            irlModel.ApplyforRefinance(token,w_con_id,w_pot_id,loan_length,remark,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void CarPhoto(String request_end_flag,String uploadType,String token, String remark,
                             int workflow_content_id, int wk_point_id, List<LocalMedia> files,
                             String type_id,String loan_length ) {

        if (files.size() <=0 && irlView != null && uploadType.equals(UPLOAD_TYPE_NORMAL)){
            irlView.showFailureMsg("请选择文件");
            return;
        }

        if (loan_length == null || loan_length.length() <= 0){
            irlView.showFailureMsg("请输入借款周期");
            return;
        }

//        if (remark == null || remark.length() <= 0){
//            irlView.showFailureMsg("请输入备注");
//            return;
//        }

        if (irlModel != null){
            if (files.size() <= 1){
                irlModel.CarPhoto( request_end_flag, uploadType,token,remark,
                        workflow_content_id, wk_point_id ,files,type_id,loan_length,this);
                this.isUploading = false;
            }else{
                irlModel.CarPhoto( request_end_flag, uploadType,token,remark,
                        workflow_content_id, wk_point_id ,files.subList(0,files.size() / 2),
                        type_id,loan_length,this);
                this.isUploading = true;
            }
            irlView.showProgress();
        }

        this.token = token;
        this.remark = remark;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;
        this.files.addAll(files);
        this.type_id = type_id;
        this.loan_length = loan_length;
    }
    @Override
    public void FirstSureAmount(String token, String w_con_id, String w_pot_id,
                                String amount, String remark) {
        if ( amount == null || "".equals(amount)){
            if (irlView != null){
                irlView.showFailureMsg("请输入评估范围");
            }
            return;
        }

        if (irlModel != null){
            irlModel.FirstSureAmount(token,w_con_id,w_pot_id,amount,remark,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void SureAmount(String token, String w_con_id, String w_pot_id,
                           String amount,String assure_amount,String derating_opinion, String remark) {
        if (!(amount != null && amount.length() > 0 && Double.parseDouble(amount) > 0)){
            if (irlView != null){
                irlView.showFailureMsg("请输入评估定额");
            }
            return;
        }
        if (!(assure_amount != null && assure_amount.length() > 0 && Double.parseDouble(assure_amount) >= 0)){
            if (irlView != null){
                irlView.showFailureMsg("请输入担保价");
            }
            return;
        }
        if (irlModel != null){
            irlModel.SureAmount(token,w_con_id,w_pot_id,amount,assure_amount,derating_opinion,remark,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void SureAmountReturn(String token, String w_con_id, String w_pot_id, String type_id) {
        if ("0".equals(type_id)){
            if (irlView != null){
                irlView.showFailureMsg("请选择需要上传的类型");
            }
            return;
        }
        if (irlModel != null){
            irlModel.SureAmountReturn(token,w_con_id, w_pot_id,  type_id,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void InputInfo(String token, int w_con_id, int w_pot_id,

                          String customer_name, String identity, String customer_phone,
                          String address, String bank_code, String bank_name,

                          String purpose,
                          String car_license, String car_registration, String car_engine_no, String car_vin,

                          String remark) {

        if (irlModel != null){
            irlModel.InputInfo( token,  w_con_id, w_pot_id,
                    customer_name,  identity,  customer_phone,address,  bank_code,bank_name,
                    purpose,
                    car_license, car_registration,  car_engine_no, car_vin, remark,
                    this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }
    @Override
    public void UploadCarPhoto(String request_end_flag,String uploadType,String token, int workflow_content_id, String remark,
                               int wk_point_id, String type_id, List<LocalMedia> files) {
        //续贷展期费，不用上传
//        if ((remark == null || "".equals(remark))
//                && !"10".equals(type_id)
//                && !"11".equals(type_id)){
//            irlView.showFailureMsg("请输入备注");
//            return;
//        }
        if ((files == null || files.size() <= 0 ) && !"11".equals(type_id)
                && !irlView.isUploadTypeAdd()){
            irlView.showFailureMsg("请选择文件");

            return;
        }
        if (irlModel != null){
            if (files.size() <= 1){
                irlModel.UploadCarPhoto( request_end_flag, uploadType, token,  workflow_content_id,  remark,  wk_point_id,
                        type_id,files,this);
                this.isUploading = false;
            }else{
                irlModel.UploadCarPhoto( request_end_flag, uploadType, token,  workflow_content_id,  remark,  wk_point_id,
                        type_id,files.subList(0,files.size() / 2),this);
                this.isUploading = true;
            }
        }

        if (irlView != null){
            irlView.showProgress();
        }

        this.token = token;
        this.workflow_content_id = workflow_content_id;
        this.remark = remark;
        this.wk_point_id = wk_point_id;
        this.type_id = type_id;
        this.files.clear();
        this.files.addAll(files);

    }

    @Override
    public void ContractDetail(String token, int workflow_content_id, int wk_point_id, String remark,
                               String amount, String loan_rate, String loan_length, String pontage,
                               String service_fee, String insurance, String car_break_rules,
                               String contract_date) {

        if (amount == null || "".equals(amount)){
            irlView.showFailureMsg("请输入金额");
            return;
        }
        if (loan_rate == null || "".equals(loan_rate) ){
            irlView.showFailureMsg("请输入利息");
            return;
        }

        if (loan_length == null || "".equals(loan_length)){
            irlView.showFailureMsg("请输入期限");
            return;
        }
        if (pontage == null || "".equals(pontage)){
            irlView.showFailureMsg("请输入展期费");
            return;
        }
        if (service_fee == null || "".equals(service_fee) ){
            irlView.showFailureMsg("请输入服务费");
            return;
        }

        if (insurance == null || "".equals(insurance)){
            irlView.showFailureMsg("请输入保险");
            return;
        }
        if (car_break_rules == null || "".equals(car_break_rules)){
            irlView.showFailureMsg("请输入违章");
            return;
        }

        if (irlModel != null){
            this.wk_point_id = wk_point_id;
            irlModel.ContractDetail( token,  workflow_content_id,  wk_point_id,  remark,
                     amount,  loan_rate,  loan_length,  pontage,
                     service_fee,  insurance,  car_break_rules, contract_date,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void AuditRefinance(String token, int workflow_content_id, int wk_point_id, String remark) {
        if ("".equals(remark)){
            irlView.showFailureMsg("请输入审核结果");
            return;
        }

        if (irlModel != null){
            irlModel.AuditRefinance(token,workflow_content_id,wk_point_id,remark,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }
    @Override
    public void AssessRefinance(String token, int workflow_content_id, int wk_point_id, String file_info, String derate_info, String remark) {

        if ("".equals(file_info) || file_info == null){
            irlView.showFailureMsg("请输入档案信息");
            return;
        }

        if ("".equals(derate_info) || derate_info == null){
            irlView.showFailureMsg("请输入降额意见");
            return;
        }

//        if ("".equals(remark)){
//            evaluationView.showFailureMsg("请输入备注");
//            return;
//        }

        if (irlModel != null){
            irlModel.AssessRefinance(token,workflow_content_id,wk_point_id,file_info,derate_info,remark,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void InfoCheckRefinance(String request_end_flag ,String uploadType,String token,
                                   int workflow_content_id,int wk_point_id,
                                   String persion_court, String car_break_rules,String insurance,
                                   String remark,List<LocalMedia> files) {

        if ("".equals(persion_court)){
            if (irlView != null){
                irlView.showFailureMsg("请输入人法查询结果");
            }
            return;
        }
        if ("".equals(car_break_rules)){
            if (irlView != null){
                irlView.showFailureMsg("请输入违章查询结果");
            }
            return;
        }
        if ("".equals(insurance)){
            if (irlView != null){
                irlView.showFailureMsg("请输入保险查询结果");
            }
            return;
        }
        if (null == files || files.size() <= 0
                && !irlView.isUploadTypeAdd()){
            if (irlView != null){
                irlView.showFailureMsg("请选择要上传的照片");
            }
            return;
        }

        if (irlModel != null){

            if (files.size() <= 1){
                irlModel.InfoCheckRefinance( request_end_flag , uploadType, token,  workflow_content_id,
                        wk_point_id, persion_court,  car_break_rules,
                        insurance, remark,files,this);
                this.isUploading = false;
            }else{
                irlModel.InfoCheckRefinance( request_end_flag , uploadType, token,  workflow_content_id,
                        wk_point_id, persion_court,  car_break_rules,
                        insurance, remark,files.subList(0,files.size() / 2),this);
                this.isUploading = true;
            }
        }
        if (irlView != null){
            irlView.showProgress();
        }

        this.token = token;
        this.workflow_content_id = workflow_content_id;
        this.remark = remark;
        this.wk_point_id = wk_point_id;
        this.persion_court = persion_court;
        this.car_break_rules = car_break_rules;
        this.insurance = insurance;
        this.remark = remark;
        this.files.clear();
        this.files.addAll(files);

    }

    @Override
    public void Form(String token, int workflow_content_id,int wk_point_id) {
        if (irlModel != null){
            irlModel.Form( token,  workflow_content_id, wk_point_id,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void onSucceed(FormResponse.Result result) {

        if (irlView != null){
            irlView.loadForms(result);
            irlView.hideProgress();
        }
    }
}
