package zjl.com.oa2.WorkFlow.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.GetWorkFlowResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.PhotoVideoDetailResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlowView extends IBaseView{
    void refreshData(GetWorkFlowResponse.Result result);
    void showProcess();
    void hideProcess();
    void reloadData();
    void loadPhotosAndVideos(PhotoVideoDetailResponse.Result data);
    void updateImgRefuse();
    void downloadContract(String url);
    void toLoanDetail(LoanDetailResponse.Result result);
}
