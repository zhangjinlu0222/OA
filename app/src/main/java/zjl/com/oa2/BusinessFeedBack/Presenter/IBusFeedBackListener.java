package zjl.com.oa2.BusinessFeedBack.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.BusFirstFeedBackResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IBusFeedBackListener extends IBaseListener{
//    void onSucceed(BusFeedBackResponse.Result result);
    void onSucceed(BusFirstFeedBackResponse.Result result);
}
