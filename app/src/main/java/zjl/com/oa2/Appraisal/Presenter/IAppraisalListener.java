package zjl.com.oa2.Appraisal.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.AppraisalResponse;

public interface IAppraisalListener extends IBaseListener{
    void onSucceed(AppraisalResponse.Result result);
}
