package zjl.com.oa.Quest.Presenter;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IQuestListPresenter {
//    void QueryQuestList(String token);
//    void QuestFilter(String token,int status,int pageCount,int order,String date);
    void WorkflowListOrder(String token,int status,int pageCount,boolean order);
    void WorkflowListAdvPage(String token,String status,String proc_type,int page_Count,String name,
                             int order_type,boolean order,String start_date,String end_date);
    void PointOpertState(String token,int w_con_id,int w_pot_id);
    void CloseFlow(String token,int w_con_id);
    void CreateRefinance(String token,int w_con_id);
}
