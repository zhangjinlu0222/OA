package zjl.com.oa2.Evaluation.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IEvaluationView extends IBaseView{
    String getToken();
    String getCarAge();
    String getCarType();
    String getCarModel();
    String getCarMilage();
    String getBakInfo();
    String getMarketValue();
    String getTakeValue();


    List<LocalMedia> getUploadPhotos();

    void showProgress();
    void hideProgress();

    void loadForms(FormResponse.Result result);
}
