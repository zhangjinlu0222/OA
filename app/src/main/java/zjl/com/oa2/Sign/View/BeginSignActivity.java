package zjl.com.oa2.Sign.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Adapter.BeginSignAdapter;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.CustomView.MyListView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.GetBeginSignResponse;
import zjl.com.oa2.Sign.Model.BeginSignPresenterImpl;
import zjl.com.oa2.Sign.Presenter.IBeginSignPresenter;
import zjl.com.oa2.Sign.Presenter.IBeginSignView;
import zjl.com.oa2.Utils.ListViewHeight;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class BeginSignActivity extends BaseActivity implements IBeginSignView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.listview)
    MyListView listview;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.etBakInfo)
    TextView etBakInfo;
    @Bind(R.id.Btnnext)
    Button Btnnext;
    @Bind(R.id.title)
    TextView title;

    private String token,workflow_content_id,wk_point_id;
    private List<GetBeginSignResponse.Result.Data> datalist = new ArrayList<>();
    private BeginSignAdapter adapter;
    private IBeginSignPresenter beginSignPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_signing);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        title.setText(context.getString(R.string.startsign));
        etBakInfo.setEnabled(false);

        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");

        adapter = new BeginSignAdapter(listview,datalist,context);
        listview.setAdapter(adapter);
        ListViewHeight.setListViewHeightBasedOnChildren(listview);

        submitDialog.msg = "加载中";
        beginSignPresenter = new BeginSignPresenterImpl(this);
        if (!netConnected){
            showNetError();
        }else{
            beginSignPresenter.getBeginSign(token,workflow_content_id);
        }
    }

    @OnClick({R.id.ig_back, R.id.etBakInfo, R.id.Btnnext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.etBakInfo:
                break;
            case R.id.Btnnext:
                if (!netConnected){
                    showNetError();
                }else if(isFastDoubleClick(R.id.Btnnext)){
                    return;
                } else{
                    submitDialog.msg = "提交中";
                    beginSignPresenter.postBeginSign(token,workflow_content_id,wk_point_id);
                }
                break;
        }
    }

    @Override
    public void refreshData(GetBeginSignResponse.Result data) {

        if (data == null ){
            this.showFailureMsg("查看签约界面-查看签约接口-获取-数据异常");
            return;
        }

        if (data.getList() != null && data.getList().size() > 0){
            datalist.clear();
            datalist.addAll(data.getList());
            adapter.notifyDataSetChanged();
        }

        if (data.getRemark() != null  && data.getRemark().length() > 0){
            etBakInfo.setText(data.getRemark());
            evaluationBeizhuWordsLeft.setText(data.getRemark().length() + Constant.WordsLeft);
        }else{
            etBakInfo.setText("");
            etBakInfo.setHint("");
            evaluationBeizhuWordsLeft.setText("0" + Constant.WordsLeft);
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
}
