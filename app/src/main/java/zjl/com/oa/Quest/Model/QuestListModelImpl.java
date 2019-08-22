package zjl.com.oa.Quest.Model;

import android.os.Handler;
import android.os.Message;

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
import zjl.com.oa.Quest.Presenter.IQuestList;
import zjl.com.oa.Quest.Presenter.IQuestListListener;
import zjl.com.oa.Quest.Presenter.IQuestListModel;
import zjl.com.oa.Response.QuestListResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public class QuestListModelImpl extends ModelImpl implements IQuestListModel{

    /**status 1=默认,2=业务状态,4=业务类型，3=时间
     *
     * order false升序   true降序*/
    @Override
    public void WorkflowListOrder(String token, int status, int pageCount, boolean order, IQuestListListener listener) {

        IQuestList service = retrofit.create(IQuestList.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("status",status);
        map.put("page_count",pageCount);
        map.put("order",order);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<QuestListResponse> call = service.WorkflowListOrder(body);

        call.enqueue(new Callback<QuestListResponse>() {
            @Override
            public void onResponse(Call<QuestListResponse> call, Response<QuestListResponse> response) {
                if (response.isSuccessful()) {
                    QuestListResponse result = response.body();
                    if (result != null && result.getCode() == Constant.Succeed){
                        listener.onSucceed(result.getResult());
                        return;
                    }
                    if (result != null  && result.getCode() == Constant.noData){
                        listener.noData();
                        return;
                    }
                    if (result != null  && result.getCode() == Constant.noMoreData){
                        listener.noMoreData();
                        return;
                    }
                    if (result != null && result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                        return;
                    }

                    listener.onFail(result.getMessage());
                }
            }

            @Override
            public void onFailure(Call<QuestListResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void WorkflowListAdvPage(String token, String status, String proc_type, int page_Count,
                                    String name, int order_type,boolean order, String start_date, String end_date,
                                    IQuestListListener listener) {

        IQuestList service = retrofit.create(IQuestList.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("status",status);
        map.put("proc_type",proc_type);
        map.put("page_count",page_Count);
        map.put("name",name);
        map.put("order_type",order_type);
        map.put("order",order);
        map.put("start_date",start_date);
        map.put("end_date",end_date);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<QuestListResponse> call = service.WorkflowListAdvPage(body);

        call.enqueue(new Callback<QuestListResponse>() {
            @Override
            public void onResponse(Call<QuestListResponse> call, Response<QuestListResponse> response) {
                if (response.isSuccessful()) {
                    QuestListResponse result = response.body();
                    if (result != null && result.getCode() == Constant.Succeed){
                        listener.onSucceed(result.getResult());
                        return;
                    }
                    if (result != null  && result.getCode() == Constant.noData){
                        listener.noData();
                        return;
                    }
                    if (result != null  && result.getCode() == Constant.noMoreData){
                        listener.noMoreData();
                        return;
                    }
                    if (result != null && result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                        return;
                    }

                    listener.onFail(result.getMessage());
                }
            }

            @Override
            public void onFailure(Call<QuestListResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void PointOpertState(String token, int w_con_id, int w_pot_id, IQuestListListener listener) {

        IQuestList service = retrofit.create(IQuestList.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        map.put("w_pot_id",w_pot_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.postPointOpertState(body);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()) {
                    ResponseWithNoData result = response.body();
                    if (result != null && result.getCode() == Constant.Succeed){
                        listener.onSucceed(true);
                        return;
                    }
                    if (result != null && result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                        return;
                    }

                    listener.onFail(result.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void CloseFlow(String token, int w_con_id,IQuestListListener listener) {

        IQuestList service = retrofit.create(IQuestList.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("wk_content_id",w_con_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.CloseFlow(body);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()) {
                    ResponseWithNoData result = response.body();
                    if (result != null && result.getCode() == Constant.Succeed){
                        listener.onSucceed();
                        return;
                    }
                    if (result != null && result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                        return;
                    }

                    listener.onFail(result.getMessage());
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                t.printStackTrace();
                listener.onFail();
            }
        });
    }

    @Override
    public void CreateRefinance(String token, int w_con_id, IQuestListListener listener) {

        IQuestList service = retrofit.create(IQuestList.class);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<ResponseWithNoData> call = service.CreateRefinance(body);

        call.enqueue(new Callback<ResponseWithNoData>() {
            @Override
            public void onResponse(Call<ResponseWithNoData> call, Response<ResponseWithNoData> response) {
                if (response.isSuccessful()) {
                    ResponseWithNoData result = response.body();
                    if (result != null && result.getCode() == Constant.Succeed){
                        listener.onSucceed();
                        return;
                    }
                    if (result != null && result.getCode() == Constant.LoginAnotherPhone){
                        listener.relogin();
                        return;
                    }

                    listener.onFail(result.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWithNoData> call, Throwable t) {
//                listListener.onFail(t.getMessage());
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
