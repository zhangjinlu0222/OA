package zjl.com.oa.Appraisal.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.AppraisalResponse;

public interface IAppraisalListener extends IBaseListener{
    void onSucceed(AppraisalResponse.Result result);
}
