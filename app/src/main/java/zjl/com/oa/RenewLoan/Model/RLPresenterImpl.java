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
    private String token, type_id , remark,persion_court,car_break_rules,insurance,overtime_fee;
    private String  wk_point_id,workflow_content_id;
    private List<LocalMedia> files = new ArrayList<>();
    private boolean isUploading = false;
    private String loan_length;
    //评估报告需要字段
    private String car_year;
    private String car_type,car_style,milage,market_amount,take_amount;
    private HashMap<String, Object> map = new HashMap<>();

    public RLPresenterImpl(IRLView view) {
        this.irlView = view;
        this.irlModel = new RLModelImpl();
    }

    @Override
    public void onSucceed() {
        if (irlView != null && !isUploading){
            irlView.hideProgress();
            irlView.toMainActivity();
            if (wk_point_id != null && wk_point_id.equals("27")){
                irlView.saveOperationState("1");
            }
        }else{
            switch (wk_point_id){
                case "3"://信息核查
                    irlModel.InfoCheck( request_end_flag, UPLOAD_TYPE_ADD,map,
                            files.subList(files.size() / 2,files.size()),this );
                    break;
                case "4":
                    irlModel.CarPhoto( request_end_flag, UPLOAD_TYPE_ADD,map,
                            type_id,files.subList(files.size() / 2,files.size()),this);//车辆照片
                    break;
                case "5":
                    irlModel.PleDgeAssess( request_end_flag, UPLOAD_TYPE_ADD,
                            map,files.subList(files.size() / 2,files.size()),this);//评估报告
                    break;
                case "8"://实地考察
                case "15"://签约
                case "17"://GPS/钥匙
                case "18"://抵押登记
                case "21"://首扣通知
                case "26"://展期费续贷
                case "32"://上传合同续贷
                    irlModel.UploadCarPhoto( request_end_flag, UPLOAD_TYPE_ADD, map,
                            type_id,files.subList(files.size() / 2,files.size()),this);
                    break;
                case "23"://信息核查续贷
                    irlModel.InfoCheckRefinance( request_end_flag , UPLOAD_TYPE_ADD, map,
                            files.subList(files.size() / 2,files.size()),this);
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
    public void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark) {
        if (irlModel != null){
            irlModel.endWorkFlow( token,  workflow_content_id, wk_point_id,remark,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void InfoCheck(String request_end_flag, String uploadType, HashMap<String ,Object> map, List<LocalMedia> files) {

        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }


        String token = map.get("token").toString();
        String workflow_content_id = map.get("w_con_id").toString();
        String wk_point_id = map.get("w_pot_id").toString();


        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (files.size() <= 0 && !irlView.isUploadTypeAdd()){
            if (irlView != null){
                irlView.showFailureMsg("请选择上传文件");
            }
            return;
        }

        if (irlModel != null){
            if (files.size() <= 1){
                irlModel.InfoCheck( request_end_flag,uploadType,map,files,this);
                isUploading = false;
            }else{
                irlModel.InfoCheck( request_end_flag,uploadType,map, files.subList(0,files.size() / 2),this);
                this.wk_point_id = wk_point_id;
                isUploading = true;
            }
        }

        if (irlView != null){
            irlView.showProgress();
        }

        this.map.clear();
        this.map.putAll(map);

        this.token = token;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;

        this.files.clear();
        this.files.addAll(files);
    }

    @Override
    public void FinishFlow(HashMap<String ,Object> map) {

        if (map == null || map.size() <=0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.FinishFlow(map,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void loanApplication(HashMap<String ,Object> map) {

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.loanApplication(map,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void InformSigned(HashMap<String ,Object> map) {
        if(map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }
        if (irlModel != null ){
            irlModel.InformSigned( map,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void BusFeedback(HashMap<String,Object> map) {
        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }
        if (irlModel != null){
            irlModel.BusFeedback(map,this );
            irlView.showProgress();
        }
    }

    @Override
    public void FirstFeedback(HashMap<String ,Object> map) {

        if(map == null || map.size() <=0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.FirstFeedback(map,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void PleDgeAssess(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                             List<LocalMedia> files) {

        if(map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (files.size() <=0 && !irlView.isUploadTypeAdd()){
            irlView.showFailureMsg("请选择上传文件");
            return;
        }

        if (irlModel != null){
            if (files.size() <= 1){
                irlModel.PleDgeAssess( request_end_flag, uploadType,
                        map,files,this);

                this.isUploading = false;
            }else{
                irlModel.PleDgeAssess( request_end_flag, uploadType,
                        map,files.subList(0,files.size() / 2),this);

                this.isUploading = true;

            }
        }

        if (irlView != null){
            irlView.showProgress();
        }

        String token = map.get("token").toString();
        String workflow_content_id = map.get("w_con_id").toString();
        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.token = token;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;

        this.files.clear();
        this.files.addAll(files);
    }

    @Override
    public void Coming(HashMap<String, Object> map) {
        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //不对备注进行强制检查，可以提交空字符串
            if (key.equals("comment")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.Coming(map,this );
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void ApplyforRefinance(HashMap<String ,Object> map) {
        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.ApplyforRefinance(map,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void CarPhoto(String request_end_flag,String uploadType,HashMap<String ,Object> map,String type_id,List<LocalMedia> files) {
        if (map == null){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String token = map.get("token").toString();
        String workflow_content_id = map.get("w_con_id").toString();
        String wk_point_id = map.get("w_pot_id").toString();

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (files.size() <=0 && irlView != null && uploadType.equals(UPLOAD_TYPE_NORMAL)){
            irlView.showFailureMsg("请选择文件");
            return;
        }


        if (irlModel != null){
            if (files.size() <= 1){
                irlModel.CarPhoto( request_end_flag, uploadType,map,type_id,files,this);
                this.isUploading = false;
            }else{
                irlModel.CarPhoto( request_end_flag, uploadType,map,type_id,files.subList(0,files.size() / 2),
                        this);
                this.wk_point_id = wk_point_id;
                this.isUploading = true;
            }
            irlView.showProgress();
        }

        this.map.clear();
        this.map.putAll(map);

        this.token = token;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;

        this.files.clear();
        this.files.addAll(files);

        this.type_id = type_id;
    }
    @Override
    public void FirstSureAmount(HashMap<String ,Object> map) {
        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.FirstSureAmount(map,this );
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void SureAmount(HashMap<String ,Object> map) {
        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            //对降额意见不做强制检查，可提交空字符串
            if (key.equals("derating_opinion")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.SureAmount(map,this);
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
    public void InputInfo(HashMap<String ,Object> map) {
        if (map == null || map.size() <=0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        //录入资料不对信息做检查
//        for (String key :map.keySet()){
//            //对备注不做强制检查，可提交空字符串
//            if (key.equals("remark")){
//                continue;
//            }
//
//            if (map.get(key) == null || map.get(key).toString().length() <=0){
//                irlView.showFailureMsg("请确认信息填写完整");
//                return;
//            }
//        }

        if (irlModel != null){
            irlModel.InputInfo( map,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void UploadCarPhoto(String request_end_flag,String uploadType,HashMap<String ,Object> map, String type_id, List<LocalMedia> files) {
        //续贷展期费，不用上传
//        if ((remark == null || "".equals(remark))
//                && !"10".equals(type_id)
//                && !"11".equals(type_id)){
//            irlView.showFailureMsg("请输入备注");
//            return;
//        }

        String token = map.get("token").toString();
        String workflow_content_id = map.get("w_con_id").toString();
        String wk_point_id = map.get("w_pot_id").toString();

        if ((files == null || files.size() <= 0 ) && !"11".equals(type_id)
                && !irlView.isUploadTypeAdd()){
            irlView.showFailureMsg("请选择文件");

            return;
        }
        if (irlModel != null){
            if (files != null && files.size() <= 1){
                irlModel.UploadCarPhoto( request_end_flag, uploadType, map,
                        type_id,files,this);
                this.isUploading = false;
            }else{
                irlModel.UploadCarPhoto( request_end_flag, uploadType, map,
                        type_id,files.subList(0,files.size() / 2),this);
                this.isUploading = true;
            }
        }

        if (irlView != null){
            irlView.showProgress();
        }

        this.map.clear();
        this.map.putAll(map);

        this.token = token;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;

        this.type_id = type_id;

        this.files.clear();
        this.files.addAll(files);

    }

    @Override
    public void ContractDetail(HashMap<String ,Object> map) {
        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.ContractDetail( map,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void AuditRefinance(HashMap<String ,Object> map) {

        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        for (String key :map.keySet()){
            //对备注做强制检查
//            if (key.equals("remark")){
//                continue;
//            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.AuditRefinance(map,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }
    @Override
    public void AssessRefinance(HashMap<String ,Object> map) {

        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }
        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlModel != null){
            irlModel.AssessRefinance(map,this);
        }

        if (irlView != null){
            irlView.showProgress();
        }
    }

//    @Override
//    public void InfoCheckRefinance(String request_end_flag ,String uploadType,String token,
//                                   int workflow_content_id,int wk_point_id,
//                                   String persion_court, String car_break_rules,String insurance,
//                                   String remark,List<LocalMedia> files) {
//
//        if ("".equals(persion_court)){
//            if (irlView != null){
//                irlView.showFailureMsg("请输入人法查询结果");
//            }
//            return;
//        }
//        if ("".equals(car_break_rules)){
//            if (irlView != null){
//                irlView.showFailureMsg("请输入违章查询结果");
//            }
//            return;
//        }
//        if ("".equals(insurance)){
//            if (irlView != null){
//                irlView.showFailureMsg("请输入保险查询结果");
//            }
//            return;
//        }
//        if (null == files || files.size() <= 0
//                && !irlView.isUploadTypeAdd()){
//            if (irlView != null){
//                irlView.showFailureMsg("请选择要上传的照片");
//            }
//            return;
//        }
//
//        if (irlModel != null){
//
//            if (files.size() <= 1){
//                irlModel.InfoCheckRefinance( request_end_flag , uploadType, token,  workflow_content_id+"",
//                        wk_point_id+"", persion_court,  car_break_rules,
//                        insurance, remark,files,this);
//                this.isUploading = false;
//            }else{
//                irlModel.InfoCheckRefinance( request_end_flag , uploadType, token,  workflow_content_id+"",
//                        wk_point_id+"", persion_court,  car_break_rules,
//                        insurance, remark,files.subList(0,files.size() / 2),this);
//                this.isUploading = true;
//            }
//        }
//        if (irlView != null){
//            irlView.showProgress();
//        }
//
//        this.token = token;
//        this.workflow_content_id = workflow_content_id+"";
//        this.remark = remark;
//        this.wk_point_id = wk_point_id+"";
//        this.persion_court = persion_court;
//        this.car_break_rules = car_break_rules;
//        this.insurance = insurance;
//        this.remark = remark;
//        this.files.clear();
//        this.files.addAll(files);
//
//    }
    @Override
    public void InfoCheckRefinance(String request_end_flag ,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files) {


        String token = map.get("token").toString();
        String workflow_content_id = map.get("w_con_id").toString();
        String wk_point_id = map.get("w_pot_id").toString();

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
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
                irlModel.InfoCheckRefinance( request_end_flag , uploadType, map,files,this);
                this.isUploading = false;
            }else{
                irlModel.InfoCheckRefinance( request_end_flag , uploadType, map,files.subList(0,files.size() / 2),this);
                this.isUploading = true;
            }
        }
        if (irlView != null){
            irlView.showProgress();
        }

        this.map.clear();
        this.map.putAll(map);

        this.token = token;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;

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
