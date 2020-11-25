package zjl.com.oa2.Login.Model;

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
import zjl.com.oa2.Login.Presenter.ILogin;
import zjl.com.oa2.Login.Presenter.ILoginListener;
import zjl.com.oa2.Login.Presenter.ILoginModel;
import zjl.com.oa2.Response.LoginResponse;

import static zjl.com.oa2.Utils.CryptLib.md5twice;

/**
 * Created by Administrator on 2018/2/11.
 */

public class LoginModelImpl extends ModelImpl implements ILoginModel {
    private static final String TAG = "LoginModelImpl";
    @Override
    public void login(String account, String pwd, final ILoginListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        ILogin service = retrofit.create(ILogin.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("phone",account);
        map.put("pwd",md5twice(pwd));
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<LoginResponse> call = service.post(body);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    LoginResponse result = response.body();
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result.getResult());
                        }else{
                            listener.onFail(result.getMessage());
                        }
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
