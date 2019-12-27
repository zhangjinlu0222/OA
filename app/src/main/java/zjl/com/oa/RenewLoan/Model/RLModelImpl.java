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
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.RenewLoan.Presenter.IRL;
import zjl.com.oa.RenewLoan.Presenter.IRLListener;
import zjl.com.oa.RenewLoan.Presenter.IRLModel;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Sign.Presenter.IInformSign;

/**
 * Created by Administrator on 2018/3/5.
 */

public class RLModelImpl extends ModelImpl implements IRLModel {
    private static final String TAG = "RLEvaluationModelImpl";

    @Override
    public void endWorkFlow(String token, int workflow_content_id, int wk_point_id, String remark,IRLListener listener) {

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
    public void InfoCheck(String request_end_flag ,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);

        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());

        RequestBody persion_courtbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("persion_court").toString());
        RequestBody creditbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("credit").toString());

        RequestBody insurancebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("insurance").toString());
        RequestBody insuranceamountbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("insurance_amount").toString());
        RequestBody insuranceenddatebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("insurance_enddate").toString());

        RequestBody car_break_rulesbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_break_rules").toString());
        RequestBody car_break_rules_amountbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_break_rules_amount").toString());

        RequestBody car_check_amountbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_check_amount").toString());
        RequestBody car_check_enddatebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_check_enddate").toString());

        RequestBody legal_personbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("legal_person").toString());
        RequestBody overtime_feebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("overtime_fee").toString());

        RequestBody remarkbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());

        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);


        List<MultipartBody.Part> filesBody = new ArrayList<>();

        int size = 0;
        for (int i=0; i< files.size();i++){
            File file;
            if (files.get(i).getCompressPath() != null
                    && files.get(i).getCompressPath().length() > 0){
                file = new File(files.get(i).getCompressPath());
            }else{
                file = new File(files.get(i).getPath());
            }
            size += file.length();
            RequestBody imgFile = RequestBody.create(MediaType.parse("image/png"), file);
            String subfix = file.getName().trim().substring(
                    file.getName().trim().lastIndexOf("."),
                    file.getName().trim().length());
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(),i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);
        }

        Call<ResponseWithNoData> call = service.InfoCheck(request_end_flag,typebody,tokenbody,workflow_content_idbody,
                persion_courtbody,creditbody,car_break_rulesbody, car_break_rules_amountbody,car_check_amountbody,
                car_check_enddatebody,insurancebody,insuranceamountbody,insuranceenddatebody,legal_personbody,
                overtime_feebody,wk_point_idbody,filesBody,remarkbody);

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
    public void FinishFlow(HashMap<String ,Object> map, IRLListener listener) {

        IRL service = retrofit.create(IRL.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.FinishFlow(body);

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
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void loanApplication(HashMap<String ,Object> map, IRLListener listener) {

        IRL service = retrofit.create(IRL.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.loanApplication(body);

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
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void InformSigned(HashMap<String ,Object> map,IRLListener listener) {

        IInformSign service = retrofit.create(IInformSign.class);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.postInformSign(body);

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
    public void BusFeedback(HashMap<String, Object> map, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.BusFeedback(body);

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
    public void FirstFeedback(HashMap<String ,Object> map, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.FirstFeedback(body);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if(response.isSuccessful()){
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
    public void PleDgeAssess(String request_end_flag, String uploadType, HashMap<String ,Object> map, List<LocalMedia> files, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody car_year_body = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_year").toString());
        RequestBody car_typebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_type").toString());
        RequestBody car_stylebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_style").toString());
        RequestBody milagebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("milage").toString());
        RequestBody remarkbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
        RequestBody market_amount_body = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("market_amount").toString());
        RequestBody take_amount_body = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("take_amount").toString());
        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

        List<MultipartBody.Part> filesBody = new ArrayList<>();

        for (int i=0; i< files.size();i++){
            File file = new File(files.get(i).getPath());
            RequestBody imgFile = RequestBody.create(MediaType.parse("image/png"), file);

            String subfix = file.getName().trim().substring(
                    file.getName().trim().lastIndexOf("."),
                    file.getName().trim().length());
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData( file.getName(),i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);
        }

        Call<ResponseWithNoData> call = service.PleDgeAssess(request_end_flag,
                typebody,tokenbody,car_year_body,car_typebody,car_stylebody, milagebody,
                remarkbody,market_amount_body,take_amount_body,
                workflow_content_idbody,wk_point_idbody,filesBody);

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
    public void Coming(HashMap<String, Object> map, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.Coming(body);

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
    public void ApplyforRefinance(HashMap<String ,Object> map, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
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
    public void CarPhoto(String request_end_flag,String uploadType,HashMap<String ,Object> map,String type_id, List<LocalMedia> files,
                             IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody remark_courtbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
        RequestBody type_id_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), type_id);
        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);
        RequestBody loanLengthBody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("loan_length").toString());

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
    public void FirstSureAmount(HashMap<String, Object> map, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
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
    public void SureAmount(HashMap<String ,Object>map, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
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
    public void InputInfo(HashMap<String ,Object> map,IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
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
    public void UploadCarPhoto(String request_end_flag,String uploadType,HashMap<String ,Object> map, String type_id, List<LocalMedia> files,
                               IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody remark_body = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
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
            if ("7".equals(type_id) && i ==0 && "1" ==request_end_flag){
                MultipartBody.Part requestImgPart =
                        MultipartBody.Part.createFormData(file.getName(), "0"+"zheshubiao"+System.currentTimeMillis() + subfix, imgFile);
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
    public void ContractDetail(HashMap<String,Object> map,
                               IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

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
    public void AuditRefinance(HashMap<String,Object> map, IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

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
    public void AssessRefinance(HashMap<String ,Object> map, IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

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
    public void InfoCheckRefinance(String request_end_flag ,String uploadType,HashMap<String ,Object> map ,List<LocalMedia> files,IRLListener listener) {
        IRL service = retrofit.create(IRL.class);

        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());

        RequestBody persion_courtbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("persion_court").toString());

        RequestBody insurancebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("insurance").toString());
        RequestBody insuranceamountbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("insurance_amount").toString());
        RequestBody insuranceenddatebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("insurance_enddate").toString());

        RequestBody car_break_rulesbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_break_rules").toString());
        RequestBody car_break_rules_amountbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_break_rules_amount").toString());

        RequestBody car_check_amountbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_check_amount").toString());
        RequestBody car_check_enddatebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_check_enddate").toString());

        RequestBody overtime_feebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("overtime_fee").toString());

        RequestBody remarkbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());

        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

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

        Call<ResponseWithNoData> call = service.InfoCheckRefinance(request_end_flag,typebody,tokenbody,workflow_content_idbody,
                persion_courtbody,car_break_rulesbody, car_break_rules_amountbody,car_check_amountbody,
                car_check_enddatebody,insurancebody,insuranceamountbody,insuranceenddatebody,
                overtime_feebody,wk_point_idbody,filesbody,remarkbody);

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
