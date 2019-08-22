package zjl.com.oa.Sign.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Adapter.GridImageAdapter;
import zjl.com.oa.ApplicationConfig.Constant;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Base.FullyGridLayoutManager;
import zjl.com.oa.Bean.UploadCarPhotosType;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.R;
import zjl.com.oa.UploadPhotos.Model.PhotoUploadPresenterImpl;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUploadView;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class SignActivity extends BaseActivity implements IPhotoUploadView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.photos)
    RecyclerView photos;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.videos)
    RecyclerView videos;
    @Bind(R.id.beizhu_words_left)
    TextView beizhuWordsLeft;
    @Bind(R.id.et_beizhu)
    EditText etBeizhu;
    @Bind(R.id.next)
    Button next;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.uploadtype)
    SwitchButton uploadtype;
    @Bind(R.id.uploadtypehint)
    TextView uploadtypehint;

    private List<LocalMedia> photoslist = new ArrayList<>();
    private List<LocalMedia> videoslist = new ArrayList<>();
    private GridImageAdapter photosAdapter;
    private GridImageAdapter videosAdapter;

    private String token, workflow_content_id, wk_point_id;
    private String workflow_name;
    private PhotoUploadPresenterImpl photoUploadPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");

        workflow_name = getIntent().getStringExtra("workflow_name");
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        if (workflow_name != null && workflow_name.length() > 0) {
            title.setText(workflow_name);
        }

        initRecycleView();
        etBeizhu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                beizhuWordsLeft.setText(s.length() + Constant.WordsLeft);
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


        photoUploadPresenter = new PhotoUploadPresenterImpl(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        hideProgress();
    }

    @OnClick({R.id.ig_back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.next:
//                if (photoslist.size() <= 0 && videoslist.size() <= 0 && !uploadType) {
//                    showFailureMsg("请选择要上传的文件");
//                    return;
//                }
                if (!netConnected) {
                    showNetError();
                    return;
                }
                if (isFastDoubleClick(R.id.next)) {
                    return;
                } else {
                    int type_id = new UploadCarPhotosType().getType_id(Integer.parseInt(wk_point_id));
                    photoUploadPresenter.uploadPhotos(request_start_flag,
                            uploadtype.isChecked() ? UPLOAD_TYPE_ADD : UPLOAD_TYPE_NORMAL,
                            getToken(), getBakInfo(),
                            Integer.parseInt(workflow_content_id), Integer.parseInt(wk_point_id),
                            getUploadPhotos(), Integer.toString(type_id));
                }
                break;
        }
    }

    private void initRecycleView() {
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        photos.setLayoutManager(manager2);
        photosAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                PictureSelector.create(SignActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .maxSelectNum(Constant.MAXCOUNT)// 最大图片选择数量
                        .minSelectNum(Constant.MINCOUNT)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .compress(false)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .selectionMedia(photoslist)// 是否传入已选图片
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        }, photoslist);
        photosAdapter.setShowDel(true);
        photos.setAdapter(photosAdapter);
        photosAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (photoslist.size() > 0) {
                    LocalMedia media = photoslist.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(SignActivity.this).externalPicturePreview(position, photoslist);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(SignActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(SignActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        videos.setLayoutManager(manager);
        videosAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                PictureSelector.create(SignActivity.this)
                        .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .maxSelectNum(Constant.MAXCOUNT)// 最大图片选择数量
                        .minSelectNum(Constant.MINCOUNT)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .compress(false)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .selectionMedia(videoslist)// 是否传入已选图片
                        .forResult(PictureConfig.REQUEST_CAMERA);//结果回调onActivityResult code
            }
        }, videoslist);
        videosAdapter.setShowDel(true);
        videos.setAdapter(videosAdapter);
        videosAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (videoslist.size() > 0) {
                    LocalMedia media = videoslist.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(SignActivity.this).externalPicturePreview(position, videoslist);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(SignActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(SignActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    photoslist = PictureSelector.obtainMultipleResult(data);
                    photosAdapter.setList(photoslist);
                    photosAdapter.notifyDataSetChanged();
                    break;
                case PictureConfig.REQUEST_CAMERA:
                    // 视频选择
                    videoslist = PictureSelector.obtainMultipleResult(data);
                    videosAdapter.setList(videoslist);
                    videosAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public String getToken() {
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);
        return token;
    }

    @Override
    public String getBakInfo() {
        return etBeizhu.getText().toString().trim();
    }

    @Override
    public List<LocalMedia> getUploadPhotos() {
        List<LocalMedia> files = new ArrayList<>();
        if (photoslist != null && photoslist.size() > 0) {
            files.addAll(photoslist);
        }
        if (videoslist != null && videoslist.size() > 0) {
            files.addAll(videoslist);
        }
        return files;
    }

    @Override
    public void UploadFail() {
        Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toMainActivity() {
        this.finish();
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
