package zjl.com.oa.Evaluation.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IEvaluationListener extends IBaseListener{
    void onSucceed(FormResponse.Result result);
}
