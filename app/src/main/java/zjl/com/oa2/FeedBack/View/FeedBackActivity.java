package zjl.com.oa2.FeedBack.View;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.FeedBack.Model.FeedBackPresenterImpl;
import zjl.com.oa2.FeedBack.Presenter.IFeedBackPresenter;
import zjl.com.oa2.FeedBack.Presenter.IFeedBackView;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class FeedBackActivity extends BaseActivity implements IFeedBackView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.feedback_tv_beizhu)
    TextView feedbackTvBeizhu;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.evaluation_et_beizhu)
    EditText evaluationEtBeizhu;
    @Bind(R.id.next)
    Button next;

    private String token,workflow_content_id,wk_point_id,workflow_name;
    private IFeedBackPresenter feedBackPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        workflow_name = getIntent().getStringExtra("workflow_name");
        evaluationEtBeizhu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                evaluationBeizhuWordsLeft.setText(s.length() + "/100字");
            }
        });

        feedBackPresenter = new FeedBackPresenterImpl(this);
        initView(workflow_name);
        initLoadData(workflow_name);
    }
    private void initView(String name){
        if ("初步反馈结果".equals(name)){
            tvTitle.setText(name);
        }
    }
    private void initLoadData(String name){

        if (!netConnected){
            showNetError();
            return;
        }

        submitDialog.msg = "加载中";
        if ("初步反馈结果".equals(name)){
            feedBackPresenter.LookFirstFeedbackResult(token,workflow_content_id);
        }else {
            feedBackPresenter.LookFeedbackResult(token,workflow_content_id);
        }
    }
    private void submitMsg(String name){

        if (!netConnected){
            showNetError();
            return;
        }

        submitDialog.msg = "提交中";
        if ("初步反馈结果".equals(name)){
            feedBackPresenter.FirstFeedbackResult(token,workflow_content_id,wk_point_id,getBakInfo());
        }else {
            feedBackPresenter.FeedbackResult(token,workflow_content_id,wk_point_id,getBakInfo());
        }
    }

    @Override
    public String getBakInfo() {
        return evaluationEtBeizhu.getText().toString().trim();
    }

    @Override
    public void refreshData(String arg) {
        if (!"".equals(arg)){
            feedbackTvBeizhu.setText(arg);
        }
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

    @OnClick({R.id.ig_back, R.id.feedback_tv_beizhu, R.id.evaluation_et_beizhu, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.feedback_tv_beizhu:
                break;
            case R.id.evaluation_et_beizhu:
                break;
            case R.id.next:
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.next)){
                    return;
                }else{
                    submitMsg(workflow_name);
                }
                break;
        }
    }
}
