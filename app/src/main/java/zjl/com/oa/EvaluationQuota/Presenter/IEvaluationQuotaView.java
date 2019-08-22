package zjl.com.oa.EvaluationQuota.Presenter;

import android.widget.ImageView;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.EvaluationQuotaResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IEvaluationQuotaView extends IBaseView{
    String getQuota();
    List<LocalMedia> getCarPhotos();
    List<LocalMedia> getReportPhotos();
    String getBakInfo();

    void refreshData(EvaluationQuotaResponse.Result result);
    void showProgressBar();
    void hideProgressBar();

    void loadUploadFlag(ImageView v, boolean flag);
    int getReUploadType(boolean epFlag,boolean cpFlag);
}
