package zjl.com.oa.Setting.PayBack.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.SearchNameResponse;
import zjl.com.oa.Response.SearchResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface IPayBackListener extends IBaseListener{
    void onSucceed(SearchNameResponse result);
    void onSucceed(String arg);
}
