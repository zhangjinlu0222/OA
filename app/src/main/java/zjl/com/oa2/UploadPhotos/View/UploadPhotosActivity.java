package zjl.com.oa2.UploadPhotos.View;

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
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Base.PhotosActivity;
import zjl.com.oa2.Bean.UploadCarPhotosType;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.R;
import zjl.com.oa2.UploadPhotos.Model.PhotoUploadPresenterImpl;
import zjl.com.oa2.UploadPhotos.Presenter.IPhotoUploadPresenter;
import zjl.com.oa2.UploadPhotos.Presenter.IPhotoUploadView;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class UploadPhotosActivity extends PhotosActivity implements IPhotoUploadView {

    @Bind(R.id.pull_refresh_grid)
    RecyclerView pullRefreshGrid;
    @Bind(R.id.evaluation_beizhu_words_left)
    TextView evaluationBeizhuWordsLeft;
    @Bind(R.id.etBakInfo)
    EditText etBakInfo;
    @Bind(R.id.Btnnext)
    Button Btnnext;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.uploadtype)
    SwitchButton uploadtype;
    @Bind(R.id.uploadtypehint)
    TextView uploadtypehint;

    private int workflow_content_id;
    private int wk_point_id;
    private int type_id;
    private IPhotoUploadPresenter photoUploadPresenter;
    //    private BuildBean dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_with_comments);
        ButterKnife.bind(this);

        workflow_content_id = Integer.parseInt(getIntent().getStringExtra("workflow_content_id"));
        wk_point_id = Integer.parseInt(getIntent().getStringExtra("wk_point_id"));
        type_id = new UploadCarPhotosType().getType_id(wk_point_id);
        switch (type_id) {

            //1=上传车照片
            //2=收车
            //3=装GPS
            //4=签约
            //5=通知业务首扣
            //6=放款结束（不传照片，备注给空格）
            //7=实地考察
            //8=抵押登记
            //9=风控审核（不传照片，备注给空格）
            case 1:
                title.setText(R.string.uploadCarPhoto);
                break;
            case 2:
                title.setText(R.string.uploadCarPicture);
                break;
            case 3:
                title.setText(R.string.uploadKeyPhoto);
                break;
            case 8:
                title.setText(R.string.mortgagereg);
                break;
            case 4:
                title.setText(R.string.sign);
                break;
            case 5:
                title.setText(R.string.firstchargeinform);
                break;
            case 6:
                title.setText(R.string.loanfinish);
                break;
            case 7:
                title.setText(R.string.otsinvest);
                break;
            default:
                title.setText(R.string.uploadCarPhoto);
                break;
        }

        photoUploadPresenter = new PhotoUploadPresenterImpl(this);

//        dialog = DialogUIUtils.showLoading(context,getString(R.string.fileuploading),true,false,false,true);
        this.setRecycleViewAdapter(pullRefreshGrid, true);
        etBakInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                evaluationBeizhuWordsLeft.setText(s.length() + "/100字");
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
                    photoUploadPresenter.uploadPhotos(request_start_flag,
                            uploadtype.isChecked() ? UPLOAD_TYPE_ADD : UPLOAD_TYPE_NORMAL,
                            getToken(), getBakInfo(), workflow_content_id, wk_point_id,
                            getUploadPhotos(), Integer.toString(type_id));
                }
                break;
        }
    }

    @Override
    public String getToken() {
        return UserInfo.getInstance(getBaseContext()).getUserInfo(UserInfo.TOKEN);
    }

    @Override
    public void UploadFail() {
        this.showFailureMsg("上传失败，请重新上传");
        this.toMainActivity();
    }

    @Override
    public String getBakInfo() {
        return etBakInfo.getText().toString().trim();
    }

    @Override
    public List<LocalMedia> getUploadPhotos() {
        return getSelectList();
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
}
