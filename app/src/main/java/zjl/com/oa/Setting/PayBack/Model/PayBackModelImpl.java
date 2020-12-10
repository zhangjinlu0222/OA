package zjl.com.oa.Setting.PayBack.Model;

import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ModelImpl;
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.Response.LogoutResponse;
import zjl.com.oa.Response.ModifyPwdResponse;
import zjl.com.oa.Response.SearchNameResponse;
import zjl.com.oa.Setting.PayBack.Presenter.IPayBack;
import zjl.com.oa.Setting.PayBack.Presenter.IPayBackListener;
import zjl.com.oa.Setting.PayBack.Presenter.IPayBackModel;
import zjl.com.oa.Setting.Presenter.ISetting;
import zjl.com.oa.Setting.Presenter.ISettingListener;
import zjl.com.oa.Setting.Presenter.ISettingModel;

import static zjl.com.oa.Utils.CryptLib.md5twice;

/**
 * Created by Administrator on 2018/3/1.
 */

public class PayBackModelImpl extends ModelImpl implements IPayBackModel {
    private static final String TAG = "PayBackModelImpl";
    @Override
    public void SearchName(String token, String name, IPayBackListener listener) {

        Log.e(TAG, token + "/t/n" +name);
        IPayBack service = retrofit.create(IPayBack.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("name",name);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));
        Call<SearchNameResponse> call = service.SearchName(body);
        call.enqueue(new Callback<SearchNameResponse>() {
            @Override
            public void onResponse(Call<SearchNameResponse> call, Response<SearchNameResponse> response) {
                if (response.isSuccessful()){
                    SearchNameResponse result = response.body();
                    Log.e(TAG, JSONObject.toJSONString(result));
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result);
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
            public void onFailure(Call<SearchNameResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void SearchCarType(String token, String w_con_id, IPayBackListener listener) {
        Log.e(TAG, token + "/t/n" +w_con_id);
        IPayBack service = retrofit.create(IPayBack.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));
        Call<SearchNameResponse> call = service.SearchCarType(body);
        call.enqueue(new Callback<SearchNameResponse>() {
            @Override
            public void onResponse(Call<SearchNameResponse> call, Response<SearchNameResponse> response) {
                if (response.isSuccessful()){
                    SearchNameResponse result = response.body();
                    Log.e(TAG, JSONObject.toJSONString(result));
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result);
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
            public void onFailure(Call<SearchNameResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void UpdateReturnSchedule(String token, String amount,String date,String schedule_id, IPayBackListener listener) {
        IPayBack service = retrofit.create(IPayBack.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("amount",amount);
        map.put("date",date);
        map.put("schedule_id",schedule_id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));
        Call<ResponseWithNoData> call = service.UpdateReturnSchedule(body);
        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()){
                    ResponseWithNoData result = response.body();
                    Log.e(TAG, JSONObject.toJSONString(result));
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed("还款成功");
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
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
