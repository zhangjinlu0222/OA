package zjl.com.oa2.Evaluation.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IEvaluationListener extends IBaseListener{
    void onSucceed(FormResponse.Result result);
}
