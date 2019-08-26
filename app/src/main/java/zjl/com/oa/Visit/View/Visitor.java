package zjl.com.oa.Visit.View;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.FormListsAdapter;
import zjl.com.oa.Adapter.InfoCheckAdapter;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.CustomView.MyListView;
import zjl.com.oa.InformationCheck.View.InformationCheck;
import zjl.com.oa.R;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Response.LookInterviewResponse;
import zjl.com.oa.Utils.TitleBarUtil;
import zjl.com.oa.Visit.Model.NewVisitorPresenterImpl;
import zjl.com.oa.Visit.Presenter.INewVisitorPresenter;
import zjl.com.oa.Visit.Presenter.INewVisitorView;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class Visitor extends BaseActivity implements INewVisitorView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.listview)
    MyListView listview;
    @Bind(R.id.button)
    Button button;
    private INewVisitorPresenter newVisitorPresenter;
    private String token;
    private String w_con_id, w_pot_id;

    private List<LocalMedia> lFiles = new ArrayList<>();

    private List<FormResponse.Result.Form> lInfos = new ArrayList<>();
    private InfoCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        w_con_id = getIntent().getStringExtra("workflow_content_id");
        w_pot_id = getIntent().getStringExtra("wk_point_id");
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        submitDialog.msg = "加载中";

        newVisitorPresenter = new NewVisitorPresenterImpl(this);
        newVisitorPresenter.Form(token, w_con_id, w_pot_id);

//        adapter = new InfoCheckAdapter(lInfos, this, listview);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        submitDialog = DialogUIUtils.showLoading(this, "提交中", true,
                false, false, true);

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

        lInfos.clear();
        lInfos.addAll(result.getFormList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void toMainActivity() {
        this.finish();
    }

    @Override
    public void showFailureMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newVisitorPresenter.onDestoryView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress();
    }

    @OnClick({R.id.ig_back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.button:
                if (!netConnected) {
                    showNetError();
                    return;
                }

                if (isFastDoubleClick(R.id.button)) {
                    return;
                } else {
                    submitDialog.msg = "提交中";
//                    newVisitorPresenter.newVisitor(token, getCustomerName(), getCustormerSource(), getProductType(), getPointNotice(), getBakInfo(), getContentId(), getPointId());
                }
                break;
        }
    }
}
