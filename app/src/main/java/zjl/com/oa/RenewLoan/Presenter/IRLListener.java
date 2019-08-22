package zjl.com.oa.RenewLoan.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLListener extends IBaseListener{
    void onSucceed(FormResponse.Result result);
}
