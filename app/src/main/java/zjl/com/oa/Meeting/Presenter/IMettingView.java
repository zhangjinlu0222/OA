package zjl.com.oa.Meeting.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IMettingView extends IBaseView{
    String getBakInfo();
    void refreshData(LookInterviewResponse.Result result);
    void showProgressBar();
    void hideProgressBar();
}
