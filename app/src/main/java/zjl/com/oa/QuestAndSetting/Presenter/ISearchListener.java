package zjl.com.oa.QuestAndSetting.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.SearchResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface ISearchListener extends IBaseListener {
    void onSucceed(SearchResponse.Result result);
}
