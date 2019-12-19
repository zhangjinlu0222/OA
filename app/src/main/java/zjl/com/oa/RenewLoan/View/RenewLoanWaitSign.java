package zjl.com.oa.RenewLoan.View;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.R;
import zjl.com.oa.RenewLoan.Model.RLPresenterImpl;
import zjl.com.oa.RenewLoan.Presenter.IRLPresenter;
import zjl.com.oa.RenewLoan.Presenter.IRLView;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Utils.TitleBarUtil;

public class RenewLoanWaitSign extends BaseActivity implements IRLView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.waitsignimg)
    ImageView waitsignimg;
    @Bind(R.id.next)
    Button next;

    private IRLPresenter iRLPresenter;
    private int workflow_content_id;
    private int wk_point_id;
    private String workflow_name;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_loan_wait_sign);
        ButterKnife.bind(this);

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));


        iRLPresenter = new RLPresenterImpl(this);
        workflow_content_id = Integer.parseInt(getIntent().getStringExtra("workflow_content_id"));
        wk_point_id = Integer.parseInt(getIntent().getStringExtra("wk_point_id"));
        workflow_name = getIntent().getStringExtra("workflow_name");
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        if (workflow_name != null && workflow_name.length() > 0) {
            title.setText(workflow_name);
        }
    }

    @OnClick({R.id.ig_back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.finish();
                break;
            case R.id.next:
                submitMsg();
                break;
        }
    }

    private void submitMsg() {
        if (iRLPresenter != null){
            //调用上传图片的借口，备注写空，type_id是11，文件传空
            HashMap<String ,Object> map = new HashMap();
            map.put("token",token );
            map.put("w_con_id",workflow_content_id );
            map.put("w_pot_id",wk_point_id );

            iRLPresenter.UploadCarPhoto(request_start_flag,UPLOAD_TYPE_NORMAL,map,"11",new ArrayList<>());
        }
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
    public void loadForms(FormResponse.Result result) {

    }

    @Override
    public String getData_Con(String arg) {
        return null;
    }
}
