package zjl.com.oa2.LoanRequest.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.GetLoanRequestResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public interface ILoanRequestView extends IBaseView{
    void refreshData(List<GetLoanRequestResponse.Result.Section> data);
    String getBakInfo();
    void showProgressBar();
    void hideProgressBar();
}
