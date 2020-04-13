package zjl.com.oa.Quest.Presenter;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IQuestListModel {
//    void QueryQuestList(String token,IQuestListListener listener);
//    void QuestFilter(String token,int status,int pageCount,int order,String date,IQuestListListener listener);
    void WorkflowListOrder(String token,int status,int pageCount,boolean order,IQuestListListener listener);
    void WorkflowListAdvPage(String token,String status,String proc_type,int page_Count,String name,
                             int order_type,boolean order,String start_date,String end_date,int fliter,
                             IQuestListListener listener);
    void PointOpertState(String token,int w_con_id,int w_pot_id,String proc_type_id,IQuestListListener listener);
    void CloseFlow(String token,int w_con_id,IQuestListListener listener);
    void CreateRefinance(String token,int w_con_id,IQuestListListener listener);
}
