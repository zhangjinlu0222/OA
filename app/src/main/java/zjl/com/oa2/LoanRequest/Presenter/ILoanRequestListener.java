package zjl.com.oa2.LoanRequest.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.GetLoanRequestResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public interface ILoanRequestListener extends IBaseListener{
    void onSucceed(List<GetLoanRequestResponse.Result.Section> data);
}
