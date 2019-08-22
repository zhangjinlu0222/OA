package zjl.com.oa.Meeting.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.luck.picture.lib.entity.LocalMedia;

import org.angmarch.views.NiceSpinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.PhotosActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.Meeting.Model.MettingPresenterImpl;
import zjl.com.oa.Meeting.Presenter.IMettingPresenter;
import zjl.com.oa.Meeting.Presenter.IMettingView;
import zjl.com.oa.R;
import zjl.com.oa.Response.LookInterviewResponse;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class MettingActivity extends PhotosActivity implements IMettingView {

    @Bind(R.id.metting_tv_uploadMeetingPhotos)
    TextView mettingTvUploadMeetingPhotos;
    @Bind(R.id.metting_tv_upload_hint)
    TextView mettingTvUploadHint;
    @Bind(R.id.space)
    View space;
    @Bind(R.id.metting_et_upload_hint)
    EditText mettingEtUploadHint;
    @Bind(R.id.metting_btn_refuse)
    Button mettingBtnRefuse;
    @Bind(R.id.metting_btn_next)
    Button mettingBtnNext;
    @Bind(R.id.ig_back)
    ImageView back;
    @Bind(R.id.photos)
    RecyclerView photos;

    private int index = -1;
    private String token, workflow_content_id, wk_point_id;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String date, project_manager;
    private IMettingPresenter mettingPresenter;
    private String type;
    private BuildBean refuseDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metting);
        ButterKnife.bind(this);
        /**
         * TitleBar 设置格式*/
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        //绑定数据在取到数据后绑定
//        mettingNsMortgage.attachDataSource(Constant.MettingMortgageType);

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
//        type = getIntent().getStringExtra("type");
//        initType();
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        date = getIntent().getStringExtra("date");
        project_manager = getIntent().getStringExtra("manager");

        mettingPresenter = new MettingPresenterImpl(this);

        this.setRecycleViewAdapter(photos,true);
    }

    @OnClick({R.id.ig_back, R.id.metting_et_upload_hint, R.id.metting_btn_refuse, R.id.metting_btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.metting_btn_refuse:
                if (!netConnected) {
                    showNetError();
                } else if (isFastDoubleClick(R.id.metting_btn_refuse)) {
                    return;
                } else {
                    showAlertDialog();
                }
                break;
            case R.id.metting_btn_next:
                goEnterOrder();
                break;
        }
    }

    //跳转到订单录入
    private void goEnterOrder() {
        Intent intent = new Intent();
        intent.setClass(this, EnteringOrderActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("manager", project_manager);
        intent.putExtra("workflow_content_id", workflow_content_id);
        intent.putExtra("wk_point_id", wk_point_id);
        intent.putExtra("photos",(Serializable) getSelectList());
        intent.putExtra("remark", getBakInfo());
        startActivityForResult(intent, 202);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 202:
                    this.finish();
                    break;
            }
        }
    }

    @Override
    public String getBakInfo() {
        return mettingEtUploadHint.getText().toString().trim();
    }

    @Override
    public void refreshData(LookInterviewResponse.Result result) {
        if (result == null) {
            this.showFailureMsg("网络异常");
            return;
        } else {
            if (result.getPoint_fee() != null && result.getPoint_fee().length() > 0) {
                mettingEtUploadHint.setHint(result.getPoint_fee());
            }
        }
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

    public void showAlertDialog() {

        View rootView = View.inflate(MettingActivity.this, R.layout.alertdialog, null);
        refuseDialog = DialogUIUtils.showCustomAlert(MettingActivity.this, rootView);

        TextView title = rootView.findViewById(R.id.title);
        title.setText("确认拒件");

        EditText et_reason = rootView.findViewById(R.id.et_feedback);
        et_reason.setHint("拒件原因");

        TextView cancel = rootView.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(refuseDialog);
            }
        });

        TextView confirm = rootView.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(refuseDialog);
                if (!netConnected) {
                    showNetError();
                } else if (et_reason.getText().toString().trim().length() <= 0) {
                    showFailureMsg("请输入拒件原因");
                } else {
                    submitDialog.msg = "提交中";
                    mettingPresenter.endWorkFlow(token, Integer.parseInt(workflow_content_id), Integer.parseInt(wk_point_id),
                            et_reason.getText().toString().trim());
                }
            }
        });

        refuseDialog.show();
    }
}
