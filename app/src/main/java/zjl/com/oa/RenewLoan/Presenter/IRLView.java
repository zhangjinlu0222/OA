package zjl.com.oa.RenewLoan.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLView extends IBaseView{
    void showProgress();
    void hideProgress();
    void loadForms(FormResponse.Result result);
    String getData_Con(String arg);//获取参数对应的控件值
}
