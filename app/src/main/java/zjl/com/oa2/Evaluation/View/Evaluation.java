package zjl.com.oa2.Evaluation.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Base.PhotosActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.Evaluation.Model.EvaluationPresenterImpl;
import zjl.com.oa2.Evaluation.Presenter.IEvaluationPresenter;
import zjl.com.oa2.Evaluation.Presenter.IEvaluationView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class Evaluation extends PhotosActivity implements IEvaluationView {

    @Bind(R.id.evaluation_car_age)
    TextView evaluationCarAge;
    @Bind(R.id.evaluation_et_car_age)
    EditText evaluationEtCarAge;
    @Bind(R.id.evaluation_car_type)
    TextView evaluationCarType;
    @Bind(R.id.evaluation_et_car_type)
    EditText evaluationEtCarType;
    @Bind(R.id.evaluation_car_model)
    TextView evaluationCarModel;
    @Bind(R.id.evaluation_et_car_model)
    EditText evaluationEtCarModel;
    @Bind(R.id.photos)
    RecyclerView photos;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.evaluation_et_beizhu)
    EditText evaluationEtBeizhu;
    @Bind(R.id.evaluationBtnNext)
    Button evaluationBtnNext;
    @Bind(R.id.evaluation_market_value)
    TextView evaluationMarketValue;
    @Bind(R.id.evaluation_et_market_value)
    EditText evaluationEtMarketValue;
    @Bind(R.id.rl_market_value)
    RelativeLayout rlMarketValue;
    @Bind(R.id.evaluation_real_value)
    TextView evaluationRealValue;
    @Bind(R.id.evaluation_et_real_value)
    EditText evaluationEtRealValue;
    @Bind(R.id.rl_real_value)
    RelativeLayout rlRealValue;
    @Bind(R.id.uploadtype)
    SwitchButton uploadtype;
    @Bind(R.id.uploadtypehint)
    TextView uploadtypehint;
    @Bind(R.id.evaluation_et_car_milage)
    EditText evaluationEtCarMilage;
    @Bind(R.id.market_value_part)
    TextView marketValuePart;
    @Bind(R.id.real_value_part)
    TextView realValuePart;

    private IEvaluationPresenter evaluationPresenter;
    private int workflow_content_id;
    private int wk_point_id;
//    private BuildBean dialog;

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
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        evaluationPresenter = new EvaluationPresenterImpl(this);
        workflow_content_id = Integer.parseInt(getIntent().getStringExtra("workflow_content_id"));
        wk_point_id = Integer.parseInt(getIntent().getStringExtra("wk_point_id"));


        type = getIntent().getStringExtra("type");
        if (type != null && "bohui".equals(type)) {
            uploadtype.setVisibility(View.VISIBLE);
            uploadtypehint.setVisibility(View.VISIBLE);
            evaluationPresenter.Form(getToken(), workflow_content_id, wk_point_id);
        } else {
            uploadtype.setVisibility(View.GONE);
            uploadtypehint.setVisibility(View.GONE);
        }
        uploadtype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(isChecked);
                uploadType = isChecked;
            }
        });
        evaluationEtMarketValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    String inputContent = charSequence.toString();
                    if (inputContent.contains(".")) {
                        int maxLength = inputContent.indexOf(".") + mDecimalNumber + 1;
                        INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(maxLength);
                    } else {
                        INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(mMaxIntegralLength);
                    }
                    evaluationEtMarketValue.setFilters(INPUT_FILTER_ARRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        evaluationEtRealValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    String inputContent = charSequence.toString();
                    if (inputContent.contains(".")) {
                        int maxLength = inputContent.indexOf(".") + mDecimalNumber + 1;
                        INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(maxLength);
                    } else {
                        INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(mMaxIntegralLength);
                    }
                    evaluationEtRealValue.setFilters(INPUT_FILTER_ARRAY);
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

        setRecycleViewAdapter(photos, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress();
    }

    @OnClick({R.id.ig_back, R.id.evaluation_et_beizhu, R.id.evaluationBtnNext, R.id.evaluation_et_car_age, R.id.evaluation_et_car_type, R.id.evaluation_et_car_model, R.id.photos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.evaluation_et_car_age:
                break;
            case R.id.evaluation_et_car_type:
                break;
            case R.id.evaluation_et_car_model:
                break;
            case R.id.photos:
                break;
            case R.id.evaluation_et_beizhu:
                break;
            case R.id.evaluationBtnNext:
                uploadMsg();
                break;
        }
    }

    private void uploadMsg() {
        if (!netConnected) {
            showNetError();
        } else if (isFastDoubleClick(R.id.evaluationBtnNext)) {
            return;
        } else {
            evaluationPresenter.uploadMsg(request_start_flag,
                    uploadtype.isChecked() ? UPLOAD_TYPE_ADD : UPLOAD_TYPE_NORMAL,getToken(),
                    Integer.parseInt(getCarAge()), getCarType(), getCarModel(),getCarMilage(),
                    getBakInfo(), getMarketValue(), getTakeValue(),
                    workflow_content_id, wk_point_id, getUploadPhotos());
        }

    }

    @Override
    public String getToken() {
        return UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
    }

    @Override
    public String getCarAge() {
        String age = evaluationEtCarAge.getText().toString().trim();
        if ("".equals(age)) {
            age = "0";
        }
        return age;
    }

    @Override
    public String getCarType() {
        return evaluationEtCarType.getText().toString().trim();
    }

    @Override
    public String getCarModel() {
        return evaluationEtCarModel.getText().toString().trim();
    }

    @Override
    public String getBakInfo() {
        return evaluationEtBeizhu.getText().toString().trim();
    }

    @Override
    public String getMarketValue() {
        return evaluationEtMarketValue.getText().toString().trim();
    }

    @Override
    public String getTakeValue() {
        return evaluationEtRealValue.getText().toString().trim();
    }

    @Override
    public String getCarMilage() {
        return evaluationEtCarMilage.getText().toString().trim();
    }

    @Override
    public List<LocalMedia> getUploadPhotos() {
        return this.getSelectList();
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

    @Override
    public void loadForms(FormResponse.Result result) {
        if (result != null){
            for (FormResponse.Result.Form form:result.getFormList()){
                String title = form.getControl_title();
                switch (title){
                    case "车辆年份":
                        evaluationEtCarAge.setText(form.getData_con());
                        break;
                    case "车辆品牌":
                        evaluationEtCarType.setText(form.getData_con());
                        break;
                    case "车辆型号":
                        evaluationEtCarModel.setText(form.getData_con());
                        break;
                    case "里程数":
                        evaluationEtCarMilage.setText(form.getData_con());
                        break;
                    case "市场价":
                        evaluationEtMarketValue.setText(form.getData_con());
                        break;
                    case "收车价":
                        evaluationEtRealValue.setText(form.getData_con());
                        break;
                    case "备注":
                        evaluationEtBeizhu.setText(form.getData_con());
                        break;
                }
            }
        }
    }
}
