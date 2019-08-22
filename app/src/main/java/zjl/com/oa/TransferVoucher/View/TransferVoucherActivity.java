package zjl.com.oa.TransferVoucher.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.TransferVoucherAdapter;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.PhotosActivity;
import zjl.com.oa.Bean.UploadCarPhotosType;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.CustomView.MyExpandableListview;
import zjl.com.oa.R;
import zjl.com.oa.Response.GetTransferVoucherResponse;
import zjl.com.oa.TransferVoucher.Model.TransferVoucherPresenterImpl;
import zjl.com.oa.TransferVoucher.Presenter.ITransferVoucherPresenter;
import zjl.com.oa.TransferVoucher.Presenter.ITransferVoucherView;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class TransferVoucherActivity extends PhotosActivity implements ITransferVoucherView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.listview)
    MyExpandableListview listview;
    @Bind(R.id.transfervoucher)
    RecyclerView transfervoucher;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.etBakInfo)
    EditText etBakInfo;
    @Bind(R.id.Btnnext)
    Button Btnnext;
    @Bind(R.id.uploadtype)
    SwitchButton uploadtype;
    @Bind(R.id.uploadtypehint)
    TextView uploadtypehint;

    private TransferVoucherAdapter transferVoucherAdapter;
    private ITransferVoucherPresenter transferVoucherPresenter;
    private List<List<GetTransferVoucherResponse.Result.Section.dict>> childData = new ArrayList<>();
    private List<String> groupData = new ArrayList<>();

    private String token, workflow_content_id, wk_point_id;
    private int type_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_charge);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        type_id = new UploadCarPhotosType().getType_id(Integer.parseInt(wk_point_id));
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        transferVoucherPresenter = new TransferVoucherPresenterImpl(this);
        if (!netConnected) {
            showNetError();
        } else {
            submitDialog.msg = "加载中";
            transferVoucherPresenter.getTransferVoucher(token, workflow_content_id);
        }

        transferVoucherAdapter = new TransferVoucherAdapter(context, groupData, childData, listview);
        listview.setAdapter(transferVoucherAdapter);
        setRecycleViewAdapter(transfervoucher, true);
        etBakInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                evaluationBeizhuWordsLeft.setText(s.length() + Constant.WordsLeft);
            }
        });

        type = getIntent().getStringExtra("type");
        if (type != null && "bohui".equals(type)) {
            uploadtype.setVisibility(View.VISIBLE);
            uploadtypehint.setVisibility(View.VISIBLE);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress();
    }

    @Override
    public void toMainActivity() {
        this.finish();
    }

    @Override
    public void refreshData(List<GetTransferVoucherResponse.Result.Section> data) {

        if (data != null && data.size() > 0) {
            groupData.clear();
            childData.clear();

            for (GetTransferVoucherResponse.Result.Section section : data) {
                groupData.add(section.getTitle());
                childData.add(section.getDict_list());
            }
            transferVoucherAdapter.notifyDataSetChanged();

            for (int i = 0; i < transferVoucherAdapter.getGroupCount(); i++) {
                listview.expandGroup(i);
            }
        } else {
            this.showFailureMsg("数据异常");
        }
    }

    @Override
    public String getBakInfo() {
        return etBakInfo.getText().toString().trim();
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

    @OnClick({R.id.ig_back, R.id.Btnnext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.Btnnext:
                if (!netConnected) {
                    showNetError();
                } else if (isFastDoubleClick(R.id.Btnnext)) {
                    return;
                } else {
                    submitDialog.msg = "提交中";
                    transferVoucherPresenter.uploadPhotos(request_start_flag,
                            uploadtype.isChecked() ? UPLOAD_TYPE_ADD : UPLOAD_TYPE_NORMAL,
                            token, getBakInfo(), workflow_content_id, wk_point_id,
                            getSelectList(), Integer.toString(type_id));
                }
                break;
        }
    }
}
