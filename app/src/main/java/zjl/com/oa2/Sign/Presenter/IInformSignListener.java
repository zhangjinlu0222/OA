package zjl.com.oa2.Sign.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.GetSigningResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInformSignListener extends IBaseListener{
    void onSucceed(List<GetSigningResponse.Result.Data> data);
}
