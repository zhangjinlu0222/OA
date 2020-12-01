package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjl.com.oa2.CustomView.MyGridView;
import zjl.com.oa2.R;

/**
 * Created by Administrator on 2018/3/14.
 */

public class BigDataDetailsGridAdapter extends BaseAdapter {
    private MyGridView gridView;
    private List<String> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public BigDataDetailsGridAdapter(MyGridView gridView, List<String> data, Context context) {
        this.gridView = gridView;
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_risk_search_detail_grid, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setBigDataDetailGridValue(viewHolder, position);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    void  setBigDataDetailGridValue(ViewHolder holder,int pos){
        holder.tvName.setText(data.get(pos));
    }
}
