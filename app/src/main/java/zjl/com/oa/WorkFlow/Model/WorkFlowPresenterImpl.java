package zjl.com.oa.WorkFlow.Model;

import java.util.List;

import zjl.com.oa.Response.GetWorkFlowResponse;
import zjl.com.oa.Response.PhotoVideoDetailResponse;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowListener;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowModel;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowPresenter;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowView;

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
    public void getWorkFlow(String token, String w_con_id) {
        if (workFlowModel != null){
            workFlowModel.getWorkFlow(token,w_con_id,this);
        }
    }

    @Override
    public void getWorkDetail(String token, String w_con_id) {

        if (workFlowModel != null){
            workFlowModel.getWorkDetail(token,w_con_id,this);
        }

        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void postPointEdit(String token, String w_con_id, String w_pot_id, String remark) {

        if (workFlowModel != null){
            workFlowModel.postPointEdit(token,w_con_id,w_pot_id,remark,this);
        }
        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void getPhotoVideoDetail(String token, String w_con_id, String type_id) {

        if (workFlowModel != null){
            workFlowModel.getPhotoVideoDetail(token,w_con_id,type_id,this);
        }
    }
    @Override
    public void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark) {
        if (workFlowModel != null){
            workFlowModel.endWorkFlow( token,  workflow_content_id, wk_point_id,remark,this);
        }
        if (workFlowView != null){
            workFlowView.showProcess();
        }
    }

    @Override
    public void recoverWorkflow(String token, int workflow_content_id) {
        if (workFlowModel != null){
            workFlowModel.recoverWorkflow( token,  workflow_content_id,this);
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
            workFlowView.updateImgRefuse();
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
}
