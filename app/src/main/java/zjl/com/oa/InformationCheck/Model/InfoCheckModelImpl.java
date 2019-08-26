package zjl.com.oa.InformationCheck.Model;

import android.graphics.Bitmap;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ModelImpl;
import zjl.com.oa.Base.ProgressRequestBody;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckModel;
import zjl.com.oa.RenewLoan.Presenter.IRL;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Utils.BitmapUtil;

import static zjl.com.oa.Utils.BitmapUtil.compressImage;

/**
 * Created by Administrator on 2018/2/11.
 */

public class InfoCheckModelImpl extends ModelImpl implements IInfoCheckModel {
    private static final String TAG = "InfoCheckModelImpl";

    @Override
    public void endWorkFlow(String token, int workflow_content_id, int wk_point_id, String remark,IInfoCheckListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IInfoCheck service = retrofit.create(IInfoCheck.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("wk_content_id",workflow_content_id);
        map.put("wk_point_id",wk_point_id);
        map.put("remark",remark);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));


        Call<ResponseWithNoData> call = service.endWorkFlow(body);

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
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void uploadMsg(String request_end_flag ,String uploadType,String token, int workflow_content_id,
                          String persion_court, String credit,
                          String car_break_rules, String insurance, String legal_person,
                          int wk_point_id, List<LocalMedia> files,String remark, IInfoCheckListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IInfoCheck service = retrofit.create(IInfoCheck.class);


        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(workflow_content_id));
        RequestBody persion_courtbody = RequestBody.create(MediaType.parse("multipart/form-data"), persion_court);
        RequestBody creditbody = RequestBody.create(MediaType.parse("multipart/form-data"), credit);
        RequestBody car_break_rulesbody = RequestBody.create(MediaType.parse("multipart/form-data"), car_break_rules);
        RequestBody insurancebody = RequestBody.create(MediaType.parse("multipart/form-data"), insurance);
        RequestBody legal_personbody = RequestBody.create(MediaType.parse("multipart/form-data"), legal_person);
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(wk_point_id));
        RequestBody remarkbody = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

        List<MultipartBody.Part> filesBody = new ArrayList<>();

        int size = 0;
        for (int i=0; i< files.size();i++){
            File file;
            if (files.get(i).getCompressPath() != null
                    && files.get(i).getCompressPath().length() > 0){
                file = new File(files.get(i).getCompressPath());
//                file = new File(BitmapUtil.compressImage(files.get(i).getCompressPath()));
            }else{
                file = new File(files.get(i).getPath());
//                file = new File(BitmapUtil.compressImage(files.get(i).getPath()));
            }
            size += file.length();
            RequestBody imgFile = RequestBody.create(MediaType.parse("image/png"), file);
            String subfix = file.getName().toString().trim().substring(
                    file.getName().toString().trim().lastIndexOf("."),
                    file.getName().toString().trim().length());
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(),i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);
        }

        Log.e(TAG,size + "");
        Call<ResponseWithNoData> call = service.post(request_end_flag,typebody,tokenbody,workflow_content_idbody,
                persion_courtbody,creditbody,
                car_break_rulesbody, insurancebody,legal_personbody,
                wk_point_idbody,filesBody,remarkbody);

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
                listener.onFail("网络异常");
            }
        });
    }

    @Override
    public void Form(String token, int workflow_content_id, int wk_point_id, IInfoCheckListener listener) {

        IInfoCheck service = retrofit.create(IInfoCheck.class);

            HashMap<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("w_con_id",workflow_content_id);
            map.put("w_pot_id",wk_point_id);

            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));


            Call<FormResponse> call = service.Form(body);

            call.enqueue(new Callback<FormResponse>() {
                @Override
                public void onResponse(Call<FormResponse> call, Response<FormResponse> response) {
                    if (response.isSuccessful()){
                        FormResponse result = response.body();
                        if (result != null){
                            if (result.getCode() == Constant.Succeed){
                                listener.onSucceed(result.getResult());
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
                public void onFailure(Call<FormResponse> call, Throwable t) {
                    t.printStackTrace();
                    listener.onFail();
                }
            });
    }
}
