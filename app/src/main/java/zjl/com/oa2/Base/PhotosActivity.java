package zjl.com.oa2.Base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import zjl.com.oa2.Adapter.GridImageAdapter;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.CustomView.ActionSheetDialog;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.TitleBarUtil;

public class PhotosActivity extends BaseActivity {

    public Context context;

    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int mediaType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        /**
         * TitleBar 设置格式*/
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
    }

    public void setRecycleViewAdapter(RecyclerView view,boolean showDel){

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        view.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setShowDel(showDel);
        view.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(PhotosActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(PhotosActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(PhotosActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }
    public void setRecycleViewAdapter(RecyclerView view){
        this.setRecycleViewAdapter(view,false);
    }

    public List<LocalMedia> getSelectList(){
        return selectList;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    public GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            showSheetDialog();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回两种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
//                    DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    break;
            }
        }
    }

    private void showSheetDialog(){
        ActionSheetDialog dialog = new ActionSheetDialog.ActionSheetBuilder(PhotosActivity.this, R.style.ActionSheetDialogBase)
                .setItems(new CharSequence[]{"选择视频", "选择照片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 1:
                                mediaType = PictureMimeType.ofImage();
                                UploadFiles(mediaType);
                                dialog.dismiss();
                                break;
                            case 0:
                                mediaType = PictureMimeType.ofVideo();
                                UploadFiles(mediaType);
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .setTitle("上传")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaType = 0;
                        Toast.makeText(PhotosActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(true)
                .create();
        dialog.show();
    }
    private void UploadFiles(int mediaType){

        PictureSelector.create(PhotosActivity.this)
                .openGallery(mediaType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .maxSelectNum(Constant.MAXCOUNT)// 最大图片选择数量
                .minSelectNum(Constant.MINCOUNT)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode( PictureConfig.MULTIPLE)// 多选 or 单选
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .compress(false)// 是否压缩
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .selectionMedia(selectList)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}
