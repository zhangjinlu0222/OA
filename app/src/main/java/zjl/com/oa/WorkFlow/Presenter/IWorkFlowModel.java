package zjl.com.oa.WorkFlow.Presenter;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlowModel {
    void getWorkFlow(String token,String w_con_id,IWorkFlowListener listener);
    void getWorkDetail(String token,String w_con_id,IWorkFlowListener listener);
    void postPointEdit(String token,String w_con_id,String w_pot_id,String remark, IWorkFlowListener listener);
    void getPhotoVideoDetail(String token,String w_con_id,String type_id,IWorkFlowListener listener);
    void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,IWorkFlowListener listener);
    void recoverWorkflow(String token, int workflow_content_id,IWorkFlowListener listener);
}
