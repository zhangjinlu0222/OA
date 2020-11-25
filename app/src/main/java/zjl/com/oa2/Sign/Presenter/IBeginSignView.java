package zjl.com.oa2.Sign.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.GetBeginSignResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IBeginSignView extends IBaseView {
    void refreshData(GetBeginSignResponse.Result data);
    void showProgressBar();
    void hideProgressBar();
}
