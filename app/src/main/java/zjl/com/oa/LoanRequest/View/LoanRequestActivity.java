package zjl.com.oa.LoanRequest.View;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.LoanRequestAdapter;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.CustomView.MyExpandableListview;
import zjl.com.oa.LoanRequest.Model.LoanRequestPresenterImpl;
import zjl.com.oa.LoanRequest.Presenter.ILoanRequestPresenter;
import zjl.com.oa.LoanRequest.Presenter.ILoanRequestView;
import zjl.com.oa.R;
import zjl.com.oa.Response.GetLoanRequestResponse;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class LoanRequestActivity extends BaseActivity implements ILoanRequestView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.listview)
    MyExpandableListview listview;
    @Bind(R.id.beizhu_words_left)
    TextView beizhuWordsLeft;
    @Bind(R.id.et_beizhu)
    EditText etBeizhu;
    @Bind(R.id.next)
    Button next;

    private LoanRequestAdapter loanRequestAdapter;
    private ILoanRequestPresenter loanRequestPresenter;
    private List<List<GetLoanRequestResponse.Result.Section.dict>> childData = new ArrayList<>();
    private List<String> groupData = new ArrayList<>();

    private String token, workflow_content_id, wk_point_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform_finance);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        if (wk_point_id != null && wk_point_id.equals("20")){
            String autoUpdate = UserInfo.getInstance(context).getUserInfo(UserInfo.AUTOUPDATE);
            if (autoUpdate != null && autoUpdate.equals("1")){
                saveOperation(wk_point_id);
            }
        }

        loanRequestPresenter = new LoanRequestPresenterImpl(this);

        if (!netConnected){
            showNetError();
        }else{
            submitDialog.msg = "加载中";
            loanRequestPresenter.lookLoanApplication(token, workflow_content_id);
        }

        loanRequestAdapter = new LoanRequestAdapter(context,groupData,childData,listview);
        listview.setAdapter(loanRequestAdapter);
        etBeizhu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                beizhuWordsLeft.setText(s.length() + Constant.WordsLeft);
            }
        });
    }

    @OnClick({R.id.ig_back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.next:
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.next)){
                    return;
                }else{
                    submitDialog.msg = "提交中";
                    loanRequestPresenter.loanApplication(token,workflow_content_id,wk_point_id,getBakInfo());
                }
                break;
        }
    }

    @Override
    public void refreshData(List<GetLoanRequestResponse.Result.Section> data) {

        if (data != null && data.size() > 0){
            groupData.clear();
            childData.clear();

            for (GetLoanRequestResponse.Result.Section section:data){
                groupData.add(section.getTitle());
                childData.add(section.getDict_list());
            }
            loanRequestAdapter.notifyDataSetChanged();

            for (int i= 0 ; i < loanRequestAdapter.getGroupCount();i++){
                listview.expandGroup(i);
            }
        }
    }

    @Override
    public String getBakInfo() {
        return etBeizhu.getText().toString().trim();
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
}
