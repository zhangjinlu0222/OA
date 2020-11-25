package zjl.com.oa2.Visit.Model;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ModelImpl;
import zjl.com.oa2.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LogoutResponse;
import zjl.com.oa2.Visit.Presenter.INewVisitor;
import zjl.com.oa2.Visit.Presenter.INewVisitorListener;
import zjl.com.oa2.Visit.Presenter.INewVisitorModel;

/**
 * Created by Administrator on 2018/3/2.
 */

public class NewVisitormodelImpl extends ModelImpl implements INewVisitorModel{
    private static final String TAG = "NewVisitormodelImpl";

    @Override
    public void Form(String token, String workflow_content_id, String wk_point_id, INewVisitorListener listener) {
        INewVisitor service = retrofit.create(INewVisitor.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));


        Call<FormResponse> call = service.Form(body);

        call.enqueue(new Callback<FormResponse>() {
            @Override
            public void onResponse(Call<FormResponse> call, Response<FormResponse> response) {
                if (response.isSuccessful()){
                    FormResponse result = response.body();
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
            public void onFailure(Call<FormResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

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

        Call<LogoutResponse> call = newVisitor.Coming(body);
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
}
