package zjl.com.oa.RenewLoan.Model;

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
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuota;
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckListener;
import zjl.com.oa.Meeting.Presenter.IMetting;
import zjl.com.oa.Meeting.Presenter.IMettingListener;
import zjl.com.oa.RenewLoan.Presenter.IRL;
import zjl.com.oa.RenewLoan.Presenter.IRLListener;
import zjl.com.oa.RenewLoan.Presenter.IRLModel;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUpload;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUploadListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public class RLModelImpl extends ModelImpl implements IRLModel {
    private static final String TAG = "RLEvaluationModelImpl";

    @Override
    public void ApplyforRefinance(String token, String w_con_id, String w_pot_id,
                                  String loan_length, String remark, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("loan_length",loan_length);
        map.put("remark",remark);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.ApplyforRefinance(body);

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
                listener.onFail("网络异常，操作失败");
            }
        });
    }

    @Override
    public void CarPhoto(String request_end_flag,String uploadType,String token, String remark,
                             int workflow_content_id, int wk_point_id, List<LocalMedia> files,
                             String type_id,String loan_length, IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody remark_courtbody = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(workflow_content_id));
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(wk_point_id));
        RequestBody type_id_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), type_id);
        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);
        RequestBody loanLengthBody = RequestBody.create(MediaType.parse("multipart/form-data"), loan_length);

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

            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(), i + ""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);

        }

        Call<ResponseWithNoData> call = service.post( request_end_flag, typebody,
                tokenbody,remark_courtbody, workflow_content_idbody,
                wk_point_idbody,type_id_idbody,loanLengthBody,filesBody);

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
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void FirstSureAmount(String token, String w_con_id, String w_pot_id, String amount, String remark, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("amount",amount);
        map.put("remark",remark);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.FirstSureAmount(body);

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
                listener.onFail("网络异常，操作失败");
            }
        });
    }

    @Override
    public void SureAmount(String token, String w_con_id, String w_pot_id,
                           String amount, String assure_amount,String derating_opinion, String remark, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("amount",amount);
        map.put("assure_amount",assure_amount);
        map.put("derating_opinion",derating_opinion);
        map.put("remark",remark);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.SureAmount(body);

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
                listener.onFail("网络异常，操作失败");
            }
        });
    }

    @Override
    public void SureAmountReturn(String token, String w_con_id, String w_pot_id, String type_id, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("type_id",type_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.SureAmountReturn(body);

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
                listener.onFail("网络异常，操作失败");
            }
        });
    }

    @Override
    public void InputInfo(String token, int w_con_id, int w_pot_id,

                          String customer_name, String identity, String customer_phone,
                          String address, String bank_name,String bank_code,

                          String purpose,
//                          double loan_rate, String purpose, int loan_length, String return_amount_method,
                          String car_license, String car_registration, String car_engine_no, String car_vin,

                          String remark,IRLListener listener) {
        IRL service = retrofit.create(IRL.class);


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

        map.put("purpose",purpose);
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

                listener.onFail("网络异常，操作失败");
                t.printStackTrace();
            }
        });
    }
    @Override
    public void UploadCarPhoto(String request_end_flag,String uploadType,String token, int workflow_content_id, String remark,
                               int wk_point_id, String type_id, List<LocalMedia> files,
                               IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody remark_body = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(workflow_content_id));
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(wk_point_id));
        RequestBody type_id_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), type_id);
        RequestBody type_body = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

        List<MultipartBody.Part> filesBody = new ArrayList<>();

        for (int i=0; i< files.size();i++){

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
            if ("7".equals(type_id) && i ==0){
                MultipartBody.Part requestImgPart =
                        MultipartBody.Part.createFormData(file.getName(), "0"+"zheshubiao"+System.currentTimeMillis(), imgFile);
                filesBody.add(requestImgPart);
            }else{
                MultipartBody.Part requestImgPart =
                        MultipartBody.Part.createFormData(file.getName(),  i+""+System.currentTimeMillis() + subfix, imgFile);
                filesBody.add(requestImgPart);
            }
        }

        Call<ResponseWithNoData> call = service.UploadCarPhoto(request_end_flag,type_body,tokenbody, workflow_content_idbody,remark_body, wk_point_idbody,type_id_idbody,filesBody);

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
    public void ContractDetail(String token, int workflow_content_id, int wk_point_id, String remark,
                               String amount, String loan_rate, String loan_length, String pontage,
                               String service_fee, String insurance, String car_break_rules,String contract_date,
                               IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);
        map.put("remark",remark);
        map.put("amount",amount);
        map.put("loan_rate",loan_rate);
        map.put("loan_length",loan_length);
        map.put("pontage",pontage);
        map.put("service_fee",service_fee);
        map.put("insurance",insurance);
        map.put("car_break_rules",car_break_rules);
        map.put("contract_date",contract_date);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.ContractDetail(body);

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
                listener.onFail();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void AuditRefinance(String token, int workflow_content_id, int wk_point_id, String remark, IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);
        map.put("remark",remark);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.AuditRefinance(body);

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
                listener.onFail();
                t.printStackTrace();
            }
        });
    }
    @Override
    public void AssessRefinance(String token, int workflow_content_id, int wk_point_id, String file_info, String derate_info, String remark, IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);
        map.put("file_info",file_info);
        map.put("derate_info",derate_info);
        map.put("remark",remark);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.AssessRefinance(body);

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
                listener.onFail();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void InfoCheckRefinance(String request_end_flag ,String uploadType,String token, int workflow_content_id,
                                   int wk_point_id,String persion_court, String car_break_rules,
                                   String insurance,String remark ,List<LocalMedia> files,IRLListener listener) {
        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"),
                Integer.toString(workflow_content_id));
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"),
                Integer.toString(wk_point_id));
        RequestBody persion_court_body = RequestBody.create(MediaType.parse("multipart/form-data"), persion_court);
        RequestBody car_break_rules_body = RequestBody.create(MediaType.parse("multipart/form-data"), car_break_rules);
        RequestBody insurance_body = RequestBody.create(MediaType.parse("multipart/form-data"), insurance);
        RequestBody remark_body = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody type_body = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

        List<MultipartBody.Part> filesbody = new ArrayList<>();

        for (int i=0; i< files.size();i++){

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
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(),  i+""+System.currentTimeMillis() + subfix, imgFile);
            filesbody.add(requestImgPart);
        }

        Call<ResponseWithNoData> call = service.InfoCheckRefinance(
                request_end_flag,type_body,tokenbody,workflow_content_idbody,wk_point_idbody,
                persion_court_body,car_break_rules_body,insurance_body,remark_body,
                filesbody);

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
                Log.e("TAG","网络异常");
                listener.onFail();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void Form(String token, int workflow_content_id, int wk_point_id,IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

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
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
