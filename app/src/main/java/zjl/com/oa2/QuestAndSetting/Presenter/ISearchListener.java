package zjl.com.oa2.QuestAndSetting.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.SearchResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface ISearchListener extends IBaseListener {
    void onSucceed(SearchResponse.Result result);
}
