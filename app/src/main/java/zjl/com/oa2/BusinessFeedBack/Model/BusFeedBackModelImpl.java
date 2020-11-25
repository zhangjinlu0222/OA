package zjl.com.oa2.BusinessFeedBack.Model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ModelImpl;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBack;
import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBackListener;
import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBackModel;
import zjl.com.oa2.Response.BusFirstFeedBackResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public class BusFeedBackModelImpl extends ModelImpl implements IBusFeedBackModel {
    private static final String TAG = "BusFeedBackModelImpl";

    @Override
    public void LookFirstSureAmount(String token, String w_con_id, IBusFeedBackListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IBusFeedBack service = retrofit.create(IBusFeedBack.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<BusFirstFeedBackResponse> call = service.LookFirstSureAmount(body);

        call.enqueue(new Callback<BusFirstFeedBackResponse>() {
            @Override
            public void onResponse(Call<BusFirstFeedBackResponse> call, Response<BusFirstFeedBackResponse> response) {
                if(response.isSuccessful()){
                    BusFirstFeedBackResponse result = response.body();
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
            public void onFailure(Call<BusFirstFeedBackResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void BusFeedback(String token, String w_con_id, String w_pot_id, int loan_length, float loan_rate,
                            String return_amount_method,String remark,IBusFeedBackListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
        IBusFeedBack service = retrofit.create(IBusFeedBack.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("loan_length",loan_length);
        map.put("loan_rate",loan_rate);
        map.put("return_amount_method",return_amount_method);
        map.put("remark",remark);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.BusFeedback(body);

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
    public void BusFirstFeedback(String token, String w_con_id, String w_pot_id, String remark,IBusFeedBackListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
        IBusFeedBack service = retrofit.create(IBusFeedBack.class);
        // 创建RequestBody，传入参数："multipart/form-data"，String
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("remark",remark);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.BusFirstFeedback(body);

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
}
