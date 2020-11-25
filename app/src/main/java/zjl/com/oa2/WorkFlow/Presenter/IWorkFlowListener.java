package zjl.com.oa2.WorkFlow.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.GetWorkFlowResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.LookContractResponse;
import zjl.com.oa2.Response.PhotoVideoDetailResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlowListener extends IBaseListener{
    void onSucceed(LookContractResponse.Result result);
    void onSucceed(GetWorkFlowResponse.Result result);
    void onSucceed(PhotoVideoDetailResponse.Result result);
    void onSucceed(boolean result);
    void onRecoverSucceed();
    void onSucceed(LoanDetailResponse.Result result);
}
