package zjl.com.oa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import zjl.com.oa.R;

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

        Glide.with(context).load(thumbnails.get(position)).into(viewholder.img);
        return convertView;
    }
}
