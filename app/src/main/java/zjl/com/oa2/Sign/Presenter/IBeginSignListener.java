package zjl.com.oa2.Sign.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.GetBeginSignResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IBeginSignListener extends IBaseListener{
    void onSucceed(GetBeginSignResponse.Result data);
}
