package zjl.com.oa.Sign.View;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIDateTimeSaveListener;
import com.dou361.dialogui.widget.DateSelectorWheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.SigningAdapter;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.CustomView.MyListView;
import zjl.com.oa.R;
import zjl.com.oa.Response.GetSigningResponse;
import zjl.com.oa.Sign.Model.InformSignPresenterImpl;
import zjl.com.oa.Sign.Presenter.IInformSignPresenter;
import zjl.com.oa.Sign.Presenter.IInformSignView;
import zjl.com.oa.Utils.ListViewHeight;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class InformSignActivity extends BaseActivity implements IInformSignView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.listview)
    MyListView listview;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.etBakInfo)
    EditText etBakInfo;
    @Bind(R.id.Btnnext)
    Button Btnnext;
    @Bind(R.id.title)
    TextView title;

    private String token,workflow_content_id,wk_point_id;
    private List<GetSigningResponse.Result.Data> datalist = new ArrayList<>();
    private SigningAdapter adapter;
    private IInformSignPresenter informSignPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_signing);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        title.setText(context.getString(R.string.informsign));

        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");

        if ( wk_point_id != null && wk_point_id.equals("13")){
            String autoUpdate = UserInfo.getInstance(context).getUserInfo(UserInfo.AUTOUPDATE);
            if (autoUpdate != null && autoUpdate.equals("1")){
                saveOperation(wk_point_id);
            }
        }

        adapter = new SigningAdapter(listview,datalist,context);
        adapter.setOnItemClickListener(new SigningAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                hideSoftInput();
                showDataPicker();
            }
        });
        listview.setAdapter(adapter);
        ListViewHeight.setListViewHeightBasedOnChildren(listview);
        submitDialog.msg = "加载中";
        informSignPresenter = new InformSignPresenterImpl(this,adapter);

        if (!netConnected){
            showNetError();
        }else{
            informSignPresenter.getInformSign(token,workflow_content_id);
        }

        etBakInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                evaluationBeizhuWordsLeft.setText(Integer.toString(s.length()).concat(Constant.WordsLeft));
            }
        });
    }

    @OnClick({R.id.ig_back, R.id.etBakInfo, R.id.Btnnext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.etBakInfo:
                break;
            case R.id.Btnnext:
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.Btnnext)){
                    return;
                }else{
                    submitDialog.msg = "提交中";
                    informSignPresenter.postInformSign(token,workflow_content_id,wk_point_id,getServiceFee(),getPontage(),getContractDate(),getBakInfo());
                }
                break;
        }
    }

    @Override
    public String getBakInfo() {
        return etBakInfo.getText().toString().trim();
    }

    @Override
    public String getServiceFee() {
        return informSignPresenter.getServiceFee();
    }

    @Override
    public String getPontage() {
        return informSignPresenter.getPontage();
    }

    @Override
    public String getContractDate() {
        return informSignPresenter.getContractDate();
    }

    @Override
    public void refreshData(List<GetSigningResponse.Result.Data> data) {

        datalist.clear();
        if (data.size() > 0){
            datalist.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        if (submitDialog != null){
            submitDialog.show();
        }
    }

    @Override
    public void hideProgressBar() {
        if (submitDialog != null){
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    private void showDataPicker(){

        DialogUIUtils.showDatePick(InformSignActivity.this, Gravity.BOTTOM, "签约日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDD, 0, new DialogUIDateTimeSaveListener() {
            @Override
            public void onSaveSelectedDate(int tag, String selectedDate) {
                datalist.get(datalist.size() -1 ).setL_value(selectedDate);
                adapter.notifyDataSetChanged();
            }
        }).show();
    }

    private void hideSoftInput(){

        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
}
