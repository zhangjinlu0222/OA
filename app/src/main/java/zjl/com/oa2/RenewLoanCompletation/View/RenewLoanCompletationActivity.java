package zjl.com.oa2.RenewLoanCompletation.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.R;
import zjl.com.oa2.RenewLoanCompletation.Model.PresenterImpl;
import zjl.com.oa2.RenewLoanCompletation.Presenter.IView;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class RenewLoanCompletationActivity extends BaseActivity implements IView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.btnRenewLoanFinish)
    Button btnRenewLoanFinish;
    @Bind(R.id.title)
    TextView title;
    private PresenterImpl modelImp;
    private String w_con_id;
    private String w_pot_id;
    private String token;
    private String w_con_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_loan_completation);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        modelImp = new PresenterImpl(this);
        w_con_id = getIntent().getStringExtra("workflow_content_id");
        w_pot_id = getIntent().getStringExtra("wk_point_id");
        w_con_name = getIntent().getStringExtra("workflow_name");
        token = UserInfo.getInstance(this).getUserInfo(UserInfo.TOKEN);
        title.setText(w_con_name);
    }

    @OnClick({R.id.ig_back, R.id.btnRenewLoanFinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.btnRenewLoanFinish:
                if (!isFastDoubleClick(R.id.btnRenewLoanFinish)) {
                    modelImp.RefinanceFinishFlow(token, w_con_id, w_pot_id);
                }
                break;
        }
    }

    @Override
    public void showProgress() {
        if (submitDialog != null) {
            submitDialog.show();
        }
    }

    @Override
    public void hideProgress() {

        if (submitDialog != null) {
            DialogUIUtils.dismiss(submitDialog);
        }
    }
}
