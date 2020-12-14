package zjl.com.oa2.WorkFlow.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import zjl.com.oa2.Adapter.WorkDetailAdapter;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.Bean.WorkFlowGroupData;
import zjl.com.oa2.CustomView.MyExpandableListview;
import zjl.com.oa2.LoanDetail.View.LoanDetail;
import zjl.com.oa2.MapView.View.MapView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.GetWorkFlowResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.PhotoVideoDetailResponse;
import zjl.com.oa2.Utils.TitleBarUtil;
import zjl.com.oa2.WorkFlow.Model.WorkFlowPresenterImpl;
import zjl.com.oa2.WorkFlow.Presenter.IWorkFlowPresenter;
import zjl.com.oa2.WorkFlow.Presenter.IWorkFlowView;

public class WorkDetailActivity extends BaseActivity implements IWorkFlowView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.time_icon)
    ImageView timeIcon;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_manager)
    TextView tvManager;
    @Bind(R.id.workdetail)
    MyExpandableListview workdetail;
    @Bind(R.id.btn_workdetail_share)
    Button btnWorkdetailShare;

    private IWorkFlowPresenter workFlowPresenter;
    private WorkDetailAdapter workDetailAdapter;
    private List<WorkFlowGroupData> groupData = new ArrayList<>();
    private List<List<GetWorkFlowResponse.Result.Section.dict>> childData = new ArrayList<>();
    private String workflow_content_id, token, date, manager,proc_type_id;
    private String wx_share_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        String autoUpdate = UserInfo.getInstance(context).getUserInfo(UserInfo.AUTOUPDATE);
        if (autoUpdate != null && autoUpdate.equals("1")){
            //进入流程，保存状态
            saveOperation("workdetail");
        }

        workDetailAdapter = new WorkDetailAdapter(this, groupData, childData, workdetail);
        workdetail.setGroupIndicator(null);
        workdetail.setAdapter(workDetailAdapter);


        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        date = getIntent().getStringExtra("date");
        manager = getIntent().getStringExtra("manager");
        proc_type_id = getIntent().getStringExtra("proc_type_id");
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        tvTime.setText(date);
        tvManager.setText(manager);

        workFlowPresenter = new WorkFlowPresenterImpl(this);
        submitDialog.msg = "加载中";
        workFlowPresenter.getWorkDetail(token, workflow_content_id,proc_type_id);
    }


    @OnClick({R.id.ig_back, R.id.btn_workdetail_share,R.id.tv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                toMainActivity();
                break;
            case R.id.btn_workdetail_share:
                WXShare(wx_share_content);
                break;
            case R.id.tv_location:
                toMapView();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void WXShare(String content) {
        OnekeyShare oks = new OnekeyShare();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("华融OA系统");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Toast.makeText(context, "分享完成", Toast.LENGTH_SHORT).show();
//                Log.e("TAG", "OnekeyShare onComplete");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "OnekeyShare onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "OnekeyShareonCancel");
            }
        });

// 启动分享GUI
        oks.show(this);
    }

    @Override
    public void toMainActivity() {
        this.finish();
    }

    @Override
    public void refreshData(GetWorkFlowResponse.Result result) {
        if (result != null && result.getList().size() > 0) {
            groupData.clear();
            childData.clear();

            for (GetWorkFlowResponse.Result.Section section : result.getList()) {
                WorkFlowGroupData wfGroupData = new WorkFlowGroupData();
                wfGroupData.setEdit(result.getEdit());
                wfGroupData.setProcTypeId(result.getProc_type_id());
                wfGroupData.setDate(section.getDate());
                wfGroupData.setReedit_flag(section.getReedit_flag());
                wfGroupData.setState(section.getState());
                wfGroupData.setTitle(section.getTitle());
                wfGroupData.setW_pot_id(section.getW_pot_id());
                groupData.add(wfGroupData);

                childData.add(section.getDict_list());
            }

            workDetailAdapter.notifyDataSetChanged();

            for (int i = 0; i < workDetailAdapter.getGroupCount(); i++) {
                if (workDetailAdapter.getChildrenCount(i) > 0) {
                    workdetail.expandGroup(i);
                } else {
                    workdetail.collapseGroup(i);
                }
            }

            if (result.getEdit() == 1){
                btnWorkdetailShare.setVisibility(View.VISIBLE);
            }else{
                btnWorkdetailShare.setVisibility(View.GONE);
            }

            wx_share_content = result.getWx_share_content();

        } else {
            this.showFailureMsg("工作流程数据异常");
        }
    }

    @Override
    public void showProcess() {
        if (submitDialog != null) {
            submitDialog.show();
        }
    }

    @Override
    public void hideProcess() {
        if (submitDialog != null) {
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void loadPhotosAndVideos(PhotoVideoDetailResponse.Result data) {

    }

    @Override
    public void updateImgRefuse() {

    }

    @Override
    public void downloadContract(String url) {

    }

    public void lookLoanDetail(){

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token );
        map.put("w_con_id",workflow_content_id );

        workFlowPresenter.LookLoanDetail(map);
    }

    public void toMapView(){
        Intent intent = new Intent(this, MapView.class);
        intent.putExtra("w_con_id", workflow_content_id);
        context.startActivity(intent);
    }

    public void toLoanDetail(LoanDetailResponse.Result result){
        Intent intent = new Intent(this, LoanDetail.class);
        intent.putExtra("loanDetail", result);
        context.startActivity(intent);
    }
}
