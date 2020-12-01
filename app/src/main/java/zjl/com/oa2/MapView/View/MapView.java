package zjl.com.oa2.MapView.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;

import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.MapView.Model.MapViewPresenterImpl;
import zjl.com.oa2.MapView.Presenter.IMapViewPresenter;
import zjl.com.oa2.MapView.Presenter.IMapViewView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.GPSResponse;
import zjl.com.oa2.Utils.TitleBarUtil;

public class MapView extends BaseActivity implements IMapViewView {
    private IMapViewPresenter mapViewPresenter;

    private BuildBean dialog;

    private com.baidu.mapapi.map.MapView mMapView;
    private BaiduMap mBaiduMap;

    private LocationClient mLocClient;
    // 是否首次定位
    boolean isFirstLoc = true;
    private BitmapDescriptor bitmapA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
    private BitmapDescriptor bitmapB = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka_grey);
    private Marker mMarker;

    private String w_con_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        dialog = DialogUIUtils.showLoading(this, "加载中", true, false, false, true);

        mapViewPresenter = new MapViewPresenterImpl(this);

        SDKInitializer.initialize(getApplicationContext());

        initView();

        w_con_id = getIntent().getStringExtra("w_con_id");

        mapViewPresenter.GetCarGps(UserInfo.getInstance(context).getUserInfo("token"),w_con_id);
    }


    private void initView(){
        findViewById(R.id.ig_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mMapView = findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();

        // 定位初始化
        initLocation();
    }

    /**
     * 定位初始化
     */
    public void initLocation(){
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        MyLocationListener myListener = new MyLocationListener();
//        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        // 打开gps
        option.setOpenGps(true);
        // 设置坐标类型
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void loadCarGps(GPSResponse.Result result) {
        Log.e("TAG",result.getLat() + result.getLon() );
        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())// 设置定位数据的精度信息，单位：米
//                .direction(location.getDirection()) // 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(Double.parseDouble(result.getLat()))
                .longitude(Double.parseDouble(result.getLon()))
                .build();
        // 设置定位数据, 只有先允许定位图层后设置数据才会生效
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng latLng = new LatLng(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLon()));
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(latLng).zoom(20.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            addMarker(latLng,result.getIs_online(),result.getGps_time());
        }
        if (mMarker != null){
            mMarker.setPosition(new LatLng(Double.parseDouble(result.getLat()), Double.parseDouble(result.getLon())));
        }
    }

    @Override
    public void showProgress() {
        if (dialog != null){
            dialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (dialog != null){
            DialogUIUtils.dismiss(dialog);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmapA.recycle();
        bitmapB.recycle();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        // 在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }

    /**
     * 添加marker
     *
     * @param latLng 经纬度
     */
    public void addMarker(LatLng latLng,String isOnline,String gpsTime){
        if (latLng.latitude == 0.0 || latLng.longitude == 0.0){
            return;
        }

        View view = LayoutInflater.from(this).inflate(R.layout.marker,null );
        TextView tvMarker = view.findViewById(R.id.tv_marker);
        tvMarker.setText(gpsTime);
        ImageView ivMarker = view.findViewById(R.id.ig_marker);
        if (isOnline != null && isOnline.equals("1")){
            ivMarker.setBackgroundResource(R.drawable.icon_marka);
        }else{
            ivMarker.setBackgroundResource(R.drawable.icon_marka_grey);
        }
        BitmapDescriptor bd = BitmapDescriptorFactory.fromView(view);
        MarkerOptions markerOptionsB = new MarkerOptions().position(latLng).yOffset(30).icon(bd).draggable(true);
        mMarker = (Marker) mBaiduMap.addOverlay(markerOptionsB);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            // MapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())// 设置定位数据的精度信息，单位：米
                    .direction(location.getDirection()) // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            // 设置定位数据, 只有先允许定位图层后设置数据才会生效
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(20.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                addMarker(latLng,"1","");
            }
            if (mMarker != null){
                mMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            }
        }
    }
}
