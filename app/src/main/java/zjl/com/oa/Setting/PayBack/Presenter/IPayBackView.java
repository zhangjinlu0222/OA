package zjl.com.oa.Setting.PayBack.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.SearchNameResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface IPayBackView extends IBaseView{
    void showProgressBar();
    void hideProgressBar();
    void updateDataWithResponse(SearchNameResponse response);
}
