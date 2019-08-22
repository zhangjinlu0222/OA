package zjl.com.oa.BusinessFeedBack.View;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.GridImageAdapter;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Base.FullyGridLayoutManager;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.BusinessFeedBack.Model.BusFeedBackPresenterImpl;
import zjl.com.oa.BusinessFeedBack.Presenter.IBusFeedBackPresenter;
import zjl.com.oa.BusinessFeedBack.Presenter.IBusFeedBackView;
import zjl.com.oa.R;
import zjl.com.oa.Response.BusFirstFeedBackResponse;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class BusinessFeedBackActivity extends BaseActivity implements IBusFeedBackView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.tv_evaluation_deposit)
    TextView tvEvaluationDeposit;
    @Bind(R.id.evaluation_et_car_age)
    TextView evaluationEtCarAge;
    @Bind(R.id.tv_violation_deposit)
    TextView tvViolationDeposit;
    @Bind(R.id.evaluation_et_car_type)
    TextView evaluationEtCarType;
    @Bind(R.id.tv_insurance_deposit)
    TextView tvInsuranceDeposit;
    @Bind(R.id.evaluation_et_car_model)
    TextView evaluationEtCarModel;
    @Bind(R.id.tv_beizhu)
    TextView tvBeizhu;
    @Bind(R.id.tv_beizhu_content)
    TextView tvBeizhuContent;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.evaluationReport)
    RecyclerView reports;
    @Bind(R.id.carphotos)
    RecyclerView cars;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.evaluation_et_beizhu)
    TextView evaluationEtBeizhu;
    @Bind(R.id.evaluationBtnRefuse)
    Button evaluationBtnRefuse;
    @Bind(R.id.evaluationBtnNext)
    Button evaluationBtnNext;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.part)
    TextView part;
    @Bind(R.id.tvloanrate)
    TextView tvloanrate;
    @Bind(R.id.et_loanrate)
    EditText etLoanrate;
    @Bind(R.id.unit)
    TextView unit;
    @Bind(R.id.rlloanrate)
    RelativeLayout rlloanrate;
    @Bind(R.id.tvloanduration)
    TextView tvloanduration;
    @Bind(R.id.et_loanduration)
    EditText etLoanduration;
    @Bind(R.id.durationunit)
    TextView durationunit;
    @Bind(R.id.rlloanduration)
    RelativeLayout rlloanduration;
    @Bind(R.id.paybackway)
    TextView paybackway;
    @Bind(R.id.ns_paybackway)
    NiceSpinner nsPaybackway;
    @Bind(R.id.rlloanpaybackway)
    RelativeLayout rlloanpaybackway;
    private List<LocalMedia> reportPhotos = new ArrayList<>();
    private List<LocalMedia> carPhotos = new ArrayList<>();
    private GridImageAdapter carsAdapter;
    private GridImageAdapter reportsAdapter;

    private String token,type;
    private String workflow_content_id, wk_point_id, workflow_name;
    private String car_break_rules, insurance, remark, car_assess_amount, sure_amount_remark, first_assess_amount_remark;

    //业务反馈显示贷款期限
    private String loan_length;

    private static final int DEFAULT_MAX_INTEGER_LENGTH = 10;
    private static final int DEFAULT_DECIMAL_NUMBER = 2;
    private static final InputFilter[] INPUT_FILTER_ARRAY = new InputFilter[1];
    /**
     * 保留小数点后多少位
     */
    private int mDecimalNumber = DEFAULT_DECIMAL_NUMBER;
    /**
     * 允许最大的整数多少位
     */
    private int mMaxIntegralLength = DEFAULT_MAX_INTEGER_LENGTH;
    private IBusFeedBackPresenter busFeedBackPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_feed_back);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        workflow_name = getIntent().getStringExtra("workflow_name");
        type = getIntent().getStringExtra("type");

        busFeedBackPresenter = new BusFeedBackPresenterImpl(this);
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        initRecycleView();
        initView(workflow_name);
        initLoadData();
    }

    private void initLoadData() {
        if (!netConnected) {
            showNetError();
            return;
        }
        submitDialog.msg = "加载中";
        busFeedBackPresenter.LookFirstSureAmount(token, workflow_content_id);
    }

    private void initView(String name) {
        if (name != null && name.length() > 0) {
            title.setText(name);
        }

        if (name != null && name.length() > 0 && "初步反馈".equals(name)) {
            rlloanrate.setVisibility(View.GONE);
            rlloanduration.setVisibility(View.GONE);
            rlloanpaybackway.setVisibility(View.GONE);
        } else if (name != null && name.length() > 0 && "业务反馈".equals(name)) {
            rlloanrate.setVisibility(View.VISIBLE);
            rlloanduration.setVisibility(View.VISIBLE);
            rlloanpaybackway.setVisibility(View.VISIBLE);
            nsPaybackway.attachDataSource(Constant.LoanPayBackWay);
            etLoanrate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.length() > 0) {
                        String inputContent = s.toString();
                        if (inputContent.contains(".")) {
                            int maxLength = inputContent.indexOf(".") + mDecimalNumber + 1;
                            INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(maxLength);
                        } else {
                            INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(mMaxIntegralLength);
                        }
                        etLoanrate.setFilters(INPUT_FILTER_ARRAY);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        if ("bohui".equals(type)){
            evaluationBtnRefuse.setEnabled(false);
            evaluationBtnRefuse.setBackgroundColor(Color.LTGRAY);
        }else if ("".equals(type)){
            evaluationBtnRefuse.setEnabled(true);
            evaluationBtnRefuse.setBackgroundResource(R.mipmap.info_bg_next);
        }
    }

    private void submitMsg(String name) {
        submitDialog.msg = "提交中";
        if ("初步反馈".equals(name)) {
            busFeedBackPresenter.BusFirstFeedback(token, workflow_content_id, wk_point_id, "");
        } else {
            busFeedBackPresenter.BusFeedback(token, workflow_content_id, wk_point_id,
                    getLoanLength(),getLoanRate(),getLoanReturnMethod(), "");
        }
    }

    private void feedback(String name, String remark) {
        submitDialog.msg = "提交中";
        if ("初步反馈".equals(name)) {
            busFeedBackPresenter.BusFirstFeedback(token, workflow_content_id, wk_point_id, remark);
        } else {
            busFeedBackPresenter.BusFeedback(token, workflow_content_id, wk_point_id,
                    getLoanLength(),getLoanRate(),getLoanReturnMethod(), remark);
        }
    }

    private void initRecycleView() {
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        reports.setLayoutManager(manager2);
        reportsAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
            }
        }, reportPhotos);
        reportsAdapter.setShowDel(false);
        reportsAdapter.setSelectMax(reportPhotos.size());
        reports.setAdapter(reportsAdapter);
        reportsAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (reportPhotos.size() > 0) {
                    LocalMedia media = reportPhotos.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(BusinessFeedBackActivity.this).externalPicturePreview(position, reportPhotos);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(BusinessFeedBackActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(BusinessFeedBackActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        cars.setLayoutManager(manager);
        carsAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
            }
        }, carPhotos);
        carsAdapter.setShowDel(false);
        carsAdapter.setSelectMax(carPhotos.size());
        cars.setAdapter(carsAdapter);
        carsAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (carPhotos.size() > 0) {
                    LocalMedia media = carPhotos.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(BusinessFeedBackActivity.this).externalPicturePreview(position, carPhotos);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(BusinessFeedBackActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(BusinessFeedBackActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    public void newAelertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(context, R.layout.alertdialog, null);
        dialog.setView(view);
        EditText et = view.findViewById(R.id.et_feedback);
        TextView cancel = view.findViewById(R.id.tv_cancel);
        TextView confirm = view.findViewById(R.id.tv_confirm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equals(et.getText().toString().trim())) {
                    showFailureMsg("请输入反馈信息");
                    return;
                } else {

                    if (!netConnected) {
                        showNetError();
                    } else if (isFastDoubleClick(v.getId())) {
                        return;
                    } else {
                        feedback(workflow_name, et.getText().toString().trim());
                    }
                }
            }
        });
        dialog.show();
    }

    @Override
    public void refreshData(BusFirstFeedBackResponse.Result result) {
        car_break_rules = result.getCar_break_rules();
        insurance = result.getInsurance();
        remark = result.getRemark();//信息核查的remark 在上面显示


        if (!"".equals(car_break_rules)) {
            evaluationEtCarType.setText(car_break_rules.trim());
        }
        if (!"".equals(insurance)) {
            evaluationEtCarModel.setText(insurance.trim());
        }
        if (!"".equals(remark)) {
            tvBeizhuContent.setText(remark.trim());
        }
        if ("初步反馈".equals(workflow_name))//如果是初步反馈，显示的是初步定额的信息
        {
            first_assess_amount_remark = result.getFirst_assess_amount_remark();//初步定额的remark 在下面显示
            if (!"".equals(first_assess_amount_remark)) {

                evaluationEtBeizhu.setText(first_assess_amount_remark.trim());
                evaluationBeizhuWordsLeft.setText(first_assess_amount_remark.trim().length() + "/100字");
            }

            car_assess_amount = result.getFirst_assess_amount();
            if (!"".equals(car_assess_amount)) {
                evaluationEtCarAge.setText(car_assess_amount.trim());
            }

        } else {//如果是最终反馈，显示的是最终定额的信息

            sure_amount_remark = result.getSure_amount_remark();//最终定额定额的remark 在下面显示
            if (!"".equals(sure_amount_remark)) {

                evaluationEtBeizhu.setText(sure_amount_remark.trim());
                evaluationBeizhuWordsLeft.setText(sure_amount_remark.trim().length() + "/100字");
            }

            car_assess_amount = result.getCar_assess_amount();
            if (!"".equals(car_assess_amount)) {
                evaluationEtCarAge.setText(car_assess_amount.trim());
            }

            loan_length = result.getLoan_length().trim();
            if (!"".equals(loan_length)){
                etLoanduration.setText(loan_length);
            }
        }

        carPhotos.clear();
        for (String arg : result.getCar_imgs()) {
            carPhotos.add(new LocalMedia(arg, 10000L, 0, ""));
        }
        carsAdapter.notifyDataSetChanged();

        reportPhotos.clear();
        for (String arg : result.getAss_imgs()) {
            reportPhotos.add(new LocalMedia(arg, 10000L, 0, ""));
        }
        reportsAdapter.notifyDataSetChanged();
    }

    @Override
    public String getBakInfo() {
        return evaluationEtBeizhu.getText().toString().trim();
    }


    @OnClick({R.id.ig_back, R.id.evaluationBtnRefuse, R.id.evaluationBtnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.evaluationBtnRefuse:
                newAelertDialog();
                break;
            case R.id.evaluationBtnNext:
                if (!netConnected) {
                    showNetError();
                } else if (isFastDoubleClick(R.id.evaluationBtnNext)) {
                    return;
                } else {
                    submitMsg(workflow_name);
                }
                break;
        }
    }

    @Override
    public void hideProgressBar() {
        if (submitDialog != null) {
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    @Override
    public void showProgressBar() {
        if (submitDialog != null) {
            submitDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressBar();
    }

    @Override
    public int getLoanLength(){
        String duration = etLoanduration.getText().toString().trim();
        if (null != duration && duration.length() >0){
            return Integer.parseInt(duration);
        }
        return 0;
    }
    @Override
    public float getLoanRate(){
        String rate = etLoanrate.getText().toString().trim();
        if (null != rate && rate.length() >0){
            return Float.parseFloat(rate);
        }
        return 0;
    }
    @Override
    public String getLoanReturnMethod(){
        return Constant.LoanPayBackWay.get(nsPaybackway.getSelectedIndex());
    }
}
