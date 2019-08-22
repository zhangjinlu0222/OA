package zjl.com.oa.BusinessFeedBack.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.BusFirstFeedBackResponse;

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
