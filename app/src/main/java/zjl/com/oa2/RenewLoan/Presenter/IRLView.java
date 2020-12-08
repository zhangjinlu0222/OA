package zjl.com.oa2.RenewLoan.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.ManagersResponse;
import zjl.com.oa2.Response.SearchResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLView extends IBaseView{
    void showProgress();
    void hideProgress();
    void loadForms(FormResponse.Result result);
    void toLoanDetail(LoanDetailResponse.Result result);
    void saveAdvSecInfo(SearchResponse.Result result);
    void saveManagers(ManagersResponse.Result result);
    String getData_Con(String arg);//获取参数对应的控件值
}
