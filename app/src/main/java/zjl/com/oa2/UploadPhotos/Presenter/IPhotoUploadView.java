package zjl.com.oa2.UploadPhotos.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import zjl.com.oa2.Base.IBaseView;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IPhotoUploadView extends IBaseView{
    String getToken();
    String getBakInfo();
    List<LocalMedia> getUploadPhotos();
    void UploadFail();

    void showProgress();
    void hideProgress();
}
