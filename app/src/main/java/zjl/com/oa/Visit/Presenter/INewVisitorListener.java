package zjl.com.oa.Visit.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorListener  extends IBaseListener{
    void onSucceed(LookInterviewResponse.Result result);
}
