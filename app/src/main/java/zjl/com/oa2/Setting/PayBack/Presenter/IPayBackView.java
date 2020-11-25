package zjl.com.oa2.Setting.PayBack.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.SearchNameResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface IPayBackView extends IBaseView{
    void showProgressBar();
    void hideProgressBar();
    void updateDataWithResponse(SearchNameResponse response);
}
