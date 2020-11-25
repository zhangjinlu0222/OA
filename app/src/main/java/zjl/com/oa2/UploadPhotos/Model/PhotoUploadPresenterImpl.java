package zjl.com.oa2.UploadPhotos.Model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import zjl.com.oa2.UploadPhotos.Presenter.IPhotoUploadListener;
import zjl.com.oa2.UploadPhotos.Presenter.IPhotoUploadModel;
import zjl.com.oa2.UploadPhotos.Presenter.IPhotoUploadPresenter;
import zjl.com.oa2.UploadPhotos.Presenter.IPhotoUploadView;

import static zjl.com.oa2.Base.BaseActivity.UPLOAD_TYPE_ADD;
import static zjl.com.oa2.Base.BaseActivity.request_end_flag;

/**
 * Created by Administrator on 2018/3/5.
 */

public class PhotoUploadPresenterImpl implements IPhotoUploadPresenter,IPhotoUploadListener{
    private IPhotoUploadView photoUploadView;
    private IPhotoUploadModel photoUploadModel;

    private int index = 0;

    private List<LocalMedia> files = new ArrayList<>();
    private String token, remark,type_id;
    private int workflow_content_id, wk_point_id;
    private boolean isUploading = false;

    public PhotoUploadPresenterImpl(IPhotoUploadView photoUploadView) {
        this.photoUploadModel = new PhotoUploadModelImpl();
        this.photoUploadView = photoUploadView;
    }

    @Override
    public void onSucceed() {

        if (photoUploadView != null && !isUploading){
            photoUploadView.toMainActivity();
            photoUploadView.hideProgress();
        }else{
            photoUploadModel.uploadPhotos( request_end_flag, UPLOAD_TYPE_ADD,token,remark,
                    workflow_content_id, wk_point_id ,files.subList(files.size() / 2,files.size()),type_id,this);
            this.isUploading = false;
        }

    }

    @Override
    public void onFail() {

        if (photoUploadView != null){
            photoUploadView.UploadFail();
            photoUploadView.hideProgress();
        }

        this.isUploading = false;
    }

    @Override
    public void onFail(String arg) {

        if (photoUploadView != null){
            photoUploadView.showFailureMsg(arg);
            photoUploadView.hideProgress();
        }

        this.isUploading = false;
    }

    @Override
    public void relogin() {
        if (photoUploadView != null){
            photoUploadView.hideProgress();
            photoUploadView.relogin();
        }
        this.isUploading = false;
    }

    @Override
    public void uploadPhotos(String request_end_flag,String uploadType,String token, String remark,
                             int workflow_content_id, int wk_point_id, List<LocalMedia> files,
                             String type_id ) {

        //9=风控审核，不需要上传图片
        if (files.size() <=0 && photoUploadView != null && !"9".equals(type_id)
                && !photoUploadView.isUploadTypeAdd()){
            photoUploadView.showFailureMsg("请选择文件");
            return;
        }

        if (photoUploadModel != null){
            if (files.size() <= 1){
                photoUploadModel.uploadPhotos( request_end_flag, uploadType,token,remark,
                        workflow_content_id, wk_point_id ,files,type_id,this);
                this.isUploading = false;
            }else{
                photoUploadModel.uploadPhotos( request_end_flag, uploadType,token,remark,
                        workflow_content_id, wk_point_id ,files.subList(0,files.size() / 2),type_id,this);
                this.isUploading = true;
            }
            photoUploadView.showProgress();
        }

        this.token = token;
        this.remark = remark;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;
        this.files.addAll(files);
        this.type_id = type_id;
    }

    @Override
    public void onDestory() {
        if (photoUploadView != null){
            photoUploadView = null;
        }
    }
}
