package zjl.com.oa2.WorkFlow.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Adapter.ThumbnailsAdapter;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.LocalMediaUtil;
import zjl.com.oa2.Utils.TitleBarUtil;
import zjl.com.oa2.Utils.VideoUtil;

public class Thumbnails extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.gv_thumbnails)
    GridView gvThumbnails;
    @Bind(R.id.ig_back)
    ImageView igBack;

    private List<String> thumbnails = new ArrayList<>();
    private List<LocalMedia> normalMedias = new ArrayList<>();

    private ThumbnailsAdapter thumbnailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnails);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        thumbnails.addAll(getIntent().getStringArrayListExtra("thumbnails"));

        for (String str :getIntent().getStringArrayListExtra("normalMedias")) {
                    LocalMedia media = new LocalMedia();
                    media.setPath(str);
                    if (VideoUtil.isVideo(str)){
                        media.setPictureType("video/mp4");
                    }
            normalMedias.add(media);
                }
        thumbnailsAdapter = new ThumbnailsAdapter(context,thumbnails);
        gvThumbnails.setAdapter(thumbnailsAdapter);
        gvThumbnails.setOnItemClickListener(this);
    }

    @OnClick({R.id.ig_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (normalMedias.get(position).getPictureType().equals("video/mp4")){
            Intent intent = new Intent(this, VideoPlay2Activity.class);
            intent.putExtra("path", normalMedias.get(position).getPath());
            startActivity(intent);
        }else{
            PictureSelector.create(Thumbnails.this).externalPicturePreview(position, normalMedias);
        }
    }
}
