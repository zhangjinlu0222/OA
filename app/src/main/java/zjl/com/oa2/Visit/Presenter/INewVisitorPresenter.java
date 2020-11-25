package zjl.com.oa2.Visit.Presenter;

import zjl.com.oa2.Base.IBasePresenter;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorPresenter extends IBasePresenter{
    void newVisitor(String token,String name,int from,int product_type_name,String remark,String comment,String w_con_id,String w_pot_id);
    void onDestoryView();
}
