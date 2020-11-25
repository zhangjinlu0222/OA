package zjl.com.oa2.Visit.Presenter;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorView extends IBaseView{
    void showProgress();
    void hideProgress();
    void loadForms(FormResponse.Result result);
}
