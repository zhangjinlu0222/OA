package zjl.com.oa2.RenewLoan.Model;

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
import retrofit2.http.Part;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ModelImpl;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa2.QuestAndSetting.Presenter.ISearch;
import zjl.com.oa2.QuestAndSetting.Presenter.ISearchListener;
import zjl.com.oa2.RenewLoan.Presenter.IRL;
import zjl.com.oa2.RenewLoan.Presenter.IRLListener;
import zjl.com.oa2.RenewLoan.Presenter.IRLModel;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.SearchResponse;
import zjl.com.oa2.Sign.Presenter.IInformSign;

/**
 * Created by Administrator on 2018/3/5.
 */

public class RLModelImpl extends ModelImpl implements IRLModel {
    private static final String TAG = "RLEvaluationModelImpl";


    @Override
    public void AdvanceSecInfo(IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        Call<SearchResponse> call = service.AdvanceSecInfo();

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    SearchResponse result = response.body();
                    if (result != null && result.getCode() == Constant.Succeed){
                        listener.onSucceed(result.getResult());
                        return;
                    }
                    if (result != null && result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void Sign(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                     List<LocalMedia> files,IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody workflow_content_id = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_id = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
        RequestBody customer_name = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("customer_name").toString());
        RequestBody identity = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("identity").toString());
        RequestBody customer_phone = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("customer_phone").toString());
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("address").toString());
        RequestBody bank_code = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("bank_code").toString());
        RequestBody bank_name = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("bank_name").toString());
        RequestBody purpose = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("purpose").toString());
        RequestBody car_license = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("car_license").toString());
        RequestBody car_registration = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("car_registration").toString());
        RequestBody car_engine_no = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("car_engine_no").toString());
        RequestBody car_vin = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("car_vin").toString());
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"),uploadType);
        RequestBody remark = RequestBody.create(MediaType.parse("multipart/form-data"),map.get("remark").toString());
        RequestBody loan_length = RequestBody.create(MediaType.parse("multipart/form-data"),  map.get("loan_length").toString());
        RequestBody contract_date = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("contract_date").toString());

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

        Call<ResponseWithNoData> call = service.Sign( request_end_flag,tokenbody,workflow_content_id,wk_point_id,customer_name,identity,customer_phone,address,
                bank_code,bank_name,purpose,car_license,car_registration,car_engine_no,car_vin,
                remark,type,loan_length,contract_date,filesBody);

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
    public void TransferOwnership(String request_end_flag,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files,
                            IRLListener listener) {
        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody w_con_id = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody w_pot_id = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
        RequestBody new_car_license = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("new_car_license").toString());
        RequestBody insurance_enddate = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("insurance_enddate").toString());
        RequestBody car_check_enddate = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_check_enddate").toString());
        RequestBody remark = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

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
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(),  i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);
        }

        Call<ResponseWithNoData> call = service.TransferOwnership(
                request_end_flag,token,w_con_id, w_pot_id,new_car_license, insurance_enddate,car_check_enddate,remark,type,filesBody);

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
    public void LookLoanDetail(HashMap<String ,Object> map,IRLListener listener) {

        IRL service = retrofit.create(IRL.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));


        Call<LoanDetailResponse> call = service.LookLoanDetail(body);

        call.enqueue(new Callback<LoanDetailResponse>() {
            @Override
            public void onResponse(Call<LoanDetailResponse> call, Response<LoanDetailResponse> response) {
                if (response.isSuccessful()){
                    LoanDetailResponse result = response.body();
                    if (result != null ){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result.getResult());
                        }else if (result.getCode() == Constant.LoginAnotherPhone){
                            listener.relogin();
                        }else{
                            listener.onFail(result.getMessage());
                        }
                    }
                }else{
                    listener.onFail("网络异常");
                }
            }

            @Override
            public void onFailure(Call<LoanDetailResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void endWorkFlow(String token, int workflow_content_id, int wk_point_id, String remark,String  proc_type_id,IRLListener listener) {

        IInfoCheck service = retrofit.create(IInfoCheck.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("wk_content_id",workflow_content_id);
        map.put("wk_point_id",wk_point_id);
        map.put("remark",remark);
        map.put("proc_type_id",proc_type_id);

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
    public void HRInterview(String request_end_flag,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files,
                          IRLListener listener) {
        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody w_con_id = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody w_pot_id = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
        RequestBody length = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("length").toString());
        RequestBody identity_id = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("identity_id").toString());
        RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("phone").toString());
        RequestBody purpose = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("purpose").toString());
        RequestBody amount = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("amount").toString());
        RequestBody a_name = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("a_name").toString());
        RequestBody a_desc = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("a_desc").toString());
        RequestBody a_phone = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("a_phone").toString());
        RequestBody b_name = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("b_name").toString());
        RequestBody b_desc = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("b_desc").toString());
        RequestBody b_phone = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("b_phone").toString());
        RequestBody c_name = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("c_name").toString());
        RequestBody c_desc = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("c_desc").toString());
        RequestBody c_phone = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("c_phone").toString());
        RequestBody diya_desc = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("diya_desc").toString());
        RequestBody zhiya_desc = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("zhiya_desc").toString());
        RequestBody situation = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("situation").toString());
        RequestBody remark = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

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
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(),  i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);

        }

        Call<ResponseWithNoData> call = service.HRInterview(
                request_end_flag,token,w_con_id, w_pot_id,length, identity_id,phone,purpose,amount,
                a_name,a_desc,a_phone,b_name,b_desc,b_phone,c_name,c_desc,c_phone,
                diya_desc,zhiya_desc,situation,remark,type,filesBody);

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
    public void InfoCheck(String request_end_flag ,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);

        RequestBody identity = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("identity").toString());
        RequestBody customer_name = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("customer_name").toString());
        RequestBody customer_phone = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("customer_phone").toString());

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

        RequestBody legal_personbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("legal_person").toString());
        RequestBody overtime_feebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("overtime_fee").toString());

        RequestBody remarkbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody is_litigation = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("is_litigation").toString());

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

        Call<ResponseWithNoData> call = service.InfoCheck(request_end_flag,identity,customer_name,customer_phone,
                typebody,tokenbody,workflow_content_idbody,
                persion_courtbody,car_break_rulesbody, car_break_rules_amountbody,car_check_amountbody,
                car_check_enddatebody,insurancebody,insuranceamountbody,insuranceenddatebody,legal_personbody,
                overtime_feebody,wk_point_idbody,remarkbody,is_litigation,filesBody);

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
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
        RequestBody car_year_body = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_year").toString());
        RequestBody car_typebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_type").toString());
        RequestBody car_stylebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("car_style").toString());
        RequestBody milagebody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("milage").toString());
        RequestBody remarkbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody market_amount_body = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("market_amount").toString());
        RequestBody discount = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("discount").toString());
        RequestBody discount_reason = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("discount_reason").toString());
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
                remarkbody,market_amount_body,discount,discount_reason,
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
    public void HRSureAmount(HashMap<String ,Object>map, IRLListener listener) {
        IRL service = retrofit.create(IRL.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.HRSureAmount(body);

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
    public void HRUploadData(String request_end_flag,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files,
                               IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("token").toString());
        RequestBody remark_body = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("remark").toString());
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_con_id").toString());
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get("w_pot_id").toString());
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
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData(file.getName(),  i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);

        }

        Call<ResponseWithNoData> call = service.HRUploadData(request_end_flag,type_body,tokenbody, workflow_content_idbody,remark_body, wk_point_idbody,filesBody);

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
    public void Form(String token, int workflow_content_id, int wk_point_id,String proc_type_id,IRLListener listener) {

        IRL service = retrofit.create(IRL.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);
        map.put("proc_type_id",proc_type_id);

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
