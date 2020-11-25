package zjl.com.oa2.EvaluationQuota.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.EvaluationQuotaResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IEvaluationQuotaListener extends IBaseListener{
    void onSucceed(EvaluationQuotaResponse.Result result);
}
