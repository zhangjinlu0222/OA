package zjl.com.oa.Visit.Presenter;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface INewVisitorView extends IBaseView{
    void showProgress();
    void hideProgress();
    void loadForms(FormResponse.Result result);
}
