package zjl.com.oa2.RenewLoanCompletation.Model;

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
import zjl.com.oa2.RenewLoanCompletation.Presenter.IListener;
import zjl.com.oa2.RenewLoanCompletation.Presenter.IModel;
import zjl.com.oa2.RenewLoanCompletation.Presenter.Iinterfaces;

/**
 * Created by Administrator on 2018/3/5.
 */

public class ModelImp extends ModelImpl implements IModel {
    private static final String TAG = "RLEvaluationModelImpl";

    @Override
    public void RefinanceFinishFlow(String token, String workflow_content_id, String wk_point_id,IListener listener) {

        Iinterfaces service = retrofit.create(Iinterfaces.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",workflow_content_id);
        map.put("w_pot_id",wk_point_id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));


        Call<ResponseWithNoData> call = service.RefinanceFinishFlow(body);

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
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
