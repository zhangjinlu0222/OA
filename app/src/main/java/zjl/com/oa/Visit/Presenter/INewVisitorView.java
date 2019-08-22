package zjl.com.oa.Visit.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorView extends IBaseView{
    String getCustomerName();
    int getCustormerSource();
    int getProductType();
    String getPointNotice();
    String getBakInfo();
    void showProgressBar();
    void hideProgressBar();
    void refreshData(LookInterviewResponse.Result result);
}
