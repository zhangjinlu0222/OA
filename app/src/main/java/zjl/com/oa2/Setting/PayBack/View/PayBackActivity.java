package zjl.com.oa2.Setting.PayBack.View;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIDateTimeSaveListener;
import com.dou361.dialogui.widget.DateSelectorWheelView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.R;
import zjl.com.oa2.RenewLoan.View.RenewLoanActivity;
import zjl.com.oa2.Response.SearchNameResponse;
import zjl.com.oa2.Setting.PayBack.Model.PayBackPresenterImpl;
import zjl.com.oa2.Setting.PayBack.Presenter.IPayBackView;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class PayBackActivity extends BaseActivity implements IPayBackView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.btnPayBackNameSearch)
    Button btnPayBackNameSearch;
    @Bind(R.id.tvCarType)
    TextView tvCarType;
    @Bind(R.id.ns_cars)
    NiceSpinner nsCars;
    @Bind(R.id.tvNeedPayBack)
    TextView tvNeedPayBack;
    @Bind(R.id.etNeedPayBack)
    EditText etNeedPayBack;
    @Bind(R.id.tvHasPayback)
    TextView tvHasPayback;
    @Bind(R.id.etHasPayback)
    EditText etHasPayback;
    @Bind(R.id.tvActualPayback)
    TextView tvActualPayback;
    @Bind(R.id.etActualPayback)
    EditText etActualPayback;
    @Bind(R.id.rlActualPaybackDate)
    RelativeLayout rlActualPaybackDate;
    @Bind(R.id.tvActualPaybackDate)
    TextView tvActualPaybackDate;
    @Bind(R.id.etActualPaybackDate)
    EditText etActualPaybackDate;
    @Bind(R.id.tvPayBackPeriods)
    TextView tvPayBackPeriods;
    @Bind(R.id.etPayBackPeriods)
    EditText etPayBackPeriods;
    @Bind(R.id.btnPayBackMark)
    Button btnPayBackMark;

    private String token;
    private PayBackPresenterImpl mPayBackPresenter;

    private List<String> carsDescirption = new ArrayList<>();
    private List<String> carsWorkFlowId = new ArrayList<>();
    public String profit_amount;/*应还款*/
    public String actual_profit_amount;/*已还款*/
    public String schedule_id;
    public String return_date;/*期数*/
    public String name;/*客户名*/
    public String paybackDate;/*实际还款日期*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_back);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        token = UserInfo.getInstance(context).getUserInfo("token");
        mPayBackPresenter = new PayBackPresenterImpl(this);
        nsCars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String w_con_id = carsWorkFlowId.get(position);
                if (!"".equals(w_con_id)){
                    mPayBackPresenter.SearchCarType(token,w_con_id);
                }else{
                    clearEtInputedText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString().trim();
                if ("".equals(name)){
                    clearEtInputedText();
                    clearNsDataSource();
                }
            }
        });
    }

    @OnClick({R.id.ig_back, R.id.btnPayBackNameSearch, R.id.btnPayBackMark,R.id.etActualPaybackDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.btnPayBackNameSearch:

                if(isFastDoubleClick(R.id.btnPayBackNameSearch)){
                    return;
                }

                String name = etName.getText().toString().trim();

                if ("".equals(name)){
                    showFailureMsg("请输入姓名");
                    return;
                }

                //提交查询前，将数据清空
                carsDescirption.clear();
                carsWorkFlowId.clear();

                mPayBackPresenter.SearchName(token,name);

                break;
            case R.id.btnPayBackMark:

                String amount = etActualPayback.getText().toString().trim();
                String date = etActualPaybackDate.getText().toString().trim();

                mPayBackPresenter.UpdateReturnSchedule(token, amount,date, schedule_id);
                break;
            case R.id.etActualPaybackDate:
                if (isFastDoubleClick(R.id.etActualPaybackDate)){
                    return;
                }
                showDataPicker();
                break;
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

    @Override
    public void updateDataWithResponse(SearchNameResponse response) {


        SearchNameResponse.Result result = response.getResult();
        if(result == null ){
            showFailureMsg("暂无数据");
            clearEtInputedText();
            clearNsDataSource();
            return;
        }

        //如果没加载过数据则进行加载，否则不再加载数据
        if (carsDescirption.size() <= 0){
            SearchNameResponse.WorkFlow[] cars = result.getCar_list();
            if (cars == null || cars.length <= 0){
                showFailureMsg("暂无数据");
                carsDescirption.add("无匹配车型");
                carsWorkFlowId.add("");
                nsCars.attachDataSource(carsDescirption);
                clearEtInputedText();
                return;
            }

            if (cars.length != 1){
                carsDescirption.add("选择车型");
                carsWorkFlowId.add("");
            }

            for (int i=0;i<cars.length;i++){
                carsDescirption.add(cars[i].getCar());
                carsWorkFlowId.add(cars[i].getW_con_id());
            }

            if(carsDescirption.size() != 0 && carsDescirption.size() == carsWorkFlowId.size()){
                nsCars.attachDataSource(carsDescirption);
            }
        }

        profit_amount = result.getProfit_amount();
        if (!"".equals(profit_amount)){
            etNeedPayBack.setText(profit_amount);
        }

        actual_profit_amount = result.getActual_profit_amount();
        if (!"".equals(actual_profit_amount)){
            etHasPayback.setText(actual_profit_amount);
        }

        return_date = result.getReturn_date();
        if (!"".equals(return_date)){
            etPayBackPeriods.setText(return_date);
        }

        name = result.getName();
        if(!"".equals(name)){
            etName.setText(name);
        }

        schedule_id = result.getSchedule_id();

    }

    private void clearEtInputedText(){

        if (!"".equals(profit_amount)){
            profit_amount = "";
            etNeedPayBack.setText("");
        }

        if (!"".equals(actual_profit_amount)){
            actual_profit_amount = "";
            etHasPayback.setText("");
        }

        if (!"".equals(return_date)){
            return_date = "";
            etPayBackPeriods.setText("");
        }

        etActualPayback.setText("");

        schedule_id = "";
    }
    
    private void clearNsDataSource(){

        //数据内容置空

        List empty = new ArrayList();
        empty.add("");
        nsCars.attachDataSource(empty);

        carsDescirption.clear();
        carsWorkFlowId.clear();
    }

    private void showDataPicker() {
        DialogUIUtils.showDatePick(PayBackActivity.this, Gravity.BOTTOM,
                "实际还款日期", System.currentTimeMillis() + 60000,
                DateSelectorWheelView.TYPE_YYYYMMDD, 0,
                new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {
                        paybackDate = selectedDate;
                        etActualPaybackDate.setText(paybackDate);
                    }
                }).show();
    }
}
