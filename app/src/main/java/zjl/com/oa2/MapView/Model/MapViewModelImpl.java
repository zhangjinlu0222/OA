package zjl.com.oa2.MapView.Model;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ModelImpl;
import zjl.com.oa2.MapView.Presenter.IMapView;
import zjl.com.oa2.MapView.Presenter.IMapViewListener;
import zjl.com.oa2.MapView.Presenter.IMapViewModel;
import zjl.com.oa2.Response.GPSResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public class MapViewModelImpl extends ModelImpl implements IMapViewModel {

    @Override
    public void GetCarGps(String token, String  w_con_id, IMapViewListener listener) {
        IMapView service = retrofit.create(IMapView.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("w_con_id",w_con_id);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(map));

        Call<GPSResponse> call = service.GetCarGps(body);

        call.enqueue(new Callback<GPSResponse>() {
            @Override
            public void onResponse(Call<GPSResponse> call, Response<GPSResponse> response) {
                if (response.isSuccessful()){
                    GPSResponse result = response.body();
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
            public void onFailure(Call<GPSResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onFail();
            }
        });
    }
}
