package zjl.com.oa2.Meeting.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.luck.picture.lib.entity.LocalMedia;

import org.angmarch.views.NiceSpinner;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Base.PhotosActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.Meeting.Model.MettingPresenterImpl;
import zjl.com.oa2.Meeting.Presenter.IEnterOrderView;
import zjl.com.oa2.Meeting.Presenter.IMettingPresenter;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class EnteringOrderActivity extends PhotosActivity implements IEnterOrderView{
    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.time_icon)
    ImageView timeIcon;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_manager)
    TextView tvManager;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.identification)
    TextView identification;
    @Bind(R.id.et_identification)
    EditText etIdentification;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.bankcode)
    TextView bankcode;
    @Bind(R.id.et_bankcode)
    EditText etBankcode;
    @Bind(R.id.bankcard)
    TextView bankcard;
    @Bind(R.id.et_bankcard)
    EditText etBankcard;
    @Bind(R.id.loanrate)
    TextView loanrate;
    @Bind(R.id.et_loanrate)
    EditText etLoanrate;
    @Bind(R.id.loandest)
    TextView loandest;
    @Bind(R.id.et_loandest)
    EditText etLoandest;
    @Bind(R.id.loanduration)
    TextView loanduration;
    @Bind(R.id.et_loanduration)
    EditText etLoanduration;
    @Bind(R.id.paybackway)
    TextView paybackway;
    @Bind(R.id.et_paybackway)
    NiceSpinner etPaybackway;
    @Bind(R.id.carnumber)
    TextView carnumber;
    @Bind(R.id.et_carnumber)
    EditText etCarnumber;
    @Bind(R.id.carregisternumber)
    TextView carregisternumber;
    @Bind(R.id.et_carregisternumber)
    EditText etCarregisternumber;
    @Bind(R.id.carenginenumber)
    TextView carenginenumber;
    @Bind(R.id.et_carenginenumber)
    EditText etCarenginenumber;
    @Bind(R.id.carframenumber)
    TextView carframenumber;
    @Bind(R.id.et_carframenumber)
    EditText etCarframenumber;
    @Bind(R.id.rvCarPhoto)
    RecyclerView rvCarPhoto;
    @Bind(R.id.rlCarPhoto)
    RelativeLayout rlCarPhoto;
    @Bind(R.id.llCarPhoto)
    LinearLayout llCarPhoto;
    @Bind(R.id.next)
    Button next;
    @Bind(R.id.title)
    TextView title;
    private String token,date, projectManager,workflow_content_id,wk_point_id,remark,mortgagetype,workflow_name;
    private List<LocalMedia> photos;
    private IMettingPresenter mettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entering_order);
        ButterKnife.bind(this);
        /**
         * TitleBar 设置格式*/
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        etPaybackway.attachDataSource(Constant.LoanPayBackWay);

        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
        date = getIntent().getStringExtra("date");
        projectManager = getIntent().getStringExtra("manager");

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        photos = getIntent().getParcelableArrayListExtra("photos");
        remark = getIntent().getStringExtra("remark");
        mortgagetype = getIntent().getStringExtra("mortgagetype");

        workflow_name = getIntent().getStringExtra("workflow_name");
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        if (workflow_name != null && workflow_name.length() > 0) {
            title.setText(workflow_name);
        }

        mettingPresenter = new MettingPresenterImpl(this);

        initView();
    }

    private void initView(){
        if (date != null && date.length() > 0){
            tvTime.setText(date.trim());
        }
        if (projectManager != null && projectManager.length() > 0){
            tvManager.setText(projectManager.trim());
        }

        if ("2".equals(wk_point_id)){
            this.setRecycleViewAdapter(rvCarPhoto,true);
            rlCarPhoto.setVisibility(View.VISIBLE);
            llCarPhoto.setVisibility(View.VISIBLE);
        }else if ("30".equals(wk_point_id)){
            rlCarPhoto.setVisibility(View.GONE);
            llCarPhoto.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ig_back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.finish();
                break;
            case R.id.next:
                if (!netConnected){
                    showNetError();
                    return;
                }
                if (isFastDoubleClick(R.id.next)){
                    return;
                }
                //面谈节点上传信息
                if ("2".equals(wk_point_id)){
                    mettingPresenter.Interview(
                            token,Integer.parseInt(workflow_content_id), Integer.parseInt(wk_point_id),
                            getSelectList(),getRemark(),
                            getCustomerName(),getID(),getPhoneNumber(),getAddress(),
                            getBankCode(),getBankCard(),
                            getLoanDest(),
                            getCarNumber(), getCarRegNumber(), getCarEngineNumber(), getCarFrameNumber());

                }else if ("30".equals(wk_point_id)){//录入订单节点上传信息

                    mettingPresenter.InputInfo(
                            token, Integer.parseInt(workflow_content_id), Integer.parseInt(wk_point_id),
                            getCustomerName(), getID(), getPhoneNumber(), getAddress(), getBankCode(), getBankCard(),

                            getLoanDest(),
//                            getLoanRate(), getLoanDest(), getLoanDuration(), getPayBackWay(),
                            getCarNumber(), getCarRegNumber(), getCarEngineNumber(), getCarFrameNumber(),getRemark());

                }
                break;
        }
    }

    @Override
    public String getCustomerName() {
        return etName.getText().toString().trim();
    }

    @Override
    public String getID() {
        return etIdentification.getText().toString().trim();
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public String getAddress() {
        return etAddress.getText().toString().trim();
    }

    @Override
    public String getBankCode() {
        return etBankcode.getText().toString().trim();
    }

    @Override
    public String getBankCard() {
        return etBankcard.getText().toString().trim();
    }

    @Override
    public double getLoanRate() {
        String rate = etLoanrate.getText().toString().trim();
        if ("".equals(rate)){
            return 0.00;
        }
        return Double.parseDouble(rate);
    }

    @Override
    public String getLoanDest() {
        return etLoandest.getText().toString().trim();
    }

    @Override
    public int getLoanDuration() {
        String duration = etLoanduration.getText().toString().trim();
        if ("".equals(duration)){
            return 0;
        }
        return Integer.parseInt(duration);
    }

    @Override
    public String getPayBackWay() {
        return Constant.LoanPayBackWay.get(etPaybackway.getSelectedIndex());
    }

    @Override
    public String getCarNumber() {
        return etCarnumber.getText().toString().trim();
    }

    @Override
    public String getCarRegNumber() {
        return etCarregisternumber.getText().toString().trim();
    }

    @Override
    public String getCarEngineNumber() {
        return etCarenginenumber.getText().toString().trim();
    }

    @Override
    public String getCarFrameNumber() {
        return etCarframenumber.getText().toString().trim();
    }

    public String getRemark(){
        if (remark != null && remark.length() > 0){
            return remark;
        }

        return  "";
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

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressBar();
    }
}
