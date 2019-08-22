package zjl.com.oa.LoanRequest.Model;

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
import zjl.com.oa.Base.ResponseWithNoData;
import zjl.com.oa.LoanRequest.Presenter.ILoanRequest;
import zjl.com.oa.LoanRequest.Presenter.ILoanRequestListener;
import zjl.com.oa.LoanRequest.Presenter.ILoanRequestModel;
import zjl.com.oa.Response.GetLoanRequestResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public class LoanRequestModelImpl extends ModelImpl implements ILoanRequestModel {
    private static final String TAG = "LoanRequestModelImpl";
    @Override
    public void lookLoanApplication(String token, String workflow_content_id, ILoanRequestListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        ILoanRequest service = retrofit.create(ILoanRequest.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<GetLoanRequestResponse> call = service.lookLoanApplication(body);

        call.enqueue(new Callback<GetLoanRequestResponse>() {
            @Override
            public void onResponse(Call<GetLoanRequestResponse> call, Response<GetLoanRequestResponse> response) {
                if (response.isSuccessful()){
                    GetLoanRequestResponse result = response.body();
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
            public void onFailure(Call<GetLoanRequestResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void loanApplication(String token, String w_con_id, String w_pot_id,String remark, ILoanRequestListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        ILoanRequest service = retrofit.create(ILoanRequest.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("remark",remark);
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
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
