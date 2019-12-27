package zjl.com.oa.InformationCheck.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
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
import zjl.com.oa.Adapter.InfoCheckAdapter;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Base.PhotosActivity;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.CustomView.ActionSheetDialog;
import zjl.com.oa.CustomView.MyListView;
import zjl.com.oa.InformationCheck.Model.InfoCheckPresenterImpl;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckPresenter;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckView;
import zjl.com.oa.R;
import zjl.com.oa.RenewLoan.View.RenewLoanActivity;
import zjl.com.oa.Response.FormResponse;
import zjl.com.oa.Utils.LocalMediaUtil;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class InformationCheck extends BaseActivity implements IInfoCheckView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.listview)
    MyListView listview;
    @Bind(R.id.infocheck_btn_refuse)
    Button infocheckBtnRefuse;
    @Bind(R.id.infocheck_btn_next)
    Button infocheckBtnNext;

    private IInfoCheckPresenter infoCheckPresenter;
    private int workflow_content_id;
    private int wk_point_id;
    private String workflow_name;
    private String token;
    //    private BuildBean dialog;
    private BuildBean refuseDialog;

    public boolean reportFlag = false;
    public boolean photoFlag = false;
    private String way;

    private List<LocalMedia> lFiles = new ArrayList<>();

    private List<FormResponse.Result.Form> lInfos = new ArrayList<>();
    private InfoCheckAdapter infoCheckAdapter;

    private BuildBean submitDialog;

    private SelectListUpdate receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_check);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        token = UserInfo.getInstance(this).getUserInfo(UserInfo.TOKEN);

        infoCheckPresenter = new InfoCheckPresenterImpl(this);
        workflow_content_id = Integer.parseInt(getIntent().getStringExtra("workflow_content_id"));
        wk_point_id = Integer.parseInt(getIntent().getStringExtra("wk_point_id"));
        workflow_name = getIntent().getStringExtra("workflow_name");

        type = getIntent().getStringExtra("type");

        if (workflow_name != null && workflow_name.length() > 0) {
            tvTitle.setText(workflow_name);
        }

        infoCheckPresenter.Form(token, workflow_content_id, wk_point_id);

        infoCheckAdapter = new InfoCheckAdapter(lInfos, InformationCheck.this, listview);
        listview.setAdapter(infoCheckAdapter);
        infoCheckAdapter.notifyDataSetChanged();

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
        hideProgress();
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
        infoCheckAdapter.notifyDataSetChanged();
    }

    @Override
    public String getData_Con(String arg) {
        for (FormResponse.Result.Form form : lInfos) {
            if (arg != null && arg.equals(form.getControl_title().trim())) {
                return form.getData_con().trim();
            }
        }
        return null;
    }


    public void showAlertDialog() {

        View rootView = View.inflate(InformationCheck.this, R.layout.alertdialog, null);
        refuseDialog = DialogUIUtils.showCustomAlert(InformationCheck.this, rootView);

        TextView title = rootView.findViewById(R.id.title);
        title.setText("确认拒件");

        EditText et_reason = rootView.findViewById(R.id.et_feedback);
        et_reason.setHint("拒件原因");

        TextView cancel = rootView.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(refuseDialog);
            }
        });

        TextView confirm = rootView.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(refuseDialog);
                if (!netConnected) {
                    showNetError();
                } else if (et_reason.getText().toString().trim().length() <= 0) {
                    showFailureMsg("请输入拒件原因");
                } else {
                    infoCheckPresenter.endWorkFlow(token, workflow_content_id, wk_point_id,
                            et_reason.getText().toString().trim());
                }
            }
        });

        refuseDialog.show();
    }

    @OnClick({R.id.ig_back,R.id.infocheck_btn_refuse, R.id.infocheck_btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.infocheck_btn_refuse:
                if (!netConnected) {
                    showNetError();
                } else if (isFastDoubleClick(R.id.infocheck_btn_refuse)) {
                    return;
                } else {
                    showAlertDialog();
                }
                break;
            case R.id.infocheck_btn_next:

                if (!netConnected) {
                    showNetError();
                } else if (isFastDoubleClick(R.id.infocheck_btn_next)) {
                    return;
                } else {
                    HashMap<String ,Object> map = new HashMap<>();

                    map.put("token",token );
                    map.put("w_con_id", workflow_content_id);
                    map.put("w_pot_id", wk_point_id);

                    for (int i=0;i< lInfos.size();i++){
                        String submit_field = lInfos.get(i).getSubmit_field();

                        if (submit_field != null && submit_field.length() >0){
                            String value = lInfos.get(i).getData_con();
                            map.put(submit_field,value);
                        }
                    }

                    if (infoCheckPresenter != null){
                        infoCheckPresenter.uploadMsg(request_start_flag,
                                !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,map,
                                lFiles);
                    }
//                    infoCheckPresenter.uploadMsg(request_start_flag,
//                            !uploadType ? UPLOAD_TYPE_NORMAL : UPLOAD_TYPE_ADD,
//                            token, workflow_content_id,
//                            getData_Con("人法查询"),
//                            getData_Con("征信查询"),
//                            getData_Con("违章查询"),
//                            getData_Con("保险查询"),
//                            getData_Con("企业法人查询"),
//                            wk_point_id,
//                            lFiles,
//                            getData_Con("备注"));
                }
                break;
        }
    }
    public void showSheetDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog.ActionSheetBuilder(InformationCheck.this, R.style.ActionSheetDialogBase)
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
                        Toast.makeText(InformationCheck.this, "取消操作", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(true)
                .create();
        dialog.show();
    }

    private void getFileWithType(int type) {

        PictureSelector.create(InformationCheck.this)
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
                .selectionMedia(lFiles)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果
                    lFiles.clear();
                    lFiles.addAll(PictureSelector.obtainMultipleResult(data));
                    addImgs(lFiles);
                    infoCheckAdapter.notifyDataSetChanged();
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

        for (FormResponse.Result.Form form : lInfos) {
            if (form.getControl_title().toString().trim().contains("附加照片")) {
                form.setImgs(imgs);
            }
        }
    }
    public void onClickPic(int position) {
        if (lFiles.size() > 0) {
            LocalMedia media = lFiles.get(position);
            String pictureType = media.getPictureType();
            int mediaType = PictureMimeType.pictureToVideo(pictureType);
            switch (mediaType) {
                case 1:
                    // 预览图片 可自定长按保存路径
                    PictureSelector.create(InformationCheck.this).externalPicturePreview(position, lFiles);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(InformationCheck.this).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(InformationCheck.this).externalPictureAudio(media.getPath());
                    break;
            }
        }
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
    public void showQuotaImage(int parentPosition, int itemPosition) {

        if (parentPosition < 0) {
            return;
        }
        if (itemPosition < 0) {
            return;
        }

        lFiles.clear();

        List<String> sList = new ArrayList<>();
        sList.addAll(lInfos.get(parentPosition).getImgs());
        for (String img : sList) {
            LocalMedia media = new LocalMedia();
            media.setPath(img);
            media.setPictureType(LocalMediaUtil.mediaType(img));
            lFiles.add(media);
        }

        LocalMedia media = lFiles.get(itemPosition);
        String pictureType = media.getPictureType();
        int mediaType = PictureMimeType.pictureToVideo(pictureType);
        switch (mediaType) {
            case 1:
                // 预览图片 可自定长按保存路径
                PictureSelector.create(InformationCheck.this).externalPicturePreview(itemPosition, lFiles);
                break;
            case 2:
                // 预览视频
                PictureSelector.create(InformationCheck.this).externalPictureVideo(media.getPath());
                break;
            case 3:
                // 预览音频
                PictureSelector.create(InformationCheck.this).externalPictureAudio(media.getPath());
                break;
        }
    }
    public String getInType() {
        if (type != null) {
            return type;
        }
        return "";
    }
    private class SelectListUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int delPos = intent.getIntExtra("deleteIndex", -1);
                if (delPos != -1 && delPos <= lFiles.size()) {
                    lFiles.remove(delPos);
                }
            }
        }
    }
}
