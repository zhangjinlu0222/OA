package zjl.com.oa.Appraisal.Model;

import android.content.Intent;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Appraisal.Presenter.IAppraisal;
import zjl.com.oa.Appraisal.Presenter.IAppraisalListener;
import zjl.com.oa.Appraisal.Presenter.IAppraisalModel;
import zjl.com.oa.Base.ModelImpl;
import zjl.com.oa.Response.AppraisalResponse;

public class AppraisalModelImpl extends ModelImpl implements IAppraisalModel {
    public static String TAG = "AppraisalModelImpl";
    @Override
    public void QueryCarAssess(String token, String car_branch, String car_model, int car_year,
                               int page_count, int order, IAppraisalListener listener) {

        IAppraisal appraisal = retrofit.create(IAppraisal.class);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("car_branch",car_branch);
        map.put("car_model",car_model);
        map.put("car_year",car_year);
        map.put("page_count",page_count);
        map.put("order",order);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<AppraisalResponse> call = appraisal.QueryCarAssess(body);

        call.enqueue(new Callback<AppraisalResponse>() {
            @Override
            public void onResponse(Call<AppraisalResponse> call, Response<AppraisalResponse> response) {
                if (response.isSuccessful()){
                    AppraisalResponse result = response.body();
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result.getResult());
                        }else if (result.getCode() == Constant.LoginAnotherPhone){
                            listener.relogin();
                        }else{
                            listener.onFail(result.getMessage());
                        }
                    }else{
                        listener.onFail();
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<AppraisalResponse> call, Throwable t) {
                listener.onFail();
            }
        });
    }
}
