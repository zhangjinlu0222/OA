package zjl.com.oa2.LoanInfoList.Model;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ModelImpl;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoList;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoListListener;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoListModel;
import zjl.com.oa2.Response.LoanInfosResponse;

public class LoanInfoListModelImpl extends ModelImpl implements ILoanInfoListModel {
    public static String TAG = "LoanInfoListModelImpl";
    @Override
    public void LoanInfoList(String token, int page_count, String start_date, String end_date,
                             String name, String flag,int order, ILoanInfoListListener listener) {

        ILoanInfoList appraisal = retrofit.create(ILoanInfoList.class);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("page_count",page_count);
        map.put("start_date",start_date);
        map.put("end_date",end_date);
        map.put("name",name);
        map.put("flag",flag);
        map.put("order",order);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<LoanInfosResponse> call = appraisal.LoanInfoList(body);

        call.enqueue(new Callback<LoanInfosResponse>() {
            @Override
            public void onResponse(Call<LoanInfosResponse> call, Response<LoanInfosResponse> response) {
                if (response.isSuccessful()){
                    LoanInfosResponse result = response.body();
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(result.getResult());
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
            public void onFailure(Call<LoanInfosResponse> call, Throwable t) {
                listener.onFail();
            }
        });
    }
}
