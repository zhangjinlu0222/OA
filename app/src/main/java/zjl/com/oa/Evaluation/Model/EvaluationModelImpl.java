package zjl.com.oa.Evaluation.Model;

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
import zjl.com.oa.Evaluation.Presenter.IEvaluation;
import zjl.com.oa.Evaluation.Presenter.IEvaluationListener;
import zjl.com.oa.Evaluation.Presenter.IEvaluationModel;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckListener;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public class EvaluationModelImpl extends ModelImpl implements IEvaluationModel {
    private static final String TAG = "EvaluationModelImpl";

    @Override
    public void uploadMsg(String request_end_flag,String uploadType,
                          String token, int car_year, String car_type, String car_style,String milage,
                          String remark,String market_amount, String take_amount,
                          int workflow_content_id, int wk_point_id,
                          List<LocalMedia> files, IEvaluationListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IEvaluation service = retrofit.create(IEvaluation.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        RequestBody tokenbody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody car_year_body = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(car_year));
        RequestBody car_typebody = RequestBody.create(MediaType.parse("multipart/form-data"), car_type);
        RequestBody car_stylebody = RequestBody.create(MediaType.parse("multipart/form-data"), car_style);
        RequestBody milagebody = RequestBody.create(MediaType.parse("multipart/form-data"), milage);
        RequestBody remarkbody = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody workflow_content_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(workflow_content_id));
        RequestBody wk_point_idbody = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(wk_point_id));
        RequestBody market_amount_body = RequestBody.create(MediaType.parse("multipart/form-data"), market_amount);
        RequestBody take_amount_body = RequestBody.create(MediaType.parse("multipart/form-data"), take_amount);
        RequestBody typebody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadType);

        List<MultipartBody.Part> filesBody = new ArrayList<>();

        for (int i=0; i< files.size();i++){
            File file = new File(files.get(i).getPath());
            RequestBody imgFile = RequestBody.create(MediaType.parse("image/png"), file);

            String subfix = file.getName().toString().trim().substring(
                    file.getName().toString().trim().lastIndexOf("."),
                    file.getName().toString().trim().length());
            MultipartBody.Part requestImgPart =
                    MultipartBody.Part.createFormData( file.getName(),i+""+System.currentTimeMillis() + subfix, imgFile);
            filesBody.add(requestImgPart);
        }

        Call<ResponseWithNoData> call = service.postPledgeAssess(request_end_flag,
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
    public void Form(String token, int workflow_content_id, int wk_point_id, IEvaluationListener listener) {

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
