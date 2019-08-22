package zjl.com.oa.EvaluationQuota.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.EvaluationQuotaResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IEvaluationQuotaListener extends IBaseListener{
    void onSucceed(EvaluationQuotaResponse.Result result);
}
