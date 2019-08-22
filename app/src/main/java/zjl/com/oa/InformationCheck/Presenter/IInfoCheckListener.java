package zjl.com.oa.InformationCheck.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInfoCheckListener extends IBaseListener{
    void onSucceed(FormResponse.Result result);
}
