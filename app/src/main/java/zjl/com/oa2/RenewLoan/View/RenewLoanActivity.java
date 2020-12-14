package zjl.com.oa2.RenewLoan.View;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPObject;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.dou361.dialogui.listener.DialogUIDateTimeSaveListener;
import com.dou361.dialogui.widget.DateSelectorWheelView;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Adapter.FormListsAdapter;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UploadCarPhotosType;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.CustomView.ActionSheetDialog;
import zjl.com.oa2.CustomView.MyListView;
import zjl.com.oa2.InformationCheck.View.InformationCheck;
import zjl.com.oa2.LoanDetail.View.LoanDetail;
import zjl.com.oa2.Login.View.LoginActivity;
import zjl.com.oa2.MapView.View.MapView;
import zjl.com.oa2.R;
import zjl.com.oa2.RenewLoan.Model.RLPresenterImpl;
import zjl.com.oa2.RenewLoan.Presenter.IRLPresenter;
import zjl.com.oa2.RenewLoan.Presenter.IRLView;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.ManagersResponse;
import zjl.com.oa2.Response.SearchResponse;
import zjl.com.oa2.RiskSearch.View.RiskSearch;
import zjl.com.oa2.Utils.LocalMediaUtil;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

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

    //业务类型id，区别华融车贷和车励享车贷
    private String proc_type_id;

    private List<FormResponse.Result.Form> formLists = new ArrayList<>();
    private FormListsAdapter formListsAdapter;

    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> zheshubiao = new ArrayList<>();
    private List<LocalMedia> videos = new ArrayList<>();
    private List<LocalMedia> photos = new ArrayList<>();
    private BroadcastReceiver receiver;
    private BuildBean submitDialog;

    public boolean reportFlag = false;
    public boolean photoFlag = false;
    private String way;

    //实地考察节点图片类型，折数表或者实地图片
    public String photoType;
    private static final int CHOOSE_ZHESHUBIAO = 10001;
    private static final int CHOOSE_PHOTO = 10002;
    //上传视频
    private static final int CHOOSE_VIDEO = 10003;


    private String sellerNames = "";
    private List<ManagersResponse.Result.Seller> sellers = new ArrayList<>();

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
        proc_type_id = getIntent().getStringExtra("proc_type_id");
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

        //华融信贷定额节点（37）除外,OA2 定额节点（10）除外
        if (workflow_name.contains("定额") && (37!=wk_point_id)) {
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
        } else if (workflow_name.contains("反馈")) {//初步反馈与业务反馈节点反馈操作
            rlrenewloanRlUpload.setVisibility(View.VISIBLE);
            renewloanBtnUpload.setText("反馈");
            if (type != null && type.equals("bohui")){
                renewloanBtnUpload.setEnabled(false);
                renewloanBtnUpload.setBackgroundColor(Color.LTGRAY);
            }else{
                renewloanBtnUpload.setEnabled(true);
                renewloanBtnUpload.setBackgroundResource(R.mipmap.info_bg_next);
            }
        } else if (workflow_name.contains("信息核查")) {//初步反馈与业务反馈节点反馈操作
            rlrenewloanRlUpload.setVisibility(View.VISIBLE);
            renewloanBtnUpload.setText("拒件");
            if (type != null && type.equals("bohui")){
                renewloanBtnUpload.setEnabled(false);
                renewloanBtnUpload.setBackgroundColor(Color.LTGRAY);
            }else{
                renewloanBtnUpload.setEnabled(true);
                renewloanBtnUpload.setBackgroundResource(R.mipmap.info_bg_next);
            }
        } else {
            rlrenewloanRlUpload.setVisibility(View.GONE);
        }
        iRLPresenter.Form(token, workflow_content_id, wk_point_id,proc_type_id);

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
                switch (wk_point_id){
                    case 3://信息核查
                    case 7: //初步反馈
                    case 11: //业务反馈
                        submitMsgAlert(wk_point_id);
                        break;
                    default:
                        ReUploadReportOrPhoto(reportFlag,photoFlag);
                }
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
        //这里对来访做判断，如果来访中的客户来源是自有客户，或者业务类型是抵押或华融信贷则不让输入同行手机号
        List<FormResponse.Result.Form> forms = result.getFormList();
        if (workflow_name.contains("来访")){
            boolean flag = false;
            for (FormResponse.Result.Form form:forms){
                if (form.getControl_title().equals("客户来源")){
                    if (form.getData_con().equals("1") || form.getData_con().equals("")){
                        flag = true;
                    }
                }else if (form.getControl_title().equals("业务类型")){
                    if (form.getData_con().equals("") || form.getData_con().equals("抵押") || form.getData_con().equals("华融信贷")){
                        flag = true;
                    }
                }
            }
            for (FormResponse.Result.Form form:forms){
                if (form.getControl_title().equals("同行手机号")){
                    if (flag){
                        form.setRead_only(true);
                        form.setPlace_holder("无");
                    }else{
                        form.setRead_only(false);
                        form.setPlace_holder("请输入手机号");
                    }
                }
            }
        }
        formLists.addAll(forms);
        formListsAdapter.notifyDataSetChanged();


        //加载form表单数据成功后，如果进入的是来访则去获取经理人信息
        if (wk_point_id == 1){
            HashMap<String ,Object> map = new HashMap<>();
            iRLPresenter.ManagerList(map);
        }
    }

    @Override
    public String getData_Con(String arg) {
        for (FormResponse.Result.Form form : formLists) {
            if (arg != null && arg.equals(form.getControl_title().trim())) {
                //业务反馈节点，还款方式内容需要转换
                if (wk_point_id == 11){
                    return form.getData_con().trim().equals("")?"0":form.getData_con().trim();
                }
                return form.getData_con().trim();
            }
        }
        return null;
    }
    public String getData_Con(String arg ,int control_style) {
        for (FormResponse.Result.Form form : formLists) {
            if (arg != null && arg.equals(form.getControl_title().trim()) &&
                    (form.getControl_style() == control_style)) {
                return form.getData_con().trim();
            }
        }
        return null;
    }

    private void submitMsg() {
        if (iRLPresenter == null) {
            return;
        }
        HashMap<String ,Object > map = new HashMap<>();
        switch (wk_point_id) {
            case 1:
                map.clear();
                map.put("token",token );
                map.put("w_con_id", workflow_content_id);
                map.put("w_pot_id", wk_point_id);

                for (int i=0;i< formLists.size();i++){
                    String submit_field = formLists.get(i).getSubmit_field();

                    if (submit_field != null && submit_field.length() >0){
                        if (submit_field.equals("user_id")){
                            String name = formLists.get(i).getData_con();
                            String user_id = getSellerId(name);
                            map.put(submit_field,user_id);
                        }else{
                            String value = formLists.get(i).getData_con();
                            map.put(submit_field,value);
                        }
                    }
                }

                if (iRLPresenter != null){
                    iRLPresenter.Coming(map);
                }

                break;
            case 3:
                map.clear();
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
                    iRLPresenter.InfoCheck(request_start_flag,!uploadType?UPLOAD_TYPE_NORMAL:UPLOAD_TYPE_ADD,map,selectList);
                }

                break;
            case 4:
                map.clear();
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
                    iRLPresenter.CarPhoto(request_start_flag,!uploadType?UPLOAD_TYPE_NORMAL:UPLOAD_TYPE_ADD,map,"1",selectList);
                }
                break;
            case 5:
                map.clear();
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
                    iRLPresenter.PleDgeAssess(request_start_flag,!uploadType?UPLOAD_TYPE_NORMAL:UPLOAD_TYPE_ADD,map,selectList);
                }
                break;
            case 6:
                map.clear();
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
                    iRLPresenter.FirstSureAmount(map);
                }
                break;
            case 7:
                map.clear();
                map.put("token",token );
                map.put("w_con_id", workflow_content_id);
                map.put("w_pot_id", wk_point_id);
                map.put("remark", "");

                if (iRLPresenter != null){
                    iRLPresenter.FirstFeedback(map);
                }
                break;
            case 8:
                if (zheshubiao.size() < 1 && !getInType().equals("bohui")){
                    showFailureMsg("请选择折数表");
                    return;
                }
                if (photos.size() < 1 && !getInType().equals("bohui")){
                    showFailureMsg("请选择实地图片");
                    return;
                }
                selectList.addAll(zheshubiao);
                selectList.addAll(photos);

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.UploadCarPhoto(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,
                            UploadCarPhotosType.getType_id(wk_point_id)+"",
                            selectList);
                }
                break;
            case 10:
                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.SureAmount(map);
                }
                break;
            case 11:
                map.clear();

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
                    iRLPresenter.BusFeedback(map);
                }
                break;
            case 13:
                map.clear();
                map.put("token",token);
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
                    iRLPresenter.InformSigned(map);
                }
                break;
            case 15:
                if (selectList.size() < 1 && !getInType().equals("bohui")){
                    showFailureMsg("请选择照片");
                    return;
                }
                //签约的时候视频可以不提交
//                if (videos.size() < 1){
//                    showFailureMsg("请选择视频");
//                    return;
//                }
                selectList.addAll(videos);
                int type_id = new UploadCarPhotosType().getType_id(wk_point_id);

                map.clear();
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

                iRLPresenter.UploadCarPhoto(
                        request_start_flag,
                        !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                        map,
                        type_id+"",
                        selectList);
                break;
            case 17:
                if (selectList.size() < 1 && !getInType().equals("bohui")){
                    showFailureMsg("请选择照片");
                    return;
                }
                selectList.addAll(videos);
                type_id = new UploadCarPhotosType().getType_id(wk_point_id);

                map.clear();
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
                iRLPresenter.UploadCarPhoto(
                        request_start_flag,
                        !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                        map,
                        type_id+"",
                        selectList);
                break;
            case 18:
                if (selectList.size() < 1 && !getInType().equals("bohui")){
                showFailureMsg("请选择照片");
                return;
            }
            type_id = new UploadCarPhotosType().getType_id(wk_point_id);

            map.clear();
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
            iRLPresenter.UploadCarPhoto(
                    request_start_flag,
                    !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                    map,
                    type_id+"",
                    selectList);
            break;
            case 20:
                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.loanApplication(map);
                }
                break;
            case 21:
                if (selectList.size() < 1 && (getInType().length() > 0 && !getInType().equals("bohui"))){
                    showFailureMsg("请选择照片");
                    return;
                }
                type_id = new UploadCarPhotosType().getType_id(wk_point_id);
                map.clear();
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
                iRLPresenter.UploadCarPhoto(
                        request_start_flag,
                        !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                        map,
                        type_id+"",
                        selectList);
                break;
            case 22://放款结束
                map.clear();
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
                    iRLPresenter.FinishFlow(map);
                }
                break;
            case 23:
                map.clear();
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
                if (iRLPresenter != null) {
                    iRLPresenter.InfoCheckRefinance(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,
                            selectList);
                }
                break;
            case 24:
                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.AssessRefinance(map);
                }
                break;
            case 25:
                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.AuditRefinance(map);
                }
                break;
            case 26:
                map.clear();
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
                iRLPresenter.UploadCarPhoto(
                        request_start_flag,
                        !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                        map,
                        "10",
                        selectList);
                break;
            case 27:
                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.ContractDetail(map);
                }
                break;
            case 30:
                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.InputInfo(map);
                }
                break;
            case 31:
                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.ApplyforRefinance(map);
                }
                break;
            case 32:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.UploadCarPhoto(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,
                            "12",
                            selectList);
                }
                break;
            case 33:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.HRInterview(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,selectList);
                }
                break;
            case 34:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.HRUploadData(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,selectList);
                }
                break;
            case 35:

                map.clear();
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

                selectList.clear();

                if (iRLPresenter != null) {
                    iRLPresenter.HRUploadData(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,selectList);
                }
                break;
            case 36:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.HRUploadData(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,selectList);
                }
                break;
            case 37:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.HRSureAmount(map);
                }
                break;

            case 38:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.HRUploadData(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,selectList);
                }
                break;
            case 39:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.HRUploadData(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,selectList);
                }
                break;
            case 41:

                map.clear();
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

                if (iRLPresenter != null) {
                    iRLPresenter.TransferOwnership(
                            request_start_flag,
                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
                            map,selectList);
                }
                break;
        }
    }

    public void onClickPic(int position,int parentPosition) {
        List<LocalMedia> formDataSource;
        switch (photoType){
            case "上传折数表":
                formDataSource = zheshubiao;
                break;
            case "上传实地图片":
                formDataSource = photos;
                break;
            case "上传视频":
                formDataSource = videos;
                break;
            default:
                formDataSource = selectList;

        }
        if (formDataSource.size() > 0){
            LocalMedia media = formDataSource.get(position);
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

    /**
     *
     * @param controlStyle
     * 3表示图片选择
     * 4表示视频选择
     * 5表示混合选择
     * 6表示图片显示
     */
    public void showSheetDialog(int controlStyle) {
        ActionSheetDialog dialog = null;
        ActionSheetDialog.ActionSheetBuilder builder = new ActionSheetDialog.ActionSheetBuilder(RenewLoanActivity.this, R.style.ActionSheetDialogBase)
                .setTitle("上传")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RenewLoanActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(true);

        switch (controlStyle){
            case 3:
                dialog = builder.setItems(new CharSequence[]{"选择照片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                getFileWithType(PictureMimeType.ofImage(),photoType);
                                break;
                        }
                        dialog.dismiss();
                    }
                }).create();
                break;
            case 4:
                dialog = builder.setItems(new CharSequence[]{"选择视频"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            getFileWithType(PictureMimeType.ofVideo(),photoType);
                            break;
                    }
                    dialog.dismiss();
                }
                }).create();
                break;
            case 5:
            case 6:
                dialog = builder.setItems(new CharSequence[]{"选择视频", "选择照片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 1:
                                getFileWithType(PictureMimeType.ofImage(),photoType);
                                break;
                            case 0:
                                getFileWithType(PictureMimeType.ofVideo(),photoType);
                                break;
                        }
                        dialog.dismiss();
                    }
                }).create();
                break;
        }
        dialog.show();
    }

    private void getFileWithType(int type,String photoType) {
        PictureSelectionModel model =
        PictureSelector.create(RenewLoanActivity.this)
                .openGallery(type)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .minSelectNum(Constant.MINCOUNT)// 最小选择数量;
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .compress(false)// 是否压缩
                .glideOverride(160, 160);// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度

        //结果回调onActivityResult code
        if ("上传折数表".equals(photoType)){
            model.selectionMedia(zheshubiao)// 是否传入已选图片
                .maxSelectNum(Constant.MINCOUNT)
                .forResult(CHOOSE_ZHESHUBIAO);
        }else if ("上传实地图片".equals(photoType)){
            model.selectionMedia(photos)// 是否传入已选图片
                    .maxSelectNum(Constant.MAXCOUNT)
                    .forResult(CHOOSE_PHOTO);
        }else if ("上传视频".equals(photoType)){
            model.selectionMedia(videos)// 是否传入已选图片
                    .maxSelectNum(Constant.MAXCOUNT)
                    .forResult(CHOOSE_VIDEO);
        }else{
            model.selectionMedia(selectList)// 是否传入已选图片
             .maxSelectNum(Constant.MAXCOUNT)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
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
                case CHOOSE_ZHESHUBIAO:
                    // 图片选择结果
                    zheshubiao.clear();
                    zheshubiao.addAll(PictureSelector.obtainMultipleResult(data));
                    addImgs(zheshubiao);
                    formListsAdapter.notifyDataSetChanged();
                    break;
                case CHOOSE_PHOTO:
                    // 图片选择结果
                    photos.clear();
                    photos.addAll(PictureSelector.obtainMultipleResult(data));
                    addImgs(photos);
                    formListsAdapter.notifyDataSetChanged();
                    break;
                case CHOOSE_VIDEO:
                    // 图片选择结果
                    videos.clear();
                    videos.addAll(PictureSelector.obtainMultipleResult(data));
                    addVideos(videos);
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
            if (form.getControl_title().trim().contains("上传照片")) {
                form.setImgs(imgs);
            }else if (wk_point_id == 5){ //评估报告节点
                form.setImgs(imgs);
            }else if (photoType != null && form.getControl_title().trim().contains(photoType)){
                form.setImgs(imgs);
            }
        }
    }

    private void addVideos(List<LocalMedia> arg) {

        List<String> imgs = new ArrayList<>();
        for (LocalMedia media : arg) {
            if (media.getCompressPath() != null && media.getCompressPath().length() > 0) {
                imgs.add(media.getCompressPath());
            } else {
                imgs.add(media.getPath());
            }
        }

        for (int i = 0 ; i < formLists.size() ; i++) {
            FormResponse.Result.Form form  = formLists.get(i);
            if (photoType != null && form.getControl_title().trim().contains(photoType)){
                form.setImgs(imgs);
            }
        }
        formListsAdapter.notifyDataSetChanged();
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
                String title = intent.getStringExtra("title");
                switch (title){
                    case "上传折数表":
                        if (delPos >= 0 && delPos < zheshubiao.size()) {
                            zheshubiao.remove(delPos);
                            removeFormImg(title,delPos);
                        }
                        break;
                    case "上传实地图片":
                        if (delPos >= 0 && delPos < photos.size()) {
                            photos.remove(delPos);
                            removeFormImg(title,delPos);
                        }
                        break;
                    case "上传视频":
                        if (delPos >= 0 && delPos < videos.size()) {
                            videos.remove(delPos);
                            removeFormImg(title,delPos);
                        }
                        break;
                    default:
                        if (delPos >= 0 && delPos < selectList.size()) {
                            selectList.remove(delPos);
                            removeFormImg(title,delPos);
                        }
                }
            }
        }
    }

    private void removeFormImg(String title,int delPos){
        for(FormResponse.Result.Form form:formListsAdapter.formLists){
            if (title .equals(form.getControl_title())){

                form.getImgs().remove(delPos);
                formListsAdapter.notifyDataSetChanged();
            }
        }
    }

    public void submitMsgAlert(int wk_point_id) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(context, R.layout.alertdialog, null);
        dialog.setView(view);

        EditText et = view.findViewById(R.id.et_feedback);
        TextView cancel = view.findViewById(R.id.tv_cancel);
        TextView confirm = view.findViewById(R.id.tv_confirm);
        TextView title = view.findViewById(R.id.title);

        switch (wk_point_id){
            case 3:
                title.setText("确认拒件");
                et.setHint("拒件原因");
                break;
            default:
                title.setText("反馈说明");
                et.setHint("请输入反馈说明");
                break;
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equals(et.getText().toString().trim())) {
                    showFailureMsg("请输入反馈信息");
                } else {

                    if (!netConnected) {
                        showNetError();
                    } else if (isFastDoubleClick(v.getId())) {
                        return;
                    } else {
                        switch (wk_point_id){
                            case 3:
                                iRLPresenter.endWorkFlow(token, workflow_content_id, wk_point_id,
                                        et.getText().toString().trim(),proc_type_id);
                                break;
                            case 7:
                                HashMap<String ,Object> feedbackInfo = new HashMap<>();
                                feedbackInfo.put("token",token );
                                feedbackInfo.put("w_con_id", workflow_content_id);
                                feedbackInfo.put("w_pot_id", wk_point_id);

                                for (int i=0;i< formLists.size();i++){
                                    String submit_field = formLists.get(i).getSubmit_field();

                                    if (submit_field != null && submit_field.length() >0){
                                        String value = formLists.get(i).getData_con();
                                        feedbackInfo.put(submit_field,value);
                                    }
                                }

                                feedbackInfo.put("remark", et.getText().toString());

                                if (iRLPresenter != null){
                                    iRLPresenter.FirstFeedback(feedbackInfo);
                                }

                                break;
                            case 11:

                                HashMap<String ,Object> map = new HashMap<>();
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

                                map.put("remark", et.getText().toString());

                                if (iRLPresenter != null){
                                    iRLPresenter.BusFeedback(map);
                                }

//                                iRLPresenter.BusFeedback(token,
//                                        Integer.toString(workflow_content_id) ,
//                                        Integer.toString(wk_point_id) ,
//                                        Integer.parseInt(getData_Con("借款期限")) ,
//                                        getData_Con("借款利率"),
//                                        getData_Con("还款方式"),
//                                        et.getText().toString());
                                break;
                        }
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void updateFormItemContent(){

        if (!workflow_name.equals("业务反馈")){
            return;
        }

        double totalCount = 0.00;

        for (FormResponse.Result.Form form : formLists) {

            String title = form.getControl_title();

            String frontColor = form.getFront_color();

//            if (title != null && (
//                    title.contains("停车费") || title.contains("GPS费")||title.contains("首扣利息")||
//                            title.contains("抵押登记") || title.contains("评估核档")||
//                            title.contains("保证金") || title.contains("违章押金")||
//                            title.contains("保险押金") || title.contains("审车押金")
//            ))

            if (frontColor != null && frontColor.equals("1"))
            {
                String value = form.getData_con();
                if (value == null || value.equals("")){
                    value="0";
                }
                totalCount = totalCount + Double.parseDouble(value);
            }

            if (title != null && title.equals("首扣")){

                form.setData_con(totalCount+"");

                formListsAdapter.notifyDataSetChanged();
            }

        }
    }

    public void disableColleaguePhone(String value) {
        if (!workflow_name.contains("来访")) {
            return;
        }

        boolean flag1 = false;
        boolean flag2 = false;

        for (FormResponse.Result.Form form : formLists) {

            String title = form.getControl_title();

            if (title != null && title.equals("客户来源") && form.getData_con().equals("自有客户")) {
                flag1 = true;
            }

            if (title != null && title.equals("业务类型") && (form.getData_con().equals("抵押") || form.getData_con().equals("华融信贷"))){
                flag2 = true;
            }
        }

        for (FormResponse.Result.Form form : formLists) {
            String title = form.getControl_title();

            if (title != null && title.equals("同行手机号")) {
                if (flag1 || flag2) {
                    form.setRead_only(true);
                    form.setPlace_holder("无");
                    form.setData_con("");
                } else{
                    form.setRead_only(false);
                    form.setPlace_holder("请输入手机号");
                }
                formListsAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void lookLoanDetail(){

        HashMap<String,Object> map = new HashMap<>();
        map.put("token",token );
        map.put("w_con_id",workflow_content_id );

        iRLPresenter.LookLoanDetail(map);
    }

    public void toLoanDetail(LoanDetailResponse.Result result){
        Intent intent = new Intent(this, LoanDetail.class);
        intent.putExtra("loanDetail", result);
        context.startActivity(intent);
    }

    public void saveManagers(ManagersResponse.Result result){
        if (result.getSeller_list() == null || result.getSeller_list().size() <= 0){
            return;
        }

        sellerNames = "";
        sellers = result.getSeller_list();

        for (int i= 0;i< sellers.size();i++){
            if (i == 0 || i == sellers.size()){
                sellerNames = sellerNames + sellers.get(i).getName();
            }else{
                sellerNames = sellerNames + "," + sellers.get(i).getName();
            }
        }

        for (int i= 0;i< formLists.size();i++){

            if (formLists.get(i).getControl_title().contains("业务员")){
                formLists.get(i).setUnit(sellerNames);
            }
        }

        formListsAdapter.notifyDataSetChanged();

    }
    public void saveAdvSecInfo(SearchResponse.Result result){
//        接口暂时空置不用
    }

    public String getSellerId(String name){
        for (ManagersResponse.Result.Seller seller:sellers){
            if (seller.getName().equals(name)){
                return seller.getUser_id();
            }
        }
        return "";
    }

    public void getLegalState(boolean isLegal){
        for (int i= 0;i< formLists.size();i++){

            if (formLists.get(i).getSubmit_field().equals("is_litigation")){
                formLists.get(i).setData_con(isLegal?"1":"0");
            }
        }

        formListsAdapter.notifyDataSetChanged();
    }


    public void toRisksSearch(){
        String name = "";
        String phone = "";
        String identity_id = "";
        for (int i=0;i<formLists.size();i++){
            if (formLists.get(i).getControl_title().contains("姓名")){
                name = formLists.get(i).getData_con();
            }
            if (formLists.get(i).getControl_title().contains("手机")){
                phone = formLists.get(i).getData_con();
            }
            if (formLists.get(i).getControl_title().contains("身份证")){
                identity_id = formLists.get(i).getData_con();
            }
        }

        if (name.equals("")){
            showFailureMsg("请输入姓名后进行查看");
            return;
        }

        if (phone.equals("")){
            showFailureMsg("请输入手机号后进行查看");
            return;
        }

        if (identity_id.equals("")){
            showFailureMsg("请输入身份证号后进行查看");
            return;
        }

        Intent intent = new Intent(this, RiskSearch.class);
        intent.putExtra("token",UserInfo.getInstance(context).getUserInfo("token"));
        intent.putExtra("w_con_id",workflow_content_id+"");
        intent.putExtra("name",name);
        intent.putExtra("phone",phone);
        intent.putExtra("identity_id",identity_id);
        context.startActivity(intent);
    }
}
