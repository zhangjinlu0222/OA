package zjl.com.oa2.Visit.Presenter;


/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorModel {
    void Form(String token, String workflow_content_id, String wk_point_id, INewVisitorListener listener);
    void newVisitor(String token,String name,int from,int product_type_name,String remark,String comment,String w_con_id,String w_pot_id,INewVisitorListener listener);
}
