package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import zjl.com.oa2.R;

public class ThumbnailsAdapter extends BaseAdapter {
    private Context context;
    private List<String> thumbnails;

    public ThumbnailsAdapter(Context context,List<String> data) {
        this.context = context;
        this.thumbnails = data;
    }

    private class ViewHolder {
        ImageView img;

        public ViewHolder(View view) {
            this.img = view.findViewById(R.id.img);
        }
    }

    @Override
    public int getCount() {
        return thumbnails.size();
    }

    @Override
    public Object getItem(int position) {
        return thumbnails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.activity_thumbnails_list_item,null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) convertView.getTag();
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//关键代码，加载原始大小
                .format(DecodeFormat.PREFER_RGB_565);//设置为这种格式去掉透明度通道，可以减少内存占有
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(thumbnails.get(position)).into(viewholder.img);
        return convertView;
    }
}
