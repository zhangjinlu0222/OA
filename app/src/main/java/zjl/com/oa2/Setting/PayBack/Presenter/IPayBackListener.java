package zjl.com.oa2.Setting.PayBack.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.SearchNameResponse;
import zjl.com.oa2.Response.SearchResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface IPayBackListener extends IBaseListener{
    void onSucceed(SearchNameResponse result);
    void onSucceed(String arg);
}
