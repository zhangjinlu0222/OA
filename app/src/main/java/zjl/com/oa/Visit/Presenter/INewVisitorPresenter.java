package zjl.com.oa.Visit.Presenter;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorPresenter {
    void LookInterview(String token, String w_con_id);
    void newVisitor(String token,String name,int from,int product_type_name,String remark,String comment,String w_con_id,String w_pot_id);
    void onDestoryView();
}
