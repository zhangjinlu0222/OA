package zjl.com.oa.Meeting.Model;

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
import zjl.com.oa.Meeting.Presenter.IMetting;
import zjl.com.oa.Meeting.Presenter.IMettingListener;
import zjl.com.oa.Meeting.Presenter.IMettingModel;
import zjl.com.oa.Response.LookInterviewResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public class MettingModelImpl extends ModelImpl implements IMettingModel{
    private static final String TAG = "MettingModelImpl";
    @Override
    public void LookInterview(String token, String w_con_id, IMettingListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IMetting service = retrofit.create(IMetting.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));


        Call<LookInterviewResponse> call = service.LookInterview(body);

        call.enqueue(new Callback<LookInterviewResponse>() {
            @Override
            public void onResponse(Call<LookInterviewResponse> call, Response<LookInterviewResponse> response) {
                if (response.isSuccessful()){
                    LookInterviewResponse result = response.body();
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
            public void onFailure(Call<LookInterviewResponse> call, Throwable t) {
//                listener.onEndWKFail(t.getMessage());
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void endWorkFlow(String token, int workflow_content_id, int wk_point_id,String remark,
                            IMettingListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IMetting service = retrofit.create(IMetting.class);

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
                            listener.onEndWKSucceed();
                        }else if (result.getCode() == Constant.LoginAnotherPhone){
                            listener.relogin();
                        }else{
                            listener.onEndWKFail(result.getMessage());
                        }
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {
//                listener.onEndWKFail(t.getMessage());
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onEndWKFail("网络异常，拒件失败");
            }
        });
    }

    @Override
    public void Interview(String token, int workflow_content_id, int wk_point_id,

                          List<LocalMedia> photos, String remark,
//                   List<LocalMedia> photos, String product_type_name, String remark,

                          String customer_name, String identity, String customer_phone,
                          String address, String bank_code, String bank_name,

                          String purpose,
//                   double loan_rate, String purpose, int loan_length, String return_amount_method,
                          String car_license, String car_registration, String car_engine_no, String car_vin,
                          IMettingListener listener) {
        IMetting service = retrofit.create(IMetting.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody bd_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody bd_workflow_content_id = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(workflow_content_id));
        RequestBody bd_wk_point_id = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(wk_point_id));


        List<MultipartBody.Part> filesBody = new ArrayList<>();

        for (int i=0; i< photos.size();i++){
            File file;
            if (photos.get(i).getCompressPath() != null
                    && photos.get(i).getCompressPath().length() > 0){
                file = new File(photos.get(i).getCompressPath());
            }else{
                file = new File(photos.get(i).getPath());
            }
            String subfix = file.getName().toString().trim().substring(
                    file.getName().toString().trim().lastIndexOf("."),
                    file.getName().toString().trim().length());

            RequestBody imgFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part requestImgPart =
                        MultipartBody.Part.createFormData( file.getName(),i+""+System.currentTimeMillis() + subfix ,imgFile);
                filesBody.add(requestImgPart);
        }

//        RequestBody bd_product_type_name = RequestBody.create(MediaType.parse("multipart/form-data"), product_type_name);
        RequestBody bd_remark = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody bd_customer_name = RequestBody.create(MediaType.parse("multipart/form-data"), customer_name);
        RequestBody bd_identity = RequestBody.create(MediaType.parse("multipart/form-data"), identity);
        RequestBody bd_customer_phone = RequestBody.create(MediaType.parse("multipart/form-data"), customer_phone);
        RequestBody bd_address = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody bd_bank_code = RequestBody.create(MediaType.parse("multipart/form-data"), bank_code);
        RequestBody bd_bank_name = RequestBody.create(MediaType.parse("multipart/form-data"), bank_name);
//        RequestBody bd_loan_rate = RequestBody.create(MediaType.parse("multipart/form-data"), Double.toString(loan_rate));
        RequestBody bd_purpose = RequestBody.create(MediaType.parse("multipart/form-data"), purpose);
//        RequestBody bd_loan_length = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(loan_length));
//        RequestBody bd_return_amount_method = RequestBody.create(MediaType.parse("multipart/form-data"), return_amount_method);
        RequestBody bd_car_license = RequestBody.create(MediaType.parse("multipart/form-data"), car_license);
        RequestBody bd_car_registration = RequestBody.create(MediaType.parse("multipart/form-data"), car_registration);
        RequestBody bd_car_engine_no = RequestBody.create(MediaType.parse("multipart/form-data"), car_engine_no);
        RequestBody bd_car_vin = RequestBody.create(MediaType.parse("multipart/form-data"), car_vin);


        Call<ResponseWithNoData> call = service.Interview(
                bd_token,bd_workflow_content_id,bd_wk_point_id,
                filesBody,bd_remark,
//                bd_product_type_name,

                bd_customer_name,bd_identity,bd_customer_phone,bd_address,
                bd_bank_code,bd_bank_name,

                bd_purpose,
//                bd_loan_rate,bd_purpose,bd_loan_length,bd_return_amount_method,
                bd_car_license,bd_car_registration,bd_car_engine_no,bd_car_vin);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()){
                    ResponseWithNoData result = response.body();
                    if (result.getCode() == Constant.Succeed){
                        listener.onSucceed();
                    }else if (result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                    }else{
                        listener.onFail(result.getMessage());
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {

//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                listener.onFail("网络异常，操作失败");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void InputInfo(String token, int w_con_id, int w_pot_id,

                          String customer_name, String identity, String customer_phone,
                          String address, String bank_code, String bank_name,

                          String purpose,
//                          double loan_rate, String purpose, int loan_length, String return_amount_method,
                          String car_license, String car_registration, String car_engine_no, String car_vin,

                          String remark,IMettingListener listener) {
        IMetting service = retrofit.create(IMetting.class);


        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);

        map.put("customer_name",customer_name);
        map.put("identity",identity);
        map.put("customer_phone",customer_phone);
        map.put("address",address);
        map.put("bank_code",bank_code);
        map.put("bank_name",bank_name);

//        map.put("loan_rate",loan_rate);
        map.put("purpose",purpose);
//        map.put("loan_length",loan_length);
//        map.put("return_amount_method",return_amount_method);
        map.put("car_license",car_license);
        map.put("car_registration",car_registration);
        map.put("car_engine_no",car_engine_no);
        map.put("car_vin",car_vin);

        map.put("remark",remark);


        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));
        Call<ResponseWithNoData> call = service.InputInfo(body);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()){
                    ResponseWithNoData result = response.body();
                    if (result.getCode() == Constant.Succeed){
                        listener.onSucceed();
                    }else if (result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                    }else{
                        listener.onFail(result.getMessage());
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {

//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                listener.onFail("网络异常，操作失败");
                t.printStackTrace();
            }
        });
    }
}
