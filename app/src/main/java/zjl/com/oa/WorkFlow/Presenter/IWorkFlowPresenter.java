package zjl.com.oa.WorkFlow.Presenter;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlowPresenter{
    void getWorkFlow(String token,String w_con_id,String proc_type_id);
    void getWorkDetail(String token,String w_con_id,String proc_type_id);
    void HRRejection(String token,String w_con_id,String w_pot_id,String proc_type_id,String remark);
    void getPhotoVideoDetail(String token,String w_con_id,String w_pot_id,String proc_type_id);
    void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,String proc_type_id);
    void recoverWorkflow(String token, int workflow_content_id,String proc_type_id);
}
