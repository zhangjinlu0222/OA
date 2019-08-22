package zjl.com.oa.InformationCheck.Model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import zjl.com.oa.InformationCheck.Presenter.IInfoCheckListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckModel;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckPresenter;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckView;
import zjl.com.oa.Response.FormResponse;

import static zjl.com.oa.Base.BaseActivity.UPLOAD_TYPE_ADD;
import static zjl.com.oa.Base.BaseActivity.request_end_flag;

/**
 * Created by Administrator on 2018/2/11.
 */

public class InfoCheckPresenterImpl  implements IInfoCheckPresenter,IInfoCheckListener {
    private IInfoCheckView infoCheckView;
    private IInfoCheckModel infoCheckModel;
    private boolean isUploading;
    private String token,persion_court,credit,
            car_break_rules,  insurance,  legal_person,remark,uploadType;
    private int workflow_content_id, wk_point_id;
    private List<LocalMedia> files = new ArrayList<>();

    public InfoCheckPresenterImpl(IInfoCheckView iInfoCheckView){
        this.infoCheckView = iInfoCheckView;
        this.infoCheckModel = new InfoCheckModelImpl();
    }

    @Override
    public void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark) {
        if (infoCheckModel != null){
            infoCheckModel.endWorkFlow( token,  workflow_content_id, wk_point_id,remark,this);
        }
        if (infoCheckView != null){
            infoCheckView.showProgress();
        }
    }

    @Override
    public void uploadMsg(String request_end_flag,String uploadType,String token, int workflow_content_id,
                          String persion_court, String credit,
                          String car_break_rules, String insurance, String legal_person,
                          int wk_point_id, List<LocalMedia> files,String remark) {
        if ("".equals(persion_court)){
            if (infoCheckView != null){
                infoCheckView.showFailureMsg("请输入人法查询结果");
            }
            return;
        }
        if ("".equals(credit)){
            if (infoCheckView != null){
                infoCheckView.showFailureMsg("请输入征信查询结果");
            }
            return;
        }
        if ("".equals(car_break_rules)){
            if (infoCheckView != null){
                infoCheckView.showFailureMsg("请输入违章查询结果");
            }
            return;
        }
        if ("".equals(insurance)){
            if (infoCheckView != null){
                infoCheckView.showFailureMsg("请输入保险查询结果");
            }
            return;
        }
        if ("".equals(legal_person)){
            if (infoCheckView != null){
                infoCheckView.showFailureMsg("请输入法人查询结果");
            }
            return;
        }
        if (files.size() <= 0 && !infoCheckView.isUploadTypeAdd()){
            if (infoCheckView != null){
                infoCheckView.showFailureMsg("请选择上传文件");
            }
            return;
        }
        if (infoCheckModel != null){
            if (files.size() <= 1){
                infoCheckModel.uploadMsg( request_end_flag,uploadType,token,
                        workflow_content_id, persion_court,  credit,
                        car_break_rules,  insurance,  legal_person,
                        wk_point_id, files,remark,this);
                isUploading = false;
            }else{
                infoCheckModel.uploadMsg( request_end_flag,uploadType,token,
                        workflow_content_id, persion_court,  credit,
                        car_break_rules,  insurance,  legal_person,
                        wk_point_id, files.subList(0,files.size() / 2),remark,this);
                isUploading = true;
            }
        }
        if (infoCheckView != null){
            infoCheckView.showProgress();
        }

        this.token = token;
        this.persion_court = persion_court;
        this.credit = credit;
        this.car_break_rules = car_break_rules;
        this.insurance = insurance;
        this.legal_person = legal_person;
        this.remark = remark;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;
        this.files.addAll(files);
        this.uploadType = uploadType;
    }

    @Override
    public void Form(String token, int workflow_content_id, int wk_point_id) {
        if (infoCheckView != null){
            infoCheckModel.Form( token,  workflow_content_id, wk_point_id,this);
        }
        if (infoCheckView != null){
            infoCheckView.showProgress();
        }
    }

    @Override
    public void onSucceed() {

        if (infoCheckView != null && !isUploading){
            infoCheckView.hideProgress();
            infoCheckView.toMainActivity();
        }else{
            infoCheckModel.uploadMsg( request_end_flag,UPLOAD_TYPE_ADD,token,  workflow_content_id, persion_court,  credit,
                    car_break_rules,  insurance,  legal_person, wk_point_id, files.subList(files.size() / 2,files.size()),remark,this);

            isUploading = false;
        }
    }

    @Override
    public void onFail(String msg) {
        if (infoCheckView != null){
            infoCheckView.hideProgress();
            infoCheckView.showFailureMsg(msg);
        }
        isUploading = false;
    }

    @Override
    public void relogin() {
        if (infoCheckView != null){
            infoCheckView.relogin();
        }
        if (infoCheckView != null){
            infoCheckView.hideProgress();
        }
        this.isUploading = false;
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }
    @Override
    public void onDestoryView(){
        if (infoCheckView != null){
            infoCheckView = null;
        }
    }

    @Override
    public void onSucceed(FormResponse.Result result) {

        if (infoCheckView != null){
            infoCheckView.loadForms(result);
            infoCheckView.hideProgress();
        }
    }
}
