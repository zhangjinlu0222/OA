package zjl.com.oa.TransferVoucher.Model;

import android.util.Log;

import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
import zjl.com.oa.Response.GetTransferVoucherResponse;
import zjl.com.oa.TransferVoucher.Presenter.ITransferVoucher;
import zjl.com.oa.TransferVoucher.Presenter.ITransferVoucherListener;
import zjl.com.oa.TransferVoucher.Presenter.ITransferVoucherModel;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUpload;

/**
 * Created by Administrator on 2018/3/5.
 */

public class TransferVoucherModelImpl extends ModelImpl implements ITransferVoucherModel{
    private static final String TAG = "TransVocherModeImpl";
    @Override
    public void getTransferVoucher(String token, String w_con_id, ITransferVoucherListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(Constant.BASE_URL).build();
        ITransferVoucher service = retrofit.create(ITransferVoucher.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<GetTransferVoucherResponse> call = service.getTransferVoucher(body);
        call.enqueue(new Callback<GetTransferVoucherResponse>() {
            @Override
            public void onResponse(Call<GetTransferVoucherResponse> call, Response<GetTransferVoucherResponse> response) {
                if (response.isSuccessful()){
                    GetTransferVoucherResponse result = response.body();
                    if(result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result.getResult().getList());
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
            public void onFailure(Call<GetTransferVoucherResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void uploadPhotos(String request_end_flag,String uploadType,String token, String remark, String workflow_content_id, String wk_point_id, List<LocalMedia> files, String type_id, ITransferVoucherListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IPhotoUpload service = retrofit.create(IPhotoUpload.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody remark_courtbody = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), workflow_content_id);
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), wk_point_id);
        RequestBody type_id_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), type_id);
        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

        List<MultipartBody.Part> filesBody = new ArrayList<>();

        for (int i=0; i< files.size();i++){
            File file = new File(files.get(i).getPath());
            RequestBody imgFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            String subfix = file.getName().toString().trim().substring(
                    file.getName().toString().trim().lastIndexOf("."),
                    file.getName().toString().trim().length());
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(),i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);
        }

        Call<ResponseWithNoData> call = service.post(request_end_flag,typebody,tokenbody,remark_courtbody, workflow_content_idbody, wk_point_idbody,type_id_idbody,filesBody);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()){
                    ResponseWithNoData result = response.body();
                    if (result != null){
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
                t.printStackTrace();
                listener.onFail("网络异常");
            }
        });
    }
}
