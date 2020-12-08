package zjl.com.oa2.LoanInfoList.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.AppraisalResponse;
import zjl.com.oa2.Response.LoanInfosResponse;

public interface ILoanInfoListListener extends IBaseListener{
    void onSucceed(LoanInfosResponse.Result result);
}
