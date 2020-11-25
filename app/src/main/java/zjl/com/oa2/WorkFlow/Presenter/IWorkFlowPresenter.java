package zjl.com.oa2.WorkFlow.Presenter;

import java.util.HashMap;

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
    void LookRefinanceContract(String token, String  workflow_content_id,String contract_type);

    //查看贷款详细
    void LookLoanDetail(HashMap<String ,Object> map);
}
