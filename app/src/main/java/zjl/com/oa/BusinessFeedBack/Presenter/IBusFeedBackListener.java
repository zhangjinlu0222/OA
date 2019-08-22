package zjl.com.oa.BusinessFeedBack.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.BusFirstFeedBackResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IBusFeedBackListener extends IBaseListener{
//    void onSucceed(BusFeedBackResponse.Result result);
    void onSucceed(BusFirstFeedBackResponse.Result result);
}
