package zjl.com.oa2.Visit.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorListener  extends IBaseListener{
    void onSucceed(FormResponse.Result result);
}
