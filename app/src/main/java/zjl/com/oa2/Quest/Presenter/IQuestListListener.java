package zjl.com.oa2.Quest.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IQuestListListener extends IBaseListener {
    void onSucceed(QuestListResponse.Result result);
    void noData();
    void noMoreData();
    void onSucceed(boolean b);
}
