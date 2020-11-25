package zjl.com.oa2.RenewLoanCompletation.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IView extends IBaseView{
    void showProgress();
    void hideProgress();
}
