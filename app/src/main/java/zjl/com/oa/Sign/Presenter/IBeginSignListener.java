package zjl.com.oa.Sign.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.GetBeginSignResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IBeginSignListener extends IBaseListener{
    void onSucceed(GetBeginSignResponse.Result data);
}
