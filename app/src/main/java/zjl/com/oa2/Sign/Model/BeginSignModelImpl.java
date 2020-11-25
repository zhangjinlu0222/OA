package zjl.com.oa2.Sign.Model;

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
import zjl.com.oa2.Response.GetBeginSignResponse;
import zjl.com.oa2.Sign.Presenter.IBeginSign;
import zjl.com.oa2.Sign.Presenter.IBeginSignListener;
import zjl.com.oa2.Sign.Presenter.IBeginSignModel;

/**
 * Created by Administrator on 2018/3/5.
 */

public class BeginSignModelImpl extends ModelImpl implements IBeginSignModel {
    private static final String TAG = "BeginSignModelImpl";

    @Override
    public void getBeginSign(String token, String workflow_content_id, IBeginSignListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IBeginSign service = retrofit.create(IBeginSign.class);


        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<GetBeginSignResponse> call = service.get(body);

        call.enqueue(new Callback<GetBeginSignResponse>() {
            @Override
            public void onResponse(Call<GetBeginSignResponse> call, Response<GetBeginSignResponse> response) {
                if (response.isSuccessful()){
                    GetBeginSignResponse result = response.body();
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
            public void onFailure(Call<GetBeginSignResponse> call, Throwable t) {

//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void postBeginSign(String token,String workflow_content_id,String wk_point_id,IBeginSignListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IBeginSign service = retrofit.create(IBeginSign.class);


        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.post(body);

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
}
