package zjl.com.oa.WorkFlow.Presenter;

import java.util.List;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.GetWorkFlowResponse;
import zjl.com.oa.Response.PhotoVideoDetailResponse;

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
}
