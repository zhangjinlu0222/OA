package zjl.com.oa2.QuestAndSetting.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.TitleBarUtil;

public class SearchLoanInfoActivity extends AppCompatActivity {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.tv_date)
    TextView tvDate;
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

    private String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_loan_info);
        ButterKnife.bind(this);

        flag = getIntent().getStringExtra("flag");

        initView();

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
    }

    private void initView(){
        if (flag != null && flag.equals("2")){
            tvDate.setVisibility(View.GONE);
            rlStartDate.setVisibility(View.GONE);
            rlEndDate.setVisibility(View.GONE);
        }else{
            tvDate.setVisibility(View.VISIBLE);
            rlStartDate.setVisibility(View.VISIBLE);
            rlEndDate.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ig_back, R.id.rl_start_date, R.id.rl_end_date, R.id.btn_reset, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                finish();
                break;
            case R.id.rl_start_date:
                showDatePicker(startdate);
                break;
            case R.id.rl_end_date:
                showDatePicker(enddate);
                break;
            case R.id.btn_reset:
                clearSearchInfos();
                break;
            case R.id.btn_search:
                if (searchOptionsValid()){
                    Intent intent = new Intent();
                    intent.putExtra("name", getSearchName());
                    intent.putExtra("startdate", getStartDate());
                    intent.putExtra("enddate", getEndDate());
                    setResult(RESULT_OK, intent);
                }
                this.finish();
                break;
        }
    }

    private boolean searchOptionsValid(){
        if ("".equals(getSearchName())  && "".equals(getStartDate())
                && "".equals(getEndDate())){
            return false;
        }
        return true;
    }

    public String getSearchName() {
        return name.getText().toString().trim();
    }

    public String getStartDate() {
        return startdate.getText().toString().trim();
    }

    public String getEndDate() {
        return enddate.getText().toString().trim();
    }

    private void clearSearchInfos(){
        name.setText("");
        startdate.setText("");
        enddate.setText("");
    }

    private void showDatePicker(TextView view){
        DialogUIUtils.showDatePick(SearchLoanInfoActivity.this, Gravity.BOTTOM,
                "选择日期", System.currentTimeMillis() + 60000,
                DateSelectorWheelView.TYPE_YYYYMMDD, 0, new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {
                        view.setText(selectedDate);
                    }
                }).show();
    }
}
