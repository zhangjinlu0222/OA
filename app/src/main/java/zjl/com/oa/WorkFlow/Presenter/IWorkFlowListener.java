package zjl.com.oa.WorkFlow.Presenter;

import java.util.List;

import zjl.com.oa.Base.IBaseListener;
import zjl.com.oa.Response.GetWorkFlowResponse;
import zjl.com.oa.Response.PhotoVideoDetailResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IWorkFlowListener extends IBaseListener{
    void onSucceed(GetWorkFlowResponse.Result result);
    void onSucceed(PhotoVideoDetailResponse.Result result);
    void onSucceed(boolean result);
    void onRecoverSucceed();
}
