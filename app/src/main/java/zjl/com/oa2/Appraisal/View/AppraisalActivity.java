package zjl.com.oa2.Appraisal.View;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Adapter.AppraisalListAdapter;
import zjl.com.oa2.Adapter.QuestListAdapter;
import zjl.com.oa2.Appraisal.Model.AppraisalPresenterImpl;
import zjl.com.oa2.Appraisal.Presenter.IAppraisalView;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.AppraisalResponse;
import zjl.com.oa2.Utils.TitleBarUtil;

public class AppraisalActivity extends BaseActivity implements IAppraisalView, PullToRefreshBase.OnPullEventListener<ListView> {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.appraisal_list)
    PullToRefreshListView appraisalList;
    private String car_type, car_model, car_year;
    private int i_car_year;

    private int currentPage = 1;

    /*
    * 暂时只有升序排序，order 设置为 1
    * */
    private int order = 1;

    private AppraisalPresenterImpl appraisalPresenter;

    private String token;
    private List<AppraisalResponse.Result.Data> data = new ArrayList<>();
    private AppraisalListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraisal);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        ButterKnife.bind(this);
        car_model = getIntent().getStringExtra("car_model");
        car_type = getIntent().getStringExtra("car_type");
        car_year = getIntent().getStringExtra("car_year");
        i_car_year = car_year.length() <=0?0:Integer.parseInt(car_year);
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
        submitDialog.msg = "加载中";
        appraisalPresenter = new AppraisalPresenterImpl(this);

        adapter = new AppraisalListAdapter( this,data, appraisalList);
        appraisalList.setAdapter(adapter);
        appraisalList.setOnPullEventListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appraisalPresenter.QueryCarAssess(token,car_type,car_model,
                i_car_year,currentPage,order);
    }

    @OnClick(R.id.ig_back)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    public void showProgress() {
        if (submitDialog != null){
            submitDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (submitDialog != null){
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        //刷新失败，则之前的pagecount自减一
        if (currentPage > 1){
            currentPage--;
        }
    }

    @Override
    public void refreshData(AppraisalResponse.Result result) {
        if (currentPage == 1){
            data.clear();
        }
        data.addAll(result.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPullEvent(PullToRefreshBase<ListView> refreshView,
                            PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
        if (direction == PullToRefreshBase.Mode.PULL_FROM_START
                && state == PullToRefreshBase.State.RELEASE_TO_REFRESH){

            currentPage = 1;
            appraisalPresenter.QueryCarAssess(token,car_type,car_model,
                    i_car_year,currentPage,order);

        }else if (direction == PullToRefreshBase.Mode.PULL_FROM_END
                && state == PullToRefreshBase.State.RELEASE_TO_REFRESH){

            currentPage++;
            appraisalPresenter.QueryCarAssess(token,car_type,car_model,
                    i_car_year,currentPage,order);
        }
    }
}
