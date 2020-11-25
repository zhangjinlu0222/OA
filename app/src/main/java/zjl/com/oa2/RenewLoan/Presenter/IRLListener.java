package zjl.com.oa2.RenewLoan.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.SearchResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLListener extends IBaseListener{
    void onSucceed(FormResponse.Result result);
    void onSucceed(LoanDetailResponse.Result result);
    void onSucceed(SearchResponse.Result result);
}
