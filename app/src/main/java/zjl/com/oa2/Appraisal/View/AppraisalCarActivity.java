package zjl.com.oa2.Appraisal.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.TitleBarUtil;

public class AppraisalCarActivity extends AppCompatActivity {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.tvCarType)
    TextView tvCarType;
    @Bind(R.id.etCarType)
    EditText etCarType;
    @Bind(R.id.tvCarModel)
    TextView tvCarModel;
    @Bind(R.id.etCarModel)
    EditText etCarModel;
    @Bind(R.id.tvCarYear)
    TextView tvCarYear;
    @Bind(R.id.etCarYear)
    EditText etCarYear;
    @Bind(R.id.btnAppraisalSearch)
    Button btnAppraisalSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraisal_car);
        ButterKnife.bind(this);


        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
    }

    @OnClick({R.id.ig_back, R.id.btnAppraisalSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                finish();
                break;
            case R.id.btnAppraisalSearch:
                toAppraisal();
                break;
        }
    }

    void toAppraisal(){
        Intent intent = new Intent(this,AppraisalActivity.class);
        String car_type = etCarType.getText().toString().trim();
        String car_model = etCarModel.getText().toString().trim();
        String car_year = etCarYear.getText().toString().trim();
        intent.putExtra("car_type",car_type);
        intent.putExtra("car_model",car_model);
        intent.putExtra("car_year",car_year);

        if (car_model.length() <= 0
                && car_type.length() <= 0
                && car_year.length() <= 0){
            Toast.makeText(this, "至少选择一项内容", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }
}
