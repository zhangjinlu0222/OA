package zjl.com.oa.Quest.Presenter;

import android.content.Context;

import java.util.Set;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IQuestListView  extends IBaseView{
    void QuestListRefresh(QuestListResponse.Result result);
    void QuestListRefreshError();
    void toVisitorAcvitity(Context context);
    void noData();
    void noMoreData();
    void previlege(Set<String> role);
    void showProgress();
    void hideProgress();
    void loadNewData();
    void stepIn();
}
