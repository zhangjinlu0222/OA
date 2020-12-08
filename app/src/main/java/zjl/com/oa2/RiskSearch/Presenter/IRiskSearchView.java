package zjl.com.oa2.RiskSearch.Presenter;

import android.content.Context;

import java.util.Set;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.BigDataResponse;
import zjl.com.oa2.Response.GPSResponse;
import zjl.com.oa2.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IRiskSearchView extends IBaseView{
    void loadBigDatas(BigDataResponse.Result result);
    void showProgress();
    void hideProgress();
}
