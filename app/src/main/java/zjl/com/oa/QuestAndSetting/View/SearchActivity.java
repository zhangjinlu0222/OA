package zjl.com.oa.QuestAndSetting.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIDateTimeSaveListener;
import com.dou361.dialogui.widget.DateSelectorWheelView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.QuestAndSetting.Model.ISearchPresenterImpl;
import zjl.com.oa.QuestAndSetting.Presenter.ISearchPresenter;
import zjl.com.oa.QuestAndSetting.Presenter.ISearchView;
import zjl.com.oa.R;
import zjl.com.oa.Response.SearchResponse;
import zjl.com.oa.Utils.TitleBarUtil;

public class SearchActivity extends BaseActivity implements ISearchView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.startdate)
    TextView startdate;
    @Bind(R.id.rl_start_date)
    RelativeLayout rlStartDate;
    @Bind(R.id.enddate)
    TextView enddate;
    @Bind(R.id.rl_end_date)
    RelativeLayout rlEndDate;
    @Bind(R.id.btn_reset)
    Button btnReset;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.ns_SearchTypes)
    NiceSpinner nsSearchTypes;
    @Bind(R.id.ns_SearchStatus)
    NiceSpinner nsSearchStatus;

    private ISearchPresenter iSearchPresenter;
    private List<String> status = new ArrayList<>();
    private List<String> type = new ArrayList<>();
    private String mSearchName,mSearchStartdate,mSearchEnddate,mSearchType,mSearchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        iSearchPresenter = new ISearchPresenterImpl(this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iSearchPresenter != null){
            iSearchPresenter.AdvanceSecInfo();
        }
    }

    private void initData(){
        mSearchName = getIntent().getStringExtra("name");
        mSearchStartdate = getIntent().getStringExtra("startdate");
        mSearchEnddate = getIntent().getStringExtra("enddate");
        mSearchType = getIntent().getStringExtra("type");
        mSearchStatus = getIntent().getStringExtra("status");

        if (mSearchName != null && mSearchName.length() > 0){
            name.setText(mSearchName);
        }

        if (mSearchStartdate != null && mSearchStartdate.length() > 0){
            startdate.setText(mSearchStartdate);
        }

        if (mSearchEnddate != null && mSearchEnddate.length() > 0){
            enddate.setText(mSearchEnddate);
        }
    }

    @OnClick({R.id.ig_back, R.id.name, R.id.startdate, R.id.rl_start_date, R.id.enddate, R.id.rl_end_date, R.id.btn_reset, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.finish();
                break;
            case R.id.name:
                break;
            case R.id.startdate:
            case R.id.rl_start_date:
                showDatePicker(startdate);
                break;
            case R.id.enddate:
            case R.id.rl_end_date:
                showDatePicker(enddate);
                break;
            case R.id.btn_reset:
                clearSearchOptions();
                break;
            case R.id.btn_search:
                if (searchOptionsValid()){
                    Intent intent = new Intent();
                    intent.putExtra("name", getSearchName());
                    intent.putExtra("startdate", getStartDate());
                    intent.putExtra("enddate", getEndDate());
                    intent.putExtra("type", getSearchType());
                    intent.putExtra("status", getSearchStatus());
                    setResult(RESULT_OK, intent);
                }
                this.finish();
                break;
        }
    }

    private boolean searchOptionsValid(){
        if ("".equals(getSearchName())  && "".equals(getStartDate())
                && "".equals(getEndDate()) && "全部".equals(getSearchType())
                && "全部".equals(getSearchStatus())){
            return false;
        }
        return true;
    }

    private void showDatePicker(TextView view) {
        DialogUIUtils.showDatePick(SearchActivity.this, Gravity.BOTTOM,
                "选择日期", System.currentTimeMillis() + 60000,
                DateSelectorWheelView.TYPE_YYYYMMDD, 0, new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {
                        view.setText(selectedDate);
                    }
                }).show();
    }

    @Override
    public String getSearchName() {
        return name.getText().toString().trim();
    }

    @Override
    public String getStartDate() {
        return startdate.getText().toString().trim();
    }

    @Override
    public String getEndDate() {
        return enddate.getText().toString().trim();
    }

    @Override
    public String getSearchType() {
        if (type != null && type.size() > 0){
            return type.get(nsSearchTypes.getSelectedIndex());
        }

        return null;
    }

    @Override
    public String getSearchStatus() {
        if (status != null && status.size() > 0){
            return status.get(nsSearchStatus.getSelectedIndex());
        }

        return null;
    }

    @Override
    public void clearSearchOptions() {
        name.setText("");
        startdate.setText("");
        enddate.setText("");
        nsSearchTypes.setSelectedIndex(0);
        nsSearchStatus.setSelectedIndex(0);
    }

    @Override
    public void loadTypeStatus(SearchResponse.Result result) {

        if (result != null){

            type.clear();
            type = result.getProc_type_list();
            if (type != null && type.size() > 0){
                nsSearchTypes.attachDataSource(type);
            }

            status.clear();
            status = result.getStatus_type_list();
            if (status != null && status.size() > 0){
                nsSearchStatus.attachDataSource(status);
            }
        }

        /**
         * 再次进入高级搜索的时候重新加载之前输入的数据*/

        if (mSearchType != null && mSearchType.length() > 0){
            nsSearchTypes.setSelectedIndex(type.indexOf(mSearchType));
        }

        if (mSearchStatus != null && mSearchStatus.length() > 0){
            nsSearchStatus.setSelectedIndex(status.indexOf(mSearchStatus));
        }
    }

    @Override
    public void showLoading() {
        if (submitDialog != null){
            submitDialog.msg = "加载中";
            submitDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (submitDialog != null){
            DialogUIUtils.dismiss(submitDialog);
        }
    }
}
