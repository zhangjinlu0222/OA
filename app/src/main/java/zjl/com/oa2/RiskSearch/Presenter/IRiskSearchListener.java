package zjl.com.oa2.RiskSearch.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.BigDataResponse;
import zjl.com.oa2.Response.GPSResponse;
import zjl.com.oa2.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IRiskSearchListener extends IBaseListener {
    void onSucceed(BigDataResponse.Result result);
}
