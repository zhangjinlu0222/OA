package zjl.com.oa.RenewLoanCompletation.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IView extends IBaseView{
    void showProgress();
    void hideProgress();
}
