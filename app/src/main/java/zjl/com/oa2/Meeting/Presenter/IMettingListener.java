package zjl.com.oa2.Meeting.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IMettingListener  extends IBaseListener{
    void onEndWKSucceed();
    void onEndWKFail(String arg);
    void onSucceed(LookInterviewResponse.Result result);
}
