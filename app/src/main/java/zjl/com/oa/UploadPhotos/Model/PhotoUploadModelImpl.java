package zjl.com.oa.UploadPhotos.Model;

import android.util.Log;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ModelImpl;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUpload;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUploadListener;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUploadModel;

/**
 * Created by Administrator on 2018/3/5.
 */

public class PhotoUploadModelImpl extends ModelImpl implements IPhotoUploadModel{
    private static final String TAG = "PhotoUploadModelImpl";

    @Override
    public void uploadPhotos(String request_end_flag,String uploadType,String token, String remark,
                             int workflow_content_id, int wk_point_id, List<LocalMedia> files,
                             String type_id, IPhotoUploadListener listener) {

        IPhotoUpload service = retrofit.create(IPhotoUpload.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody remark_courtbody = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(workflow_content_id));
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(wk_point_id));
        RequestBody type_id_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), type_id);
        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

        List<MultipartBody.Part> filesBody = new ArrayList<>();

        for (int i=0; i< files.size();i++){

            if (files.get(i) == null){
                continue;
            }

            File file;
            if (files.get(i).getCompressPath() != null
                    && files.get(i).getCompressPath().length() >0){

                file = new File(files.get(i).getCompressPath());
            }else{

                file = new File(files.get(i).getPath());
            }

            String subfix = file.getName().trim().substring(
                    file.getName().trim().lastIndexOf("."),
                    file.getName().trim().length());

            RequestBody imgFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            if ("7".equals(type_id) && i ==0 && "1".equals(request_end_flag)){
                MultipartBody.Part requestImgPart =
                        MultipartBody.Part.createFormData(file.getName(), "0"+"zheshubiao"+System.currentTimeMillis() +subfix, imgFile);
                filesBody.add(requestImgPart);
            }else{
                MultipartBody.Part requestImgPart =
                        MultipartBody.Part.createFormData(file.getName(), i + ""+System.currentTimeMillis() + subfix, imgFile);
                filesBody.add(requestImgPart);
            }
        }

        Call<ResponseWithNoData> call = service.post( request_end_flag, typebody,tokenbody,remark_courtbody, workflow_content_idbody, wk_point_idbody,type_id_idbody,filesBody);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()){
                    ResponseWithNoData result = response.body();
                    if (result != null ){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed();
                        }else if (result.getCode() == Constant.LoginAnotherPhone){
                            listener.relogin();
                        }else{
                            listener.onFail(result.getMessage());
                        }
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
