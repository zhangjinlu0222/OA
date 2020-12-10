package zjl.com.oa.WorkFlow.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.GridImageAdapter;
import zjl.com.oa.Adapter.WorkFlowAdapter;
import zjl.com.oa.ApplicationConfig.Activitys;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Base.FullyGridLayoutManager;
import zjl.com.oa.Bean.QuestState;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.Bean.WorkFlowGroupData;
import zjl.com.oa.CustomView.MyExpandableListview;
import zjl.com.oa.R;
import zjl.com.oa.Response.GetWorkFlowResponse;
import zjl.com.oa.Response.PhotoVideoDetailResponse;
import zjl.com.oa.Utils.PhotoUtil;
import zjl.com.oa.Utils.TitleBarUtil;
import zjl.com.oa.Utils.VideoUtil;
import zjl.com.oa.WorkFlow.Model.WorkFlowPresenterImpl;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowPresenter;
import zjl.com.oa.WorkFlow.Presenter.IWorkFlowView;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class WorkFlowActivity extends BaseActivity implements IWorkFlowView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.workflow)
    MyExpandableListview workflow;
    @Bind(R.id.ig_refuse)
    Button igRefuse;

    private IWorkFlowPresenter workFlowPresenter;
    private WorkFlowAdapter workFlowAdapter;
    private List<WorkFlowGroupData> groupData = new ArrayList<>();
    private List<List<GetWorkFlowResponse.Result.Section.dict>> childData = new ArrayList<>();
    private String workflow_content_id,proc_type_id, token, date, manager;
    private BuildBean dialog = null;
    private boolean dialogshowing;
    private AlertDialog videoPreviewDialog = null;
    private BuildBean reEditDialog = null;
    private String workflow_state;

    private List<String> thumbnails = new ArrayList<>();
    private List<String> normalMedias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        dialog = DialogUIUtils.showLoading(context, "请求中", true, false, false, true);

        String autoUpdate = UserInfo.getInstance(context).getUserInfo(UserInfo.AUTOUPDATE);
        if (autoUpdate != null && autoUpdate.equals("1")){
            //进入流程，保存状态
            saveOperation("workflow");
        }

        workFlowAdapter = new WorkFlowAdapter(context, groupData, childData, workflow);
        workflow.setGroupIndicator(null);
        workflow.setAdapter(workFlowAdapter);
        workFlowAdapter.setOnItemClickListener(new WorkFlowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //如果表单结束，则管理员不能驳回
                //如果表单已驳回，则不能继续驳回
                //如果表单未驳回，则执行驳回
                switch (view.getId()) {
                    case R.id.reedit:
                        if (QuestState.isQuestDone(groupData.get(position).getStatus())) {
                            showFailureMsg("表单已结束");
                            return;
                        }

                        if (groupData.get(position).getReedit_flag() == 0) {
                            showFailureMsg("表单已驳回，点击子项上传信息或文件");
                            return;
                        }

                        if (groupData.get(position).getReedit_flag() == 1) {
                            showReEditDialog(position);
                            return;
                        }
                        break;
//                    case R.id.refuse:
//
//                        if (QuestState.isQuestDone(groupData.get(position).getStatus())){
//                            showFailureMsg("表单已结束");
//                            return;
//                        }
//                        dialog.msg = "拒件操作提交中";
//                        showRefuseDialog(position);
//                        break;
                }
            }
        });
        workflow.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //防止多次点击
                if (isFastDoubleClick()){
                    return false;
                }

                String w_pot_id = groupData.get(groupPosition).getW_pot_id();
                String workflow_name = groupData.get(groupPosition).getTitle();
                String proc_type_id = groupData.get(groupPosition).getProcTypeId();
                //如果父元组中reedit flag 为0 ，并且点击位置是groudata最后一项，则重新上传此步骤的内容
                if (0 == groupData.get(groupPosition).getReedit_flag() && childPosition == childData.get(groupPosition).size() - 1) {
                    Intent intent = new Intent(WorkFlowActivity.this, Activitys.getClass(w_pot_id));
                    intent.putExtra("workflow_content_id", workflow_content_id);
                    intent.putExtra("wk_point_id", w_pot_id);
                    intent.putExtra("workflow_name", workflow_name);
                    intent.putExtra("proc_type_id", proc_type_id);

                    if (w_pot_id != null && //面谈节点步骤ID，添加日期和经理
                            (w_pot_id.equals("30") || w_pot_id.equals("2"))) { //录入订单节点步骤ID，添加日期和经理
                        intent.putExtra("date", date);
                        intent.putExtra("manager", manager);
                    }

                    if (w_pot_id != null &&  //定额节点步骤ID,添加进入方式，驳回的时候不让改数据
                            ("6".equals(w_pot_id) || "10".equals(w_pot_id))) {
//                        intent.putExtra("type", "bohui");
                        intent.putExtra("way", proc_type_id);
                    }

                    if (w_pot_id != null &&  //业务反馈步骤ID,添加进入方式，驳回的时候不让改数据
                            ("7".equals(w_pot_id) || "11".equals(w_pot_id))) {
//                        intent.putExtra("type", "bohui");
                    }
                    intent.putExtra("type", "bohui");
                    startActivity(intent);
                } else {
                    String key = childData.get(groupPosition).get(childPosition).getL_key();
                    String value = childData.get(groupPosition).get(childPosition).getL_value();
                    if (key.contains("签约视频") && value.contains("点击查看")) {
                        if (workFlowPresenter != null) {
                            workFlowPresenter.getPhotoVideoDetail(token, workflow_content_id, "999",proc_type_id);
                        }
                    } else if (key.contains("合同") && value.contains("查看合同")){
                        if (workFlowPresenter != null){
                            workFlowPresenter.LookRefinanceContract(token,workflow_content_id,key);
                        }
                    } else if (value.contains("点击查看")) {
                        if (workFlowPresenter != null) {
                            workFlowPresenter.getPhotoVideoDetail(token, workflow_content_id, w_pot_id,proc_type_id);
                        }
                    }
                }
                return false;
            }
        });

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        proc_type_id = getIntent().getStringExtra("proc_type_id");
        //date 和manager 在表单被管理员在面谈步骤打回重新进去的时候使用
        date = getIntent().getStringExtra("date");
        manager = getIntent().getStringExtra("manager");

        //表单状态在管理员进行驳回的时候使用
        workflow_state = getIntent().getStringExtra("state");
        validateRefuse(workflow_state);

        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        workFlowPresenter = new WorkFlowPresenterImpl(this);
//        workFlowPresenter.getWorkFlow(token, workflow_content_id);
    }

    private void validateRefuse(String workflow_state) {
        ViewGroup.LayoutParams params = igRefuse.getLayoutParams();
        if (QuestState.isRefused(workflow_state)){
            igRefuse.setText(R.string.reuse);
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }else{
            igRefuse.setText(R.string.refuse);
            params.width = 100;
        }
        igRefuse.invalidate();
    }

    @Override
    public void refreshData(GetWorkFlowResponse.Result result) {
        /**
         * 管理员登录则显示拒件按钮*/
        if ( result != null && "1".equals(result.getRefuse_flag())){
            igRefuse.setVisibility(View.VISIBLE);
        }else{
            igRefuse.setVisibility(View.GONE);
        }

        if (result != null && result.getList().size() > 0) {

            groupData.clear();
            childData.clear();

            for (GetWorkFlowResponse.Result.Section section : result.getList()) {
                WorkFlowGroupData wfGroupData = new WorkFlowGroupData();
                wfGroupData.setEdit(result.getEdit());
                wfGroupData.setDate(section.getDate());
                wfGroupData.setReedit_flag(section.getReedit_flag());
                //将步骤状态写入，在是否显示驳回按钮的时候调用
                wfGroupData.setState(section.getState());
                //将表单状态写进数据源中
                wfGroupData.setStatus(workflow_state);

                wfGroupData.setTitle(section.getTitle());
                wfGroupData.setW_pot_id(section.getW_pot_id());
                wfGroupData.setProcTypeId(result.getProc_type_id());
                groupData.add(wfGroupData);

                //如果该步骤被驳回了，则添加重新上传的文字提示
                if (section.getReedit_flag() == 0) {
                    GetWorkFlowResponse.Result.Section.dict dict = new GetWorkFlowResponse.Result.Section.dict("信息资料", "重新上传");
                    if (section.getDict_list() != null && section.getDict_list().size() > 0) {
                        section.getDict_list().add(dict);
                    } else {
                        ArrayList<GetWorkFlowResponse.Result.Section.dict> list = new ArrayList<>();
                        list.add(dict);
                        section.setDict_list(list);
                    }
                }
                childData.add(section.getDict_list());
            }

            workFlowAdapter.notifyDataSetChanged();
        } else {
            this.showFailureMsg("工作流程数据异常");
        }
    }

    @Override
    public void showProcess() {
        if (dialog != null && !dialogshowing) {
            dialog.msg = "请求中";
            dialog.show();
            dialogshowing = true;
        }
    }

    @Override
    public void updateImgRefuse() {
        validateRefuse(groupData.get(0).getState());
    }

    @Override
    public void hideProcess() {
        if (dialog != null && dialogshowing) {
            DialogUIUtils.dismiss(dialog);
            dialogshowing = false;
        }
    }

    //驳回申请提交成功后重新拉取数据
    @Override
    public void reloadData() {
        if (workFlowPresenter != null) {
            workFlowPresenter.getWorkFlow(token, workflow_content_id,proc_type_id);
        }
    }

    @Override
    public void loadPhotosAndVideos(PhotoVideoDetailResponse.Result data) {

        if (data == null ){
            this.showFailureMsg("暂无数据");
            return;
        }

        Intent intent = new Intent(context,Thumbnails.class);
        intent.putExtra("thumbnails",(Serializable) data.getThum());
        intent.putExtra("normalMedias",(Serializable) data.getBig());
        startActivity(intent);
    }

    //禁止在提交驳回请求的时候退出界面
    @Override
    public void onBackPressed() {

        if (dialogshowing) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProcess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (workFlowPresenter != null) {
            workFlowPresenter.getWorkFlow(token, workflow_content_id,proc_type_id);
        }
    }

    public void showVideoPreview(List<LocalMedia> data) {
        videoPreviewDialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(context, R.layout.videopreview, null);
        videoPreviewDialog.setView(view);

        RecyclerView videos = view.findViewById(R.id.videos);
        initVideoPreview(videos, data);

        TextView cancel = view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPreviewDialog.dismiss();
            }
        });

        videoPreviewDialog.show();

    }

    public void initVideoPreview(RecyclerView view, List<LocalMedia> data) {

        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        view.setLayoutManager(manager2);
        GridImageAdapter adapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
            }
        }, data);
        adapter.setShowDel(false);
        adapter.setSelectMax(data.size());
        view.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(WorkFlowActivity.this, VideoPlay2Activity.class);
                intent.putExtra("path", data.get(position).getPath());
                startActivity(intent);
            }
        });
    }

    public void showReEditDialog(int position) {
        View rootView = View.inflate(this, R.layout.alertdialog, null);
        reEditDialog = DialogUIUtils.showCustomAlert(this, rootView);

        TextView title = rootView.findViewById(R.id.title);
        title.setText("驳回原因");
        EditText reason = rootView.findViewById(R.id.et_feedback);
        reason.setHint("请输入驳回原因");

        TextView cancel = rootView.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(reEditDialog);
            }
        });

        TextView confirm = rootView.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reason.getText().toString().trim().length() > 0) {

                    dialog.msg = "驳回申请提交中";

                    workFlowPresenter.HRRejection(token, workflow_content_id,
                            groupData.get(position).getW_pot_id(),
                            groupData.get(position).getProcTypeId(),
                            reason.getText().toString().trim());
                    DialogUIUtils.dismiss(reEditDialog);
                } else {
                    Toast.makeText(context, "请输入驳回原因", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reEditDialog.show();
    }

    public void showRefuseDialog() {
        View rootView = View.inflate(this, R.layout.alertdialog, null);
        reEditDialog = DialogUIUtils.showCustomAlert(this, rootView);

        TextView title = rootView.findViewById(R.id.title);
        title.setText("拒件原因");
        EditText reason = rootView.findViewById(R.id.et_feedback);
        reason.setHint("请输入拒件原因");

        TextView cancel = rootView.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(reEditDialog);
            }
        });

        TextView confirm = rootView.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reason.getText().toString().trim().length() > 0) {

                    int wk_con_id = Integer.parseInt(workflow_content_id);
                    int wk_pot_id = Integer.parseInt(getWk_Pot_Id(groupData));

                    workFlowPresenter.endWorkFlow(token, wk_con_id, wk_pot_id, reason.getText().toString().trim(),proc_type_id);
                    DialogUIUtils.dismiss(reEditDialog);
                } else {
                    Toast.makeText(context, "请输入拒件原因", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reEditDialog.show();
    }

    private String getWk_Pot_Id(List<WorkFlowGroupData> data){
        String wk_pot_id = "0";
        for (WorkFlowGroupData d :data){
            if (d.getDate().length() > 0 && d.getDate().trim().equals("待处理")){
                wk_pot_id = d.getW_pot_id();
            }
        }

        return wk_pot_id;
    }

    @OnClick({R.id.ig_back, R.id.ig_refuse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.ig_refuse:

                if (QuestState.isQuestDone(groupData.get(0).getStatus())){
                    showFailureMsg("表单已结束");
                    return;
                }

                //表单不是已办状态，执行拒件操作，或者恢复单据操作
                if(QuestState.isRefused(workflow_state)){
                    workFlowPresenter.recoverWorkflow(token,Integer.parseInt(workflow_content_id),proc_type_id);
                    dialog.msg = "恢复单据操作提交中";
                }else{
                    dialog.msg = "拒件操作提交中";
                    showRefuseDialog();
                }
                break;
        }
    }

    public void downloadContract(String url){
        if (url == null || url.equals("")){
            showFailureMsg("查看合同异常，请重试");
            return;
        }

        Intent intent = new Intent(this,Contract.class);
        intent.putExtra("url",url );
        context.startActivity(intent);
    }
}
