package zjl.com.oa2.WorkFlow.Presenter;

import java.util.HashMap;

import zjl.com.oa2.RenewLoan.Presenter.IRLListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlowModel {
    void getWorkFlow(String token,String w_con_id,String proc_type_id,IWorkFlowListener listener);
    void getWorkDetail(String token,String w_con_id,String proc_type_id,IWorkFlowListener listener);
    void HRRejection(String token,String w_con_id,String w_pot_id,String proc_type_id,String remark, IWorkFlowListener listener);
    void getPhotoVideoDetail(String token,String w_con_id,String w_pot_id,String proc_type_id,IWorkFlowListener listener);
    void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,String proc_type_id,IWorkFlowListener listener);
    void recoverWorkflow(String token, int workflow_content_id,String proc_type_id,IWorkFlowListener listener);
    void LookRefinanceContract(String token, String  workflow_content_id,String contract_type,IWorkFlowListener listener);

    //查看贷款详细
    void LookLoanDetail(HashMap<String ,Object> map, IWorkFlowListener listener);
}
