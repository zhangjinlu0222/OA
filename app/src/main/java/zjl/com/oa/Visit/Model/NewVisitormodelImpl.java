package zjl.com.oa.Visit.Model;

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
import zjl.com.oa.Meeting.Presenter.IMetting;
import zjl.com.oa.Response.LogoutResponse;
import zjl.com.oa.Response.LookInterviewResponse;
import zjl.com.oa.Interface.INewVisitor;
import zjl.com.oa.Visit.Presenter.INewVisitorListener;
import zjl.com.oa.Visit.Presenter.INewVisitorModel;

/**
 * Created by Administrator on 2018/3/2.
 */

public class NewVisitormodelImpl extends ModelImpl implements INewVisitorModel{
    private static final String TAG = "NewVisitormodelImpl";

    @Override
    public void newVisitor(String token,String name,int from,int product_type_name,String remark,String comment,String w_con_id,String w_pot_id,INewVisitorListener listener) {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        INewVisitor newVisitor = retrofit.create(INewVisitor.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("name",name);
        map.put("from",from);
        map.put("product_type_name",product_type_name);
        map.put("remark",remark);
        map.put("comment",comment);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<LogoutResponse> call = newVisitor.post(body);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()){
                    int code = response.body().getCode();
                    if (code == Constant.Succeed){
                        listener.onSucceed();
                    }else if(code == Constant.LoginAnotherPhone){
                        listener.relogin();
                    }else{
                        listener.onFail();
                    }
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void LookInterview(String token, String w_con_id, INewVisitorListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        IMetting service = retrofit.create(IMetting.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));


        Call<LookInterviewResponse> call = service.LookInterview(body);

        call.enqueue(new Callback<LookInterviewResponse>() {
            @Override
            public void onResponse(Call<LookInterviewResponse> call, Response<LookInterviewResponse> response) {
                if (response.isSuccessful()){
                    LookInterviewResponse result = response.body();
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
            public void onFailure(Call<LookInterviewResponse> call, Throwable t) {
//                listener.onEndWKFail(t.getMessage());
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
