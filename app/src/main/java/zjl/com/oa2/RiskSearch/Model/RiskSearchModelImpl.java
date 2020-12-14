package zjl.com.oa2.RiskSearch.Model;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ModelImpl;
import zjl.com.oa2.Response.BigDataResponse;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearch;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchListener;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchModel;

/**
 * Created by Administrator on 2018/3/2.
 */

public class RiskSearchModelImpl extends ModelImpl implements IRiskSearchModel {

    @Override
    public void GetBigDatas(String token, String w_con_id, String name, String phone, String identity_id, IRiskSearchListener listener) {
        IRiskSearch service = retrofit.create(IRiskSearch.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("name",name);
        map.put("phone",phone);
        map.put("identity_id",identity_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<BigDataResponse> call = service.GetBigDatas(body);

        call.enqueue(new Callback<BigDataResponse>() {
            @Override
            public void onResponse(Call<BigDataResponse> call, Response<BigDataResponse> response) {
                if (response.isSuccessful()){
                    BigDataResponse result = response.body();
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
            public void onFailure(Call<BigDataResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void LookBigDatas(String token, String w_con_id,IRiskSearchListener listener) {
        IRiskSearch service = retrofit.create(IRiskSearch.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<BigDataResponse> call = service.LookBigDatas(body);

        call.enqueue(new Callback<BigDataResponse>() {
            @Override
            public void onResponse(Call<BigDataResponse> call, Response<BigDataResponse> response) {
                if (response.isSuccessful()){
                    BigDataResponse result = response.body();
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
            public void onFailure(Call<BigDataResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
