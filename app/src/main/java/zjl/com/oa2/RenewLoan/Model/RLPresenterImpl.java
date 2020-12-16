package zjl.com.oa2.RenewLoan.Model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import zjl.com.oa2.QuestAndSetting.Presenter.ISearchModel;
import zjl.com.oa2.RenewLoan.Presenter.IRLListener;
import zjl.com.oa2.RenewLoan.Presenter.IRLModel;
import zjl.com.oa2.RenewLoan.Presenter.IRLPresenter;
import zjl.com.oa2.RenewLoan.Presenter.IRLView;
import zjl.com.oa2.RenewLoan.View.RenewLoanActivity;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.ManagersResponse;
import zjl.com.oa2.Response.SearchResponse;
import zjl.com.oa2.Utils.IDCardUtil;
import zjl.com.oa2.Utils.PhoneUtils;

import static zjl.com.oa2.Base.BaseActivity.UPLOAD_TYPE_ADD;
import static zjl.com.oa2.Base.BaseActivity.UPLOAD_TYPE_NORMAL;
import static zjl.com.oa2.Base.BaseActivity.request_end_flag;
import static zjl.com.oa2.Utils.DateCheck.isValidDate;

/**
 * Created by Administrator on 2018/3/5.
 */

public class RLPresenterImpl implements IRLPresenter,IRLListener {

    private IRLView irlView;
    private IRLModel irlModel;
    private String type_id;
    private String  wk_point_id;
    private List<LocalMedia> files = new ArrayList<>();
    private boolean isUploading = false;
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
                case "4"://车辆照片
                    irlModel.CarPhoto( request_end_flag, UPLOAD_TYPE_ADD,map,
                            type_id,files.subList(files.size() / 2,files.size()),this);
                    break;
                case "5"://评估报告
                    irlModel.PleDgeAssess( request_end_flag, UPLOAD_TYPE_ADD,
                            map,files.subList(files.size() / 2,files.size()),this);
                    break;
                case "15"://签约
                    irlModel.Sign( request_end_flag, UPLOAD_TYPE_ADD,
                            map,files.subList(files.size() / 2,files.size()),this);
                    break;
                case "8"://实地考察
                case "17"://GPS/钥匙
                case "18"://抵押登记
                case "21"://首扣通知
                case "26"://展期费续贷
                case "32"://上传合同续贷
                    irlModel.UploadCarPhoto( request_end_flag, UPLOAD_TYPE_ADD, map,
                            this.type_id,this.files.subList(files.size() / 2,files.size()),this);
                    break;
                case "33"://华融信贷面谈
                    irlModel.HRInterview( request_end_flag, UPLOAD_TYPE_ADD, map,
                            files.subList(files.size() / 2,files.size()),this);
                    break;
                case "34"://华融信贷上传资料
                case "36"://华融信贷家访
                case "38"://华融信贷签约
                case "39"://华融信贷放款
                    irlModel.HRUploadData( request_end_flag, UPLOAD_TYPE_ADD, map,
                            files.subList(files.size() / 2,files.size()),this);
                    break;
                case "23"://信息核查续贷
                    irlModel.InfoCheckRefinance( request_end_flag , UPLOAD_TYPE_ADD, map,
                            files.subList(files.size() / 2,files.size()),this);
                    break;
                case "41"://过户办理
                    irlModel.TransferOwnership( request_end_flag, UPLOAD_TYPE_ADD, map,
                            files.subList(files.size() / 2,files.size()),this);
                    break;
            }
        }
        this.isUploading = false;
    }

    @Override
    public void onSucceed(SearchResponse.Result result) {
        if (irlView != null){
            irlView.saveAdvSecInfo(result);
            irlView.hideProgress();
        }
    }
    @Override
    public void onSucceed(ManagersResponse.Result result) {
        if (irlView != null){
            irlView.saveManagers(result);
            irlView.hideProgress();
        }
    }


    @Override
    public void AdvanceSecInfo() {
        if (irlModel != null){
            irlModel.AdvanceSecInfo(this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }

    @Override
    public void ManagerList(HashMap<String ,Object > map) {
        if (irlModel != null){
            irlModel.ManagerList(map,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
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
    public void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,String proc_type_id) {
        if (irlModel != null){
            irlModel.endWorkFlow( token,  workflow_content_id, wk_point_id,remark, proc_type_id,this);
        }
        if (irlView != null){
            irlView.showProgress();
        }
    }


    @Override
    public void Sign(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                     List<LocalMedia> files) {
        if (map == null){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);
        this.files.clear();
        this.files.addAll(files);

        this.wk_point_id = wk_point_id;


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

        if (files.size() <=0 && irlView != null && uploadType.equals(UPLOAD_TYPE_NORMAL) && !"35".equals(wk_point_id)){
            irlView.showFailureMsg("请选择文件");
            return;
        }

        if(irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.Sign( request_end_flag, uploadType,map,files,this);
            }else{
                this.isUploading = true;
                irlModel.Sign( request_end_flag, uploadType,map,files.subList(0,files.size() / 2),
                        this);
            }
        }
    }

    @Override
    public void TransferOwnership(String request_end_flag,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files) {

        if (map == null){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.files.clear();
        this.files.addAll(files);

        this.wk_point_id = wk_point_id;

        for (String key :map.keySet()){

            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if(key.equals("new_car_license")
                    && (map.get(key) == null
                    || map.get(key).toString().length() <=0)){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }

        }

        if (files.size() <=0 && irlView != null && uploadType.equals(UPLOAD_TYPE_NORMAL)){
            irlView.showFailureMsg("请选择文件");
            return;
        }

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.TransferOwnership( request_end_flag, uploadType,map,files,this);
            }else{
                this.isUploading = true;
                irlModel.TransferOwnership( request_end_flag, uploadType,map,files.subList(0,files.size() / 2),
                        this);
            }
        }
    }

    @Override
    public void HRInterview(String request_end_flag,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files) {

        if (map == null){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.files.clear();
        this.files.addAll(files);

        this.wk_point_id = wk_point_id;

        for (String key :map.keySet()){

            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }

            if(key.equals("length")
                    && (map.get(key) == null
                    || map.get(key).toString().length() <=0)){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }

            if(key.equals("identity_id")){

                if (map.get(key) == null
                        || map.get(key).toString().length() <=0){
                    irlView.showFailureMsg("请输入身份证号");
                    return;
                }

                if (map.get(key) != null
                         && !IDCardUtil.IDCardValidate(map.get(key).toString())){
                    irlView.showFailureMsg("请输入正确的身份证号");
                    return;
                }
            }

            if(key.equals("phone")){

                if (map.get(key) == null
                        || map.get(key).toString().length() <=0){
                    irlView.showFailureMsg("请输入手机号");
                    return;
                }

                if (map.get(key) != null
                         && !PhoneUtils.isChinaPhoneLegal(map.get(key).toString())){
                    irlView.showFailureMsg("请输入正确的手机号");
                    return;
                }
            }

            if(key.equals("purpose") && (map.get(key) == null || map.get(key).toString().length() <=0)){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
            if(key.equals("amount") && (map.get(key) == null || map.get(key).toString().length() <=0)){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }

        }

        if (files.size() <=0 && irlView != null && uploadType.equals(UPLOAD_TYPE_NORMAL)){
            irlView.showFailureMsg("请选择文件");
            return;
        }

        if (irlView != null ){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.HRInterview( request_end_flag, uploadType,map,files,this);
            }else{
                this.isUploading = true;
                irlModel.HRInterview( request_end_flag, uploadType,map,files.subList(0,files.size() / 2),
                        this);
            }
        }
    }

    @Override
    public void LookLoanDetail(HashMap<String ,Object> map) {

        if (map == null){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String workflow_content_id = map.get("w_con_id").toString();

        if (workflow_content_id == null || workflow_content_id.length() <=0){
            irlView.showFailureMsg("表单异常请重试");
            return;
        }

        if (irlModel != null){
            irlModel.LookLoanDetail(map,this);
            irlView.showProgress();
        }
    }

    @Override
    public void InfoCheck(String request_end_flag, String uploadType, HashMap<String ,Object> map, List<LocalMedia> files) {

        if (map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);
        this.files.clear();
        this.files.addAll(files);

        this.wk_point_id = wk_point_id;

        String is_litigation = map.get("is_litigation").toString();
        if (is_litigation != null && is_litigation.equals("0")){
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
        }



        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.InfoCheck( request_end_flag,uploadType,map,files,this);
            }else{
                this.isUploading = true;
                irlModel.InfoCheck( request_end_flag,uploadType,map, files.subList(0,files.size() / 2),this);
            }
        }
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.FinishFlow(map,this);
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.loanApplication(map,this);
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null ){
            irlModel.InformSigned( map,this);
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


        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.BusFeedback(map,this );
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.FirstFeedback(map,this);
        }
    }

    @Override
    public void PleDgeAssess(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                             List<LocalMedia> files) {

        if(map == null || map.size() <= 0){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.files.clear();
        this.files.addAll(files);

        this.wk_point_id = wk_point_id;

        for (String key :map.keySet()){
            //对备注不做强制检查，可提交空字符串
            if (key.equals("remark")){
                continue;
            }
            //对减额原因不做强制检查，可提交空字符串
            if (key.equals("discount_reason")){
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.PleDgeAssess( request_end_flag, uploadType,
                        map,files,this);

            }else{
                this.isUploading = true;
                irlModel.PleDgeAssess( request_end_flag, uploadType,
                        map,files.subList(0,files.size() / 2),this);
            }
        }
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

            //不对同行进行强制检查，可以提交空字符串
            if (key.equals("tonghang_phone")){
                continue;
            }

            if (map.get(key) == null || map.get(key).toString().length() <=0){
                irlView.showFailureMsg("请确认信息填写完整");
                return;
            }
        }

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.Coming(map,this );
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.ApplyforRefinance(map,this);
        }

    }

    @Override
    public void HRUploadData(String request_end_flag,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files) {
        if (map == null){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.wk_point_id = wk_point_id;

        this.files.clear();
        this.files.addAll(files);

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

        if (files.size() <=0 && irlView != null && uploadType.equals(UPLOAD_TYPE_NORMAL) && !"35".equals(wk_point_id)){
            irlView.showFailureMsg("请选择文件");
            return;
        }


        if (irlView != null ){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.HRUploadData( request_end_flag, uploadType,map,files,this);
            }else{
                this.isUploading = true;
                irlModel.HRUploadData( request_end_flag, uploadType,map,files.subList(0,files.size() / 2),
                        this);
            }
        }
    }

    @Override
    public void CarPhoto(String request_end_flag,String uploadType,HashMap<String ,Object> map,String type_id,List<LocalMedia> files) {
        if (map == null){
            irlView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.wk_point_id = wk_point_id;

        this.files.clear();
        this.files.addAll(files);

        this.type_id = type_id;

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

        if (irlView != null ){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.CarPhoto( request_end_flag, uploadType,map,type_id,files,this);
            }else{
                this.isUploading = true;
                irlModel.CarPhoto( request_end_flag, uploadType,map,type_id,files.subList(0,files.size() / 2),
                        this);
            }
        }
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.FirstSureAmount(map,this );
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.SureAmount(map,this);
        }
    }
    @Override
    public void HRSureAmount(HashMap<String ,Object> map) {
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.HRSureAmount(map,this);
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
        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.SureAmountReturn(token,w_con_id, w_pot_id,  type_id,this);
        }
    }

    @Override
    public void InputInfo(HashMap<String ,Object> map) {
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.InputInfo( map,this);
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

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.wk_point_id = wk_point_id;

        this.type_id = type_id;

        this.files.clear();
        this.files.addAll(files);

        if ((files == null || files.size() <= 0 ) && !"11".equals(type_id)
                && !irlView.isUploadTypeAdd()){
            irlView.showFailureMsg("请选择文件");

            return;
        }

        if (irlView != null){
            irlView.showProgress();
        }


        if (irlModel != null){
            if (files != null && files.size() <= 1){
                this.isUploading = false;
                irlModel.UploadCarPhoto( request_end_flag, uploadType, map,
                        type_id,files,this);
            }else{
                this.isUploading = true;
                irlModel.UploadCarPhoto( request_end_flag, uploadType, map,
                        type_id,files.subList(0,files.size() / 2),this);
            }
        }

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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.ContractDetail( map,this);
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.AuditRefinance(map,this);
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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            irlModel.AssessRefinance(map,this);
        }
    }

    @Override
    public void InfoCheckRefinance(String request_end_flag ,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files) {

        String wk_point_id = map.get("w_pot_id").toString();

        this.map.clear();
        this.map.putAll(map);

        this.wk_point_id = wk_point_id;

        this.files.clear();
        this.files.addAll(files);

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

        if (irlView != null){
            irlView.showProgress();
        }

        if (irlModel != null){
            if (files.size() <= 1){
                this.isUploading = false;
                irlModel.InfoCheckRefinance( request_end_flag , uploadType, map,files,this);
            }else{
                this.isUploading = true;
                irlModel.InfoCheckRefinance( request_end_flag , uploadType, map,files.subList(0,files.size() / 2),this);
            }
        }

    }

    @Override
    public void Form(String token, int workflow_content_id,int wk_point_id,String proc_type_id) {
        if (irlModel != null){
            irlModel.Form( token,  workflow_content_id, wk_point_id,proc_type_id,this);
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
    @Override
    public void onSucceed(LoanDetailResponse.Result result) {

        if (irlView != null){
            irlView.toLoanDetail(result);
            irlView.hideProgress();
        }
    }
}
