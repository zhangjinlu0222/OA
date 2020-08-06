package zjl.com.oa.WorkFlow.Model;

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
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.Response.GetWorkFlowResponse;
import zjl.com.oa.Response.LookContractResponse;
import zjl.com.oa.Response.PhotoVideoDetailResponse;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlow;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowListener;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowModel;

/**
 * Created by Administrator on 2018/3/5.
 */

public class WorkFlowModelImpl extends ModelImpl implements IWorkFlowModel{
    private static final String TAG = "WorkFlowModelImpl";

    @Override
    public void LookRefinanceContract(String token, String w_con_id,String contract_type,IWorkFlowListener listener) {

        IWorkFlow service = retrofit.create(IWorkFlow.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("type",contract_type);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<LookContractResponse> call = service.LookRefinanceContract(body);
        call.enqueue(new Callback<LookContractResponse>() {
            @Override
            public void onResponse(Call<LookContractResponse> call, Response<LookContractResponse> response) {
                if (response.isSuccessful()){
                    LookContractResponse result = response.body();
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
            public void onFailure(Call<LookContractResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void getWorkFlow(String token, String w_con_id,String proc_type_id,IWorkFlowListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(Constant.BASE_URL).build();
        IWorkFlow service = retrofit.create(IWorkFlow.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("proc_type_id",proc_type_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<GetWorkFlowResponse> call = service.getWorkFlow(body);
        call.enqueue(new Callback<GetWorkFlowResponse>() {
            @Override
            public void onResponse(Call<GetWorkFlowResponse> call, Response<GetWorkFlowResponse> response) {
                if (response.isSuccessful()){
                    GetWorkFlowResponse result = response.body();
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
            public void onFailure(Call<GetWorkFlowResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void getWorkDetail(String token, String w_con_id,String proc_type_id, IWorkFlowListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(Constant.BASE_URL).build();
        IWorkFlow service = retrofit.create(IWorkFlow.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("proc_type_id",proc_type_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<GetWorkFlowResponse> call = service.getWorkDetail(body);
        call.enqueue(new Callback<GetWorkFlowResponse>() {
            @Override
            public void onResponse(Call<GetWorkFlowResponse> call, Response<GetWorkFlowResponse> response) {
                if (response.isSuccessful()){
                    GetWorkFlowResponse result = response.body();
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
            public void onFailure(Call<GetWorkFlowResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void HRRejection(String token, String w_con_id, String w_pot_id,String proc_type_id,String remark,  IWorkFlowListener listener) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(Constant.BASE_URL).build();
        IWorkFlow service = retrofit.create(IWorkFlow.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("proc_type_id",proc_type_id);
        map.put("remark",remark);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.HRRejection(body);
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

    @Override
    public void getPhotoVideoDetail(String token, String w_con_id, String w_pot_id,String proc_type_id, IWorkFlowListener listener) {

//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(Constant.BASE_URL).build();
        IWorkFlow service = retrofit.create(IWorkFlow.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        map.put("proc_type_id",proc_type_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<PhotoVideoDetailResponse> call = service.getPhotoVideoDetail(body);
        call.enqueue(new Callback<PhotoVideoDetailResponse>() {
            @Override
            public void onResponse(Call<PhotoVideoDetailResponse> call, Response<PhotoVideoDetailResponse> response) {
                if (response.isSuccessful()){
                    PhotoVideoDetailResponse result = response.body();
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
            public void onFailure(Call<PhotoVideoDetailResponse> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                Log.e(TAG,"网络异常");
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
    @Override
    public void endWorkFlow(String token, int workflow_content_id, int wk_point_id, String remark,String proc_type_id,IWorkFlowListener listener) {

        IWorkFlow service = retrofit.create(IWorkFlow.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("wk_content_id",workflow_content_id);
        map.put("wk_point_id",wk_point_id);
        map.put("remark",remark);
        map.put("proc_type_id",proc_type_id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));


        Call<ResponseWithNoData> call = service.endWorkFlow(body);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()){
                    ResponseWithNoData result = response.body();
                    if (result != null){

                        if (result.getCode() == Constant.Succeed){
                            listener.onSucceed(true);
                            return;
                        }

                        if (result.getCode() == Constant.LoginAnotherPhone){
                            listener.relogin();
                            return;
                        }

                        listener.onFail(result.getMessage());
                    }
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

    @Override
    public void recoverWorkflow(String token, int workflow_content_id,String proc_type_id, IWorkFlowListener listener) {
        IWorkFlow service = retrofit.create(IWorkFlow.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("wk_content_id",workflow_content_id+"");
        map.put("proc_type_id",proc_type_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.recoverWorkflow(body);
        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()){
                    ResponseWithNoData result = response.body();
                    if (result != null){
                        if (result.getCode() == Constant.Succeed){
                            listener.onRecoverSucceed();
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
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
