package zjl.com.oa2.MapView.Model;

import zjl.com.oa2.MapView.Presenter.IMapViewListener;
import zjl.com.oa2.MapView.Presenter.IMapViewModel;
import zjl.com.oa2.MapView.Presenter.IMapViewPresenter;
import zjl.com.oa2.MapView.Presenter.IMapViewView;
import zjl.com.oa2.Response.GPSResponse;

/**
 * Created by Administrator on 2018/3/2.
 */

public class MapViewPresenterImpl implements IMapViewPresenter,IMapViewListener {
    private IMapViewView view;
    private IMapViewModel model;
    private boolean isShowing = false;

    public MapViewPresenterImpl(IMapViewView view) {
        this.view = view;
        this.model = new MapViewModelImpl();
    }

    @Override
    public void onSucceed(GPSResponse.Result result) {

        if (view != null){
            isShowing = false;
            view.hideProgress();
        }

        if (view != null){
            view.loadCarGps(result);
        }
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onFail(String msg) {
        if (view != null){
            view.showFailureMsg(msg);
        }

        if (view != null){
            isShowing = false;
            view.hideProgress();
        }
    }

    @Override
    public void relogin() {
        if (view != null){
            view.relogin();
        }

        if (view != null){
            isShowing = false;
            view.hideProgress();
        }
    }

    @Override
    public void GetCarGps(String token, String w_con_id) {
        if (model != null){
            model.GetCarGps(token,w_con_id ,this );
        }
    }
}
