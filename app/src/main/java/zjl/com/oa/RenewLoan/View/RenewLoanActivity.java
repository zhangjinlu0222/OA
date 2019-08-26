package zjl.com.oa.RenewLoan.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.dou361.dialogui.listener.DialogUIDateTimeSaveListener;
import com.dou361.dialogui.widget.DateSelectorWheelView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.FormListsAdapter;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.CustomView.ActionSheetDialog;
import zjl.com.oa.CustomView.MyListView;
import zjl.com.oa.Login.View.LoginActivity;
import zjl.com.oa.R;
import zjl.com.oa.RenewLoan.Model.RLPresenterImpl;
import zjl.com.oa.RenewLoan.Presenter.IRLPresenter;
import zjl.com.oa.RenewLoan.Presenter.IRLView;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Utils.LocalMediaUtil;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class RenewLoanActivity extends BaseActivity implements IRLView {


    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.listview)
    MyListView listview;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.renewloan_rl_upload)
    RelativeLayout rlrenewloanRlUpload;
    @Bind(R.id.renewloan_btn_upload)
    Button renewloanBtnUpload;

    private IRLPresenter iRLPresenter;
    private int workflow_content_id;
    private int wk_point_id;
    private String workflow_name;
    private String token;

    private List<FormResponse.Result.Form> formLists = new ArrayList<>();
    private FormListsAdapter formListsAdapter;

    private List<LocalMedia> selectList = new ArrayList<>();
    private BroadcastReceiver receiver;
    private BuildBean submitDialog;

    public boolean reportFlag = false;
    public boolean photoFlag = false;
    private String way;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewloan);
        ButterKnife.bind(this);

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        iRLPresenter = new RLPresenterImpl(this);
        workflow_content_id = Integer.parseInt(getIntent().getStringExtra("workflow_content_id"));
        wk_point_id = Integer.parseInt(getIntent().getStringExtra("wk_point_id"));
        workflow_name = getIntent().getStringExtra("workflow_name");
        token = UserInfo.getInstance(this).getUserInfo(UserInfo.TOKEN);

        if (wk_point_id == 27){
            String autoUpdate = UserInfo.getInstance(context).getUserInfo(UserInfo.AUTOUPDATE);
            if (autoUpdate != null && autoUpdate.equals("1")){
                saveOperation(Integer.toString(wk_point_id));
            }
        }

        type = getIntent().getStringExtra("type");

        way = getIntent().getStringExtra("way");

        if (workflow_name != null && workflow_name.length() > 0) {
            tvTitle.setText(workflow_name);
        }

        if (workflow_name.contains("定额")) {
            rlrenewloanRlUpload.setVisibility(View.VISIBLE);
            if (type != null && type.equals("bohui")){
                renewloanBtnUpload.setEnabled(false);
                renewloanBtnUpload.setBackgroundColor(Color.LTGRAY);
            }else if ("抵押".equals(way) && "最终定额".equals(workflow_name)){
                //抵押的最终定额不让重新上传
                renewloanBtnUpload.setEnabled(false);
                renewloanBtnUpload.setBackgroundColor(Color.LTGRAY);
            }else{
                renewloanBtnUpload.setEnabled(true);
                renewloanBtnUpload.setBackgroundResource(R.mipmap.info_bg_next);
            }
        } else {
            rlrenewloanRlUpload.setVisibility(View.GONE);
        }
        iRLPresenter.Form(token, workflow_content_id, wk_point_id);

        formListsAdapter = new FormListsAdapter(formLists, RenewLoanActivity.this, listview);
        listview.setAdapter(formListsAdapter);
        formListsAdapter.setOnItemClickListener(new FormListsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.time:
                        showDataPicker(position);
                        break;
                }
            }
        });
        formListsAdapter.notifyDataSetChanged();

        submitDialog = DialogUIUtils.showLoading(this, "提交中", true,
                false, false, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new SelectListUpdate();
        IntentFilter intentFilter = new IntentFilter("picUpdate");
        this.registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void showDataPicker(int position) {
        DialogUIUtils.showDatePick(RenewLoanActivity.this, Gravity.BOTTOM,
                "合同日期", System.currentTimeMillis() + 60000,
                DateSelectorWheelView.TYPE_YYYYMMDD, 0,
                new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {
                        formLists.get(position).setData_con(selectedDate);
                        formListsAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    @OnClick({R.id.ig_back, R.id.btn_next, R.id.renewloan_btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.finish();
                break;
            case R.id.btn_next:
                submitMsg();
                break;
            case R.id.renewloan_btn_upload:
                    ReUploadReportOrPhoto(reportFlag,photoFlag);
                break;
        }
    }
    private void ReUploadReportOrPhoto(boolean reportFlag, boolean photoFlag) {

        if (!netConnected){
            showNetError();
        }else if (isFastDoubleClick(R.id.evaluationBtnRefuse)){
            return;
        }else {

            submitDialog.msg = "提交中";
            iRLPresenter.SureAmountReturn(token, Integer.toString(workflow_content_id),
                    Integer.toString( wk_point_id),
                    getReUploadType(reportFlag, photoFlag));
        }
    }

    private String getReUploadType(boolean reportFlag,boolean photoFlag){
        if (reportFlag && photoFlag){
            return "3";
        }
        if (reportFlag && !photoFlag){
            return "1";
        }
        if (!reportFlag && photoFlag){
            return "2";
        }
        return "0";
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
        formLists.clear();
        formLists.addAll(result.getFormList());
        formListsAdapter.notifyDataSetChanged();
    }

    @Override
    public String getData_Con(String arg) {
        for (FormResponse.Result.Form form : formLists) {
            if (arg != null && arg.equals(form.getControl_title().toString().trim())) {
                return form.getData_con().toString().trim();
            }
        }
        return null;
    }
    public String getData_Con(String arg ,int control_style) {
        for (FormResponse.Result.Form form : formLists) {
            if (arg != null && arg.equals(form.getControl_title().toString().trim()) &&
                    (form.getControl_style() == control_style)) {
                return form.getData_con().toString().trim();
            }
        }
        return null;
    }

    private void submitMsg() {
        if (iRLPresenter == null) {
            return;
        }
        switch (wk_point_id) {
            case 1:
                HashMap<String ,Object > map = new HashMap<>();
                map.put("token",token );
                map.put("w_con_id", workflow_content_id);
                map.put("w_pot_id", wk_point_id);

                for (int i=0;i< formLists.size();i++){
                    String submit_field = formLists.get(i).getSubmit_field();

                    if (submit_field != null && submit_field.length() >0){
                        String value = formLists.get(i).getData_con();
                        map.put(submit_field,value);
                    }
                }

                if (iRLPresenter != null){
                    iRLPresenter.Coming(map);
                }

                break;
            case 3:
                if (iRLPresenter != null) {
                    iRLPresenter.FirstSureAmount(
                            token,
                            Integer.toString(workflow_content_id),
                            Integer.toString(wk_point_id),
                            getData_Con("初步定额"),
                            getData_Con("备注",12));
                }
                break;
            case 4:
                if (iRLPresenter != null) {
                    iRLPresenter.CarPhoto(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            token,
                            getData_Con("备注"),
                            workflow_content_id,
                            wk_point_id,
                            selectList,
                            "1",
                            getData_Con("借款周期"));
                }
                break;
            case 5:
                HashMap<String ,Object > map1 = new HashMap<>();
                map1.put("token",token );
                map1.put("w_con_id", workflow_content_id);
                map1.put("w_pot_id", wk_point_id);

                for (int i=0;i< formLists.size();i++){
                    String submit_field = formLists.get(i).getSubmit_field();

                    if (submit_field != null && submit_field.length() >0){
                        String value = formLists.get(i).getData_con();
                        map1.put(submit_field,value);
                    }
                }

                if (iRLPresenter != null){
                    iRLPresenter.Coming(map1);
                }

                break;
            case 6:
                if (iRLPresenter != null) {
                    iRLPresenter.FirstSureAmount(
                            token,
                            Integer.toString(workflow_content_id),
                            Integer.toString(wk_point_id),
                            getData_Con("初步定额"),
                            getData_Con("备注",12));
                }
                break;
            case 10:
                if (iRLPresenter != null) {
                    iRLPresenter.SureAmount(
                            token,
                            Integer.toString(workflow_content_id),
                            Integer.toString(wk_point_id),
                            getData_Con("评估定额"),
                            getData_Con("担保价"),
                            getData_Con("降额意见"),
                            getData_Con("备注"));
                }
                break;
            case 23:
                if (iRLPresenter != null) {
                    iRLPresenter.InfoCheckRefinance(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            token,
                            workflow_content_id,
                            wk_point_id,
                            getData_Con("人法查询"),
                            getData_Con("违章查询"),
                            getData_Con("保险查询"),
                            getData_Con("备注"),
                            selectList);
                }
                break;
            case 24:
                if (iRLPresenter != null) {
                    iRLPresenter.AssessRefinance(
                            token,
                            workflow_content_id,
                            wk_point_id,
                            getData_Con("档案信息"),
                            getData_Con("降额意见"),
                            getData_Con("备注")
                    );
                }
                break;
            case 25:
                if (iRLPresenter != null) {
                    iRLPresenter.AuditRefinance(
                            token,
                            workflow_content_id,
                            wk_point_id,
                            getData_Con("备注"));
                }
                break;
            case 26:
                if (iRLPresenter != null) {
                    iRLPresenter.UploadCarPhoto(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            token,
                            workflow_content_id,
                            getData_Con("备注"),
                            wk_point_id,
                            "10",
                            selectList);
                }
                break;
            case 27:
                if (iRLPresenter != null) {
                    iRLPresenter.ContractDetail(
                            token,
                            workflow_content_id,
                            wk_point_id,
                            getData_Con("备注"),
                            getData_Con("金额"),
                            getData_Con("利息"),
                            getData_Con("期限"),
                            getData_Con("展期费"),
                            getData_Con("服务费"),
                            getData_Con("保险"),
                            getData_Con("违章"),
                            getData_Con("合同日期")
                    );
                }
                break;
            case 30:
                if (iRLPresenter != null) {
                    iRLPresenter.InputInfo(
                            token,
                            workflow_content_id,
                            wk_point_id,
                            getData_Con("客户姓名"),
                            getData_Con("身份证号"),
                            getData_Con("手机号"),
                            getData_Con("现住址"),
                            getData_Con("开户银行"),
                            getData_Con("银行账号"),
                            getData_Con("借款用途"),
                            getData_Con("车辆牌照"),
                            getData_Con("汽车登记证"),
                            getData_Con("发动机号"),
                            getData_Con("车架号"),
                            ""
                    );
                }
                break;
            case 31:
                if (iRLPresenter != null) {
                    iRLPresenter.ApplyforRefinance(
                            token,
                            Integer.toString(workflow_content_id),
                            Integer.toString(wk_point_id),
                            getData_Con("期限"),
                            getData_Con("备注")
                    );
                }
                break;
        }
    }

    public void onClickPic(int position) {
        if (selectList.size() > 0) {
            LocalMedia media = selectList.get(position);
            String pictureType = media.getPictureType();
            int mediaType = PictureMimeType.pictureToVideo(pictureType);
            switch (mediaType) {
                case 1:
                    // 预览图片 可自定长按保存路径
                    PictureSelector.create(RenewLoanActivity.this).externalPicturePreview(position, selectList);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(RenewLoanActivity.this).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(RenewLoanActivity.this).externalPictureAudio(media.getPath());
                    break;
            }
        }
    }

    public void showSheetDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog.ActionSheetBuilder(RenewLoanActivity.this, R.style.ActionSheetDialogBase)
                .setItems(new CharSequence[]{"选择视频", "选择照片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 1:
                                getFileWithType(PictureMimeType.ofImage());
                                break;
                            case 0:
                                getFileWithType(PictureMimeType.ofVideo());
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setTitle("上传")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RenewLoanActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(true)
                .create();
        dialog.show();
    }

    private void getFileWithType(int type) {

        PictureSelector.create(RenewLoanActivity.this)
                .openGallery(type)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .maxSelectNum(Constant.MAXCOUNT)// 最大图片选择数量
                .minSelectNum(Constant.MINCOUNT)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .compress(false)// 是否压缩
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .selectionMedia(selectList)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果
                    selectList.clear();
                    selectList.addAll(PictureSelector.obtainMultipleResult(data));
                    addImgs(selectList);
                    formListsAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private void addImgs(List<LocalMedia> arg) {

        List<String> imgs = new ArrayList<>();
        for (LocalMedia media : arg) {
            if (media.getCompressPath() != null && media.getCompressPath().length() > 0) {
                imgs.add(media.getCompressPath());
            } else {
                imgs.add(media.getPath());
            }
        }

        for (FormResponse.Result.Form form : formLists) {
            if (form.getControl_title().toString().trim().contains("上传照片")) {
                form.setImgs(imgs);
            }
        }
    }

    @Override
    public void relogin() {
        Toast.makeText(this, Constant.Action_Login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void toMainActivity() {
        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public void showFailureMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isUploadTypeAdd() {
        if ("bohui".equals(type) && uploadType) {
            return true;
        }
        return false;
    }

    public void setUploadType(boolean isChecked) {
        this.uploadType = isChecked;
    }

    public String getInType() {
        if (type != null) {
            return type;
        }
        return "";
    }

    public void showQuotaImage(int parentPosition, int itemPosition) {

        if (parentPosition < 0) {
            return;
        }
        if (itemPosition < 0) {
            return;
        }

        selectList.clear();

        List<String> sList = new ArrayList<>();
        sList.addAll(formLists.get(parentPosition).getImgs());
        for (String img : sList) {
            LocalMedia media = new LocalMedia();
            media.setPath(img);
            media.setPictureType(LocalMediaUtil.mediaType(img));
            selectList.add(media);
        }

        LocalMedia media = selectList.get(itemPosition);
        String pictureType = media.getPictureType();
        int mediaType = PictureMimeType.pictureToVideo(pictureType);
        switch (mediaType) {
            case 1:
                // 预览图片 可自定长按保存路径
                PictureSelector.create(RenewLoanActivity.this).externalPicturePreview(itemPosition, selectList);
                break;
            case 2:
                // 预览视频
                PictureSelector.create(RenewLoanActivity.this).externalPictureVideo(media.getPath());
                break;
            case 3:
                // 预览音频
                PictureSelector.create(RenewLoanActivity.this).externalPictureAudio(media.getPath());
                break;
        }
    }

    private class SelectListUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int delPos = intent.getIntExtra("deleteIndex", -1);
                if (delPos != -1 && delPos <= selectList.size()) {
                    selectList.remove(delPos);
                }
            }
        }
    }
}
