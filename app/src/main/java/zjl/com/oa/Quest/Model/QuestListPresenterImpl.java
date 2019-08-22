package zjl.com.oa.Quest.Model;

import zjl.com.oa.Quest.Presenter.IQuestListListener;
import zjl.com.oa.Quest.Presenter.IQuestListModel;
import zjl.com.oa.Quest.Presenter.IQuestListPresenter;
import zjl.com.oa.Quest.Presenter.IQuestListView;
import zjl.com.oa.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public class QuestListPresenterImpl implements IQuestListPresenter,IQuestListListener{
    private IQuestListView questListView;
    private IQuestListModel questListModel;
    private boolean isShowing = false;

    public QuestListPresenterImpl(IQuestListView questListView) {
        this.questListView = questListView;
        this.questListModel = new QuestListModelImpl();
    }

    @Override
    public void WorkflowListOrder(String token, int status, int pageCount, boolean order) {

        if (questListModel != null){
            questListModel.WorkflowListOrder(token,status,pageCount,order ,this);
        }
        if (questListView != null && !isShowing){
            isShowing = true;
            questListView.showProgress();
        }
    }

    @Override
    public void WorkflowListAdvPage(String token, String status, String proc_type, int page_Count,
                                    String name, int order_type,boolean order, String start_date,
                                    String end_date) {
        if (questListModel != null){
            questListModel.WorkflowListAdvPage(token,status,proc_type,page_Count,name,order_type,
                    order,start_date,end_date,this);
        }
        if (questListView != null && !isShowing){
            isShowing = true;
            questListView.showProgress();
        }
    }

    @Override
    public void PointOpertState(String token, int w_con_id, int w_pot_id) {

        if (questListModel != null){
            questListModel.PointOpertState(token,w_con_id,w_pot_id,this);
        }
        if (questListView != null && !isShowing){
            isShowing = true;
            questListView.showProgress();
        }
    }
    @Override
    public void CloseFlow(String token, int w_con_id) {

        if (questListModel != null){
            questListModel.CloseFlow(token,w_con_id,this);
        }
        if (questListView != null && !isShowing){
            isShowing = true;
            questListView.showProgress();
        }
    }
    @Override
    public void CreateRefinance(String token, int w_con_id) {

        if (questListModel != null){
            questListModel.CreateRefinance(token,w_con_id,this);
        }
        if (questListView != null && !isShowing){
            isShowing = true;
            questListView.showProgress();
        }
    }

    @Override
    public void onSucceed(QuestListResponse.Result result) {
        if (questListView != null){
            questListView.QuestListRefresh(result);
        }
        if (questListView != null){
            isShowing = false;
            questListView.hideProgress();
        }
    }

    @Override
    public void noData() {

        if (questListView != null){
            questListView.noData();
        }
        if (questListView != null){
            isShowing = false;
            questListView.hideProgress();
        }
    }
    @Override
    public void noMoreData() {

        if (questListView != null){
            questListView.noMoreData();
        }
        if (questListView != null){
            isShowing = false;
            questListView.hideProgress();
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed() {
        if (questListView != null){
            questListView.loadNewData();
        }
    }
    @Override
    public void onSucceed(boolean b) {
        if (b){
            if (questListView != null){
                questListView.stepIn();
            }
        }
    }

    @Override
    public void onFail(String msg) {
        if (questListView != null){
            questListView.showFailureMsg(msg);
            questListView.QuestListRefreshError();
        }

        if (questListView != null){
            isShowing = false;
            questListView.hideProgress();
        }
    }

    @Override
    public void relogin() {
        if (questListView != null){
            questListView.relogin();
        }

        if (questListView != null){
            isShowing = false;
            questListView.hideProgress();
        }
    }
}
