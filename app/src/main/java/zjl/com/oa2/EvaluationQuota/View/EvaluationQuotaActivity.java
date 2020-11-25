package zjl.com.oa2.EvaluationQuota.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dou361.dialogui.DialogUIUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Adapter.GridImageAdapter;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Base.FullyGridLayoutManager;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.EvaluationQuota.Model.EvaluationQuotaPresenterImpl;
import zjl.com.oa2.EvaluationQuota.Presenter.IEvaluationQuotaPresenter;
import zjl.com.oa2.EvaluationQuota.Presenter.IEvaluationQuotaView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.EvaluationQuotaResponse;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class EvaluationQuotaActivity extends BaseActivity implements IEvaluationQuotaView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.tv_evaluation_deposit)
    TextView tvEvaluationDeposit;
    @Bind(R.id.et_evaluation_deposit)
    EditText evaluationEtDeposit;
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
    @Bind(R.id.photoSelected)
    ImageView photoSelected;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.evaluationReport)
    RecyclerView reports;
    @Bind(R.id.carPhotoSelected)
    ImageView carPhotoSelected;
    @Bind(R.id.carphotos)
    RecyclerView cars;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.evaluation_et_beizhu)
    EditText evaluationEtBeizhu;
    @Bind(R.id.evaluationBtnRefuse)
    Button evaluationBtnRefuse;
    @Bind(R.id.evaluationBtnNext)
    Button evaluationBtnNext;
    @Bind(R.id.market_amount)
    TextView marketAmount;
    @Bind(R.id.take_amount)
    TextView takeAmount;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_market_amount)
    TextView tvMarketAmount;
    @Bind(R.id.tv_take_amount)
    TextView tvTakeAmount;
    @Bind(R.id.rl_market_amount)
    RelativeLayout rlMarketAmount;
    @Bind(R.id.rl_take_amount)
    RelativeLayout rlTakeAmount;

    private List<LocalMedia> reportPhotos = new ArrayList<>();
    private List<LocalMedia> carPhotos = new ArrayList<>();
    private GridImageAdapter carsAdapter;
    private GridImageAdapter reportsAdapter;

    private String token;
    private String workflow_content_id, wk_point_id,workflow_name;
    private String car_break_rules, insurance, remark;
    private boolean epReUpload, cpReUpload;

    private String market_amount, take_amount,type,way;
//    private BuildBean loadingDialog;

    private IEvaluationQuotaPresenter evaluationQuotaPresenter;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_quota);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        workflow_name = getIntent().getStringExtra("workflow_name");
        way = getIntent().getStringExtra("way");
        type = getIntent().getStringExtra("type");

        initView(type);

        evaluationQuotaPresenter = new EvaluationQuotaPresenterImpl(this);
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

//        loadingDialog = DialogUIUtils.showLoading(context,"加载�?,true,true,true,true);

        evaluationEtDeposit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (evaluationEtDeposit.getInputType() != InputType.TYPE_CLASS_TEXT){

                    if(charSequence.length() > 0) {
                        String inputContent = charSequence.toString();
                        if (inputContent.contains(".")) {
                            int maxLength = inputContent.indexOf(".") + mDecimalNumber + 1;
                            INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(maxLength);
                        } else {
                            INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(mMaxIntegralLength);
                        }
                        evaluationEtDeposit.setFilters(INPUT_FILTER_ARRAY);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
    }

    private void initView(String arg){
        if (arg != null && arg.equals("bohui")){
            carPhotoSelected.setEnabled(false);
            photoSelected.setEnabled(false);
            evaluationBtnRefuse.setEnabled(false);
            carPhotoSelected.setVisibility(View.INVISIBLE);
            photoSelected.setVisibility(View.INVISIBLE);
            evaluationBtnRefuse.setBackgroundColor(Color.LTGRAY);
        }else if ("抵押".equals(way) && "最终定额".equals(workflow_name)){
            //抵押的最终定额不让重新上传
            carPhotoSelected.setEnabled(false);
            photoSelected.setEnabled(false);
            evaluationBtnRefuse.setEnabled(false);
            carPhotoSelected.setVisibility(View.INVISIBLE);
            photoSelected.setVisibility(View.INVISIBLE);
            evaluationBtnRefuse.setBackgroundColor(Color.LTGRAY);
        }else{
            carPhotoSelected.setEnabled(true);
            photoSelected.setEnabled(true);
            evaluationBtnRefuse.setEnabled(true);
            carPhotoSelected.setVisibility(View.VISIBLE);
            photoSelected.setVisibility(View.VISIBLE);
            evaluationBtnRefuse.setBackgroundResource(R.mipmap.info_bg_next);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //6初步定额
        //10最终定额
        if (workflow_name != null && workflow_name.length() > 0){
            title.setText(workflow_name);
        }

        if ("初步定额".equals(workflow_name)) {
            evaluationEtDeposit.setHint("请输入评估范围");
            evaluationEtDeposit.setText("");
            evaluationEtDeposit.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            evaluationEtDeposit.setHint("请输入评估金额");
            evaluationEtDeposit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        if (!netConnected){
            showNetError();
        }else {
            submitDialog.msg = "加载中";
            evaluationQuotaPresenter.LookFirstSureAmount(token, workflow_content_id);
        }

        initRecycleView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭加载�?
        hideProgressBar();
    }

    private void initRecycleView() {
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        reports.setLayoutManager(manager2);
        reportsAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
            }
        }, reportPhotos);
        reportsAdapter.setSelectMax(reportPhotos.size());
        reportsAdapter.setShowDel(false);
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
                            // 预览图片 可自定长按保存路�?
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(EvaluationQuotaActivity.this).externalPicturePreview(position, reportPhotos);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(EvaluationQuotaActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(EvaluationQuotaActivity.this).externalPictureAudio(media.getPath());
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
        carsAdapter.setSelectMax(carPhotos.size());
        carsAdapter.setShowDel(false);
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
                            // 预览图片 可自定长按保存路�?
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(EvaluationQuotaActivity.this).externalPicturePreview(position, carPhotos);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(EvaluationQuotaActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(EvaluationQuotaActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    reportPhotos = PictureSelector.obtainMultipleResult(data);
                    reportsAdapter.setList(reportPhotos);
                    reportsAdapter.notifyDataSetChanged();
                    break;
                case PictureConfig.REQUEST_CAMERA:
                    // 图片选择
                    carPhotos = PictureSelector.obtainMultipleResult(data);
                    carsAdapter.setList(carPhotos);
                    carsAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @OnClick({R.id.ig_back, R.id.photoSelected, R.id.carPhotoSelected, R.id.evaluationBtnRefuse, R.id.evaluationBtnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.photoSelected:
                epReUpload = !epReUpload;
                loadUploadFlag(photoSelected, epReUpload);
                break;
            case R.id.carPhotoSelected:
                cpReUpload = !cpReUpload;
                loadUploadFlag(carPhotoSelected, cpReUpload);
                break;
            case R.id.evaluationBtnRefuse:
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.evaluationBtnRefuse)){
                    return;
                }else {

                    submitDialog.msg = "提交中";
                    evaluationQuotaPresenter.SureAmountReturn(token, workflow_content_id, wk_point_id, Integer.toString(getReUploadType(epReUpload, cpReUpload)));
                }
                break;
            case R.id.evaluationBtnNext:
                //6 初步定额提交数据
                //10 最终定额提交数据
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.evaluationBtnNext)){
                    return;
                }else if ("初步定额".equals(workflow_name)){
                    submitDialog.msg = "提交中";
                    evaluationQuotaPresenter.FirstSureAmount(token, workflow_content_id, wk_point_id, getQuota(), getBakInfo());
                }else{
                    submitDialog.msg = "提交中";
                    evaluationQuotaPresenter.SureAmount(token, workflow_content_id, wk_point_id, Double.parseDouble(getQuota()), getBakInfo());
                }
                break;
        }
    }

    @Override
    public void refreshData(EvaluationQuotaResponse.Result result) {
        car_break_rules = result.getCar_break_rules();
        insurance = result.getInsurance();
        remark = result.getRemark();
        market_amount = result.getMarket_amount();
        take_amount = result.getTake_amount();

        if (!"".equals(car_break_rules)) {
            evaluationEtCarType.setText(car_break_rules.trim());
        }
        if (!"".equals(insurance)) {
            evaluationEtCarModel.setText(insurance.trim());
        }
        if (!"".equals(remark)) {
            tvBeizhuContent.setText(remark.trim());
        }
        if (!"".equals(take_amount)) {
            takeAmount.setText(take_amount.trim());
        }
        if (!"".equals(market_amount)) {
            marketAmount.setText(market_amount.trim());
        }

        if ("初步定额".equals(workflow_name)){
            evaluationEtDeposit.setHint("请输入评估范围");
        }else{
            evaluationEtDeposit.setHint("请输入评估定额");
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
    public String getQuota() {
        String quota = evaluationEtDeposit.getText().toString().trim();
        if ("初步定额".equals(title.getText().toString().trim())){
            return quota;
        }else {
            if ("".equals(quota)){
                return "0";
            }else{
                return quota;
            }
        }
    }

    @Override
    public List<LocalMedia> getCarPhotos() {
        return carPhotos;
    }

    @Override
    public List<LocalMedia> getReportPhotos() {
        return reportPhotos;
    }

    @Override
    public String getBakInfo() {
        return evaluationEtBeizhu.getText().toString().trim();
    }

    @Override
    public void loadUploadFlag(ImageView v, boolean flag) {
        if (flag) {
            Glide.with(this).load(R.mipmap.checked).into(v);
        } else {
            Glide.with(this).load(R.mipmap.unchecked).into(v);
        }
    }

    @Override
    public int getReUploadType(boolean epFlag, boolean cpFlag) {
        if (epFlag && cpFlag) {
            return 3;
        }
        if (epFlag && !cpFlag) {
            return 1;
        }
        if (!epFlag && cpFlag) {
            return 2;
        }
        return 0;
    }
}
