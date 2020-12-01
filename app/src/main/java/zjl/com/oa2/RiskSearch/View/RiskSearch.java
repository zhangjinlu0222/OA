package zjl.com.oa2.RiskSearch.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;

import java.util.ArrayList;
import java.util.List;

import zjl.com.oa2.Adapter.BigDataAdapter;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.CustomView.MyListView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.BigDataResponse;
import zjl.com.oa2.RiskSearch.Model.RiskSearchPresenterImpl;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchPresenter;
import zjl.com.oa2.RiskSearch.Presenter.IRiskSearchView;
import zjl.com.oa2.Utils.TitleBarUtil;

public class RiskSearch extends BaseActivity implements IRiskSearchView{
    private ListView risksView;
    private IRiskSearchPresenter mRiskSearchPresenter;

    private BuildBean dialog;

    private String w_con_id,token,name,phone,identity_id;

    private BigDataAdapter adapter;

    private List<BigDataResponse.Result.Detections> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_search);

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        initView();

        token = getIntent().getStringExtra("token");
        w_con_id = getIntent().getStringExtra("w_con_id");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        identity_id = getIntent().getStringExtra("identity_id");

        mRiskSearchPresenter = new RiskSearchPresenterImpl(this);

        if (name == null || name.equals("")){
            mRiskSearchPresenter.GetBigDatas(token,w_con_id,name,phone,identity_id);
        }else{
            mRiskSearchPresenter.GetBigDatas(token,w_con_id,name,phone,identity_id);
        }

    }

    private void initView(){
        risksView = findViewById(R.id.risksView);
        findViewById(R.id.ig_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new BigDataAdapter(risksView,data,this);

        risksView.setAdapter(adapter);
    }

    @Override
    public void loadBigDatas(BigDataResponse.Result result) {

        if (result != null && result.getBig_data().size() > 0){
            List<BigDataResponse.Result.Detections> detections = result.getBig_data();
            this.data.clear();
            this.data.addAll(detections);
            this.adapter.notifyDataSetChanged();
        }else{
            showFailureMsg("数据异常请重试");
        }
    }

    @Override
    public void showProgress() {
        if (this.dialog != null){
            dialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (dialog != null){
            DialogUIUtils.dismiss(dialog);
        }
    }
}
