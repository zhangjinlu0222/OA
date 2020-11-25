package zjl.com.oa2.WorkFlow.Model;

import java.util.HashMap;
import java.util.List;

import zjl.com.oa2.Response.GetWorkFlowResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.LookContractResponse;
import zjl.com.oa2.Response.PhotoVideoDetailResponse;
import zjl.com.oa2.WorkFlow.Presenter.IWorkFlowListener;
import zjl.com.oa2.WorkFlow.Presenter.IWorkFlowModel;
import zjl.com.oa2.WorkFlow.Presenter.IWorkFlowPresenter;
import zjl.com.oa2.WorkFlow.Presenter.IWorkFlowView;

/**
 * Created by Administrator on 2018/3/5.
 */

public class WorkFlowPresenterImpl implements IWorkFlowListener,IWorkFlowPresenter{
    private IWorkFlowView workFlowView;
    private IWorkFlowModel workFlowModel;

    public WorkFlowPresenterImpl(IWorkFlowView workFlowView) {
        this.workFlowView = workFlowView;
        this.workFlowModel = new WorkFlowModelImpl();
    }

    @Override
    public void LookLoanDetail(HashMap<String ,Object> map) {

        if (map == null){
            workFlowView.showFailureMsg("请确认信息填写完整");
            return;
        }

        String workflow_content_id = map.get("w_con_id").toString();

        if (workflow_content_id == null || workflow_content_id.length() <=0){
            workFlowView.showFailureMsg("表单异常请重试");
            return;
        }

        if (workFlowModel != null){
            workFlowModel.LookLoanDetail(map,this);
            workFlowView.showProcess();
        }
    }

    @Override
    public void getWorkFlow(String token, String w_con_id,String proc_type_id) {
        if (workFlowModel != null){
            workFlowModel.getWorkFlow(token,w_con_id, proc_type_id,this);
        }
        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void getWorkDetail(String token, String w_con_id,String proc_type_id) {

        if (workFlowModel != null){
            workFlowModel.getWorkDetail(token,w_con_id, proc_type_id,this);
        }

        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void HRRejection(String token, String w_con_id, String w_pot_id,String proc_type_id, String remark) {

        if (workFlowModel != null){
            workFlowModel.HRRejection(token,w_con_id,w_pot_id,proc_type_id,remark,this);
        }
        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void getPhotoVideoDetail(String token, String w_con_id, String w_pot_id,String proc_type_id) {

        if (workFlowModel != null){
            workFlowModel.getPhotoVideoDetail(token,w_con_id,w_pot_id,proc_type_id,this);
        }
    }
    @Override
    public void LookRefinanceContract(String token, String w_con_id, String contract_type) {

        if (workFlowModel != null){
            workFlowModel.LookRefinanceContract(token,w_con_id,contract_type,this);
        }

        if (workFlowView != null ){
            workFlowView.showProcess();
        }
    }
    @Override
    public void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,String proc_type_id) {
        if (workFlowModel != null){
            workFlowModel.endWorkFlow( token,  workflow_content_id, wk_point_id,remark,proc_type_id,this);
        }
        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void recoverWorkflow(String token, int workflow_content_id,String proc_type_id) {
        if (workFlowModel != null){
            workFlowModel.recoverWorkflow( token,  workflow_content_id,proc_type_id,this);
        }
        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void onSucceed() {
        if (workFlowView != null){
            workFlowView.hideProcess();
            workFlowView.reloadData();
        }
    }
    @Override
    public void onSucceed(boolean result) {
        if (result && workFlowView != null){
            workFlowView.hideProcess();
            workFlowView.toMainActivity();
        }
    }

    @Override
    public void onRecoverSucceed() {
        if (workFlowView != null){
            workFlowView.hideProcess();
            workFlowView.reloadData();
            workFlowView.toMainActivity();
        }
    }

    @Override
    public void onFail() {
        if (workFlowView != null){
            workFlowView.hideProcess();
        }
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed(LookContractResponse.Result result) {
        if (workFlowView != null){
            workFlowView.downloadContract(result.getAndroid_url());
        }
        if (workFlowView != null){
            workFlowView.hideProcess();
        }
    }
    @Override
    public void onSucceed(GetWorkFlowResponse.Result result) {
        if (workFlowView != null){
            workFlowView.refreshData(result);
        }
        if (workFlowView != null){
            workFlowView.hideProcess();
        }
    }

    @Override
    public void onSucceed(PhotoVideoDetailResponse.Result result) {
        if (workFlowView != null){
            workFlowView.loadPhotosAndVideos(result);
        }
    }

    @Override
    public void onFail(String msg) {

        if (workFlowView != null){
            workFlowView.showFailureMsg(msg);
        }
        if (workFlowView != null){
            workFlowView.hideProcess();
        }
    }

    @Override
    public void relogin() {
        if (workFlowView != null){
            workFlowView.hideProcess();
            workFlowView.relogin();
        }
    }


    @Override
    public void onSucceed(LoanDetailResponse.Result result) {

        if (workFlowView != null){
            workFlowView.toLoanDetail(result);
            workFlowView.hideProcess();
        }
    }
}
