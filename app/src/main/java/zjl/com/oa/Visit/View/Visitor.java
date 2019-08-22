package zjl.com.oa.Visit.View;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.R;
import zjl.com.oa.Response.LookInterviewResponse;
import zjl.com.oa.Utils.TitleBarUtil;
import zjl.com.oa.Visit.Model.NewVisitorPresenterImpl;
import zjl.com.oa.Visit.Presenter.INewVisitorPresenter;
import zjl.com.oa.Visit.Presenter.INewVisitorView;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class Visitor extends BaseActivity implements INewVisitorView {
    @Bind(R.id.visitor_tv_customer_name)
    TextView visitorTvCustomerName;
    @Bind(R.id.visitor_et_customer_name)
    EditText visitorEtCustomerName;
    @Bind(R.id.visitor_tv_customer_original)
    TextView visitorTvCustomerOriginal;
    @Bind(R.id.visitor_et_customer_original)
    NiceSpinner visitorEtCustomerOriginal;
    @Bind(R.id.visitor_tv_point_notice)
    TextView visitorTvPointNotice;
    @Bind(R.id.visitor_et_point_notice)
    EditText visitorEtPointNotice;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.bakInfo)
    EditText bakInfo;
    @Bind(R.id.ig_back)
    ImageView ig_back;
    @Bind(R.id.visitor_tv_project_type)
    TextView visitorTvProjectType;
    @Bind(R.id.visitor_ns_project_type)
    NiceSpinner visitorNsProjectType;
    private INewVisitorPresenter newVisitorPresenter;
    private String token;
    private String w_con_id, w_pot_id;

    private List<String> source = new LinkedList<>();
    private List<String> type = new LinkedList<>();

    private List<Integer> source_id = new LinkedList<>();
    private List<Integer> type_id = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        w_con_id = getIntent().getStringExtra("workflow_content_id");
        w_pot_id = getIntent().getStringExtra("wk_point_id");

        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
        newVisitorPresenter = new NewVisitorPresenterImpl(this);

        submitDialog.msg = "加载中";
        newVisitorPresenter.LookInterview(token, w_con_id);

        bakInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                evaluationBeizhuWordsLeft.setText(length + "/100字");
            }
        });

    }

    @Override
    public String getCustomerName() {
        return visitorEtCustomerName.getText().toString().trim();
    }

    @Override
    public int getCustormerSource() {
        if ((source != null && source.size() > 0)
            && (source_id != null && source_id.size() >0)){
            int sourceId = source_id.get(visitorEtCustomerOriginal.getSelectedIndex());
            if (sourceId > 0){
                return sourceId;
            }else{
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getProductType(){

        if ((type != null && type.size() > 0)
                && (type_id != null && type_id.size() >0)){
            int typeId = type_id.get(visitorNsProjectType.getSelectedIndex());
            if (typeId > 0){
                return typeId;
            }else{
                return 0;
            }
        }
        return 0;
    }

    @Override
    public String getPointNotice() {
        return visitorEtPointNotice.getText().toString().trim();
    }

    @Override
    public String getBakInfo() {
        return bakInfo.getText().toString().trim();
    }

    @Override
    public void showProgressBar() {
        if (submitDialog != null) {
            submitDialog.show();
        }
    }

    @Override
    public void hideProgressBar() {

        if (submitDialog != null) {
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    @Override
    public void refreshData(LookInterviewResponse.Result result) {
        if (result != null) {
            if (result.getSource_from() != null && result.getSource_from().size() > 0) {
                source.clear();
                source.addAll(result.getSource_from());
                visitorEtCustomerOriginal.attachDataSource(result.getSource_from());
            }

            if (result.getProc_type() != null && result.getProc_type().size() > 0){
                type.clear();
                type.addAll(result.getProc_type());
                visitorNsProjectType.attachDataSource(result.getProc_type());
            }

            if (result.getSource_from_new() != null && result.getSource_from_new().size() > 0) {
                source_id.clear();
                source_id.addAll(result.getSource_from_new());
            }

            if (result.getProc_type_new() != null && result.getProc_type_new().size() > 0){
                type_id.clear();
                type_id.addAll(result.getProc_type_new());
            }
        }
    }

    @Override
    public void toMainActivity() {
        this.finish();
    }

    @Override
    public void showFailureMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newVisitorPresenter.onDestoryView();
    }

    @OnClick({R.id.ig_back, R.id.visitor_tv_customer_name, R.id.visitor_et_customer_name, R.id.visitor_tv_customer_original, R.id.visitor_et_customer_original, R.id.visitor_tv_point_notice, R.id.visitor_et_point_notice, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.button:

                if (!netConnected) {
                    showNetError();
                    return;
                }

                if (isFastDoubleClick(R.id.button)) {
                    return;
                } else {
                    submitDialog.msg = "提交中";
                    newVisitorPresenter.newVisitor(token, getCustomerName(), getCustormerSource(),getProductType(), getPointNotice(), getBakInfo(), getContentId(), getPointId());
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressBar();
    }

    public String getContentId() {
        return w_con_id;
    }

    public String getPointId() {
        return w_pot_id;
    }
}
