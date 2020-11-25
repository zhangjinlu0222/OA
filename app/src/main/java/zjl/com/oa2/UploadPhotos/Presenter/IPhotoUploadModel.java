package zjl.com.oa2.UploadPhotos.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IPhotoUploadModel {
    void uploadPhotos(String request_end_flag,String uploadType,String token, String remark,
                      int workflow_content_id, int wk_point_id,
                      List<LocalMedia> files,String type_id, IPhotoUploadListener listener);
}
