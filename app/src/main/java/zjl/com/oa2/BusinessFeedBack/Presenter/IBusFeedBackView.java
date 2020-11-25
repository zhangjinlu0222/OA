package zjl.com.oa2.BusinessFeedBack.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.BusFirstFeedBackResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IBusFeedBackView extends IBaseView{
    String getBakInfo();
    int getLoanLength();
    float getLoanRate();
    String getLoanReturnMethod();

//    void refreshData(BusFeedBackResponse.Result result);
    void refreshData(BusFirstFeedBackResponse.Result result);
    void showProgressBar();
    void hideProgressBar();
}
