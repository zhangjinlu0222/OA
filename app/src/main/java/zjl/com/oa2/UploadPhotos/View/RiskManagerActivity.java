package zjl.com.oa2.UploadPhotos.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.UploadCarPhotosType;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.R;
import zjl.com.oa2.UploadPhotos.Model.PhotoUploadPresenterImpl;
import zjl.com.oa2.UploadPhotos.Presenter.IPhotoUploadView;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class RiskManagerActivity extends BaseActivity implements IPhotoUploadView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.releaseloan)
    TextView releaseloan;
    @Bind(R.id.releaseloanhint)
    TextView releaseloanhint;
    @Bind(R.id.space)
    View space;
    @Bind(R.id.next)
    Button next;
    private String workflow_content_id, wk_point_id;

    private PhotoUploadPresenterImpl photoUploadPresenter;
    private int type_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_finish);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        type_id = new UploadCarPhotosType().getType_id(Integer.parseInt(wk_point_id));

        photoUploadPresenter = new PhotoUploadPresenterImpl(this);
        initView();
    }
    private void initView(){
        title.setText(R.string.riskmanager);
        releaseloan.setText(R.string.riskmanagerstate);
        releaseloanhint.setText(R.string.riskmanagerhint);
        next.setText(R.string.next);
    }

    @Override
    public String getToken() {
        return UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
    }

    @Override
    public String getBakInfo() {
        //风控审核调用上传图片接口，备注传入空格
        return " ";
    }

    @Override
    public List<LocalMedia> getUploadPhotos() {
        return new ArrayList<>();
    }

    @Override
    public void UploadFail() {

    }

    @Override
    public void showProgress() {
        if (submitDialog != null){
            submitDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (submitDialog != null){
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    @OnClick({R.id.ig_back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.next:
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.next)){
                    return;
                }else {
                    photoUploadPresenter.uploadPhotos(request_start_flag,UPLOAD_TYPE_NORMAL,getToken(), getBakInfo(),
                            Integer.parseInt(workflow_content_id), Integer.parseInt(wk_point_id),
                            getUploadPhotos(), Integer.toString(type_id));
                }
                break;
        }
    }
}
