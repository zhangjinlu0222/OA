package zjl.com.oa2.FeedBack.Presenter;

import zjl.com.oa2.Base.IBaseView;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IFeedBackView extends IBaseView{
    String getBakInfo();
    void refreshData(String arg);
    void showProgressBar();
    void hideProgressBar();
}
