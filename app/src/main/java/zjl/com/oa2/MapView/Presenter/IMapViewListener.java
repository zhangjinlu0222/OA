package zjl.com.oa2.MapView.Presenter;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.GPSResponse;
import zjl.com.oa2.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IMapViewListener extends IBaseListener {
    void onSucceed(GPSResponse.Result result);
}
