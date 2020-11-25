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
import zjl.com.oa2.Response.GetSigningResponse;
import zjl.com.oa2.Sign.Presenter.IInformSign;
import zjl.com.oa2.Sign.Presenter.IInformSignListener;
import zjl.com.oa2.Sign.Presenter.IInformSignModel;

/**
 * Created by Administrator on 2018/3/5.
 */

public class InformSignModelImpl extends ModelImpl implements IInformSignModel {
    private static final String TAG = "InformSignModelImpl";
    @Override
    public void getInformSign(String token, String workflow_content_id, IInformSignListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IInformSign service = retrofit.create(IInformSign.class);


        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<GetSigningResponse> call = service.post(body);

        call.enqueue(new Callback<GetSigningResponse>() {
            @Override
            public void onResponse(Call<GetSigningResponse> call, Response<GetSigningResponse> response) {
                if (response.isSuccessful()){
                    GetSigningResponse result = response.body();
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result.getResult().getList());
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
            public void onFailure(Call<GetSigningResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void postInformSign(String token,String workflow_content_id,String wk_point_id,
                               String service_fee,String pontage,
                               String contract_date,String remark,IInformSignListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IInformSign service = retrofit.create(IInformSign.class);


        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);
        map.put("service_fee",service_fee);
        map.put("pontage",pontage);
        map.put("contract_date",contract_date);
        map.put("remark",remark);
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
}
