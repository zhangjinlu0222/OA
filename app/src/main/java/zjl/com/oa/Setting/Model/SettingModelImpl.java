package zjl.com.oa.Setting.Model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.ModelImpl;
import zjl.com.oa.Response.LogoutResponse;
import zjl.com.oa.Response.ModifyPwdResponse;
import zjl.com.oa.Setting.Presenter.ISetting;
import zjl.com.oa.Setting.Presenter.ISettingListener;
import zjl.com.oa.Setting.Presenter.ISettingModel;

import static zjl.com.oa.Utils.CryptLib.md5twice;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SettingModelImpl extends ModelImpl implements ISettingModel {
    private static final String TAG = "SettingModelImpl";
    @Override
    public void logout(String token,ISettingListener listener) {

//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ISetting service = retrofit.create(ISetting.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));
        Call<LogoutResponse> call = service.post(body);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void ModifyPwd(String token, String oldPwd, String newPwd, ISettingListener listener) {
        ISetting service = retrofit.create(ISetting.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("oldPwd",md5twice(oldPwd));//密碼加嚴
        map.put("newPwd",md5twice(newPwd));
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));
        Call<ModifyPwdResponse> call = service.ModifyPwd(body);
        call.enqueue(new Callback<ModifyPwdResponse>() {
            @Override
            public void onResponse(Call<ModifyPwdResponse> call, Response<ModifyPwdResponse> response) {
                if (response.isSuccessful()){
                    ModifyPwdResponse result = response.body();
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result.getResult().getToken());
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
            public void onFailure(Call<ModifyPwdResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
