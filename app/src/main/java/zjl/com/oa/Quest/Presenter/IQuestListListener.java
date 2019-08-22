package zjl.com.oa.Quest.Presenter;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IQuestListListener extends IBaseListener {
    void onSucceed(QuestListResponse.Result result);
    void noData();
    void noMoreData();
    void onSucceed(boolean b);
}
