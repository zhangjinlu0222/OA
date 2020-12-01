package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjl.com.oa2.CustomView.MyGridView;
import zjl.com.oa2.CustomView.MyListView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.BigDataResponse;

/**
 * Created by Administrator on 2018/3/14.
 */

public class BigDataDetailsAdapter extends BaseAdapter {
    private MyListView listView;
    private List<BigDataResponse.Result.Detections.Detail> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public BigDataDetailsAdapter(MyListView listView, List<BigDataResponse.Result.Detections.Detail> data, Context context) {
        this.listView = listView;
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
            convertView = layoutInflater.inflate(R.layout.item_risk_search_detail, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setBigDataDetailValue(viewHolder, position);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.mGridView)
        MyGridView mGridView;
        BigDataDetailsGridAdapter adapter;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void setBigDataDetailValue(ViewHolder holder,int pos){
        String name = data.get(pos).getName();
        holder.tvName.setText(name);

        List<String > header = data.get(pos).getHeader();

        holder.mGridView.setNumColumns(header.size());

        List<List<String>> values = data.get(pos).getValues();

        List<String > base = new ArrayList<>();
        base.addAll(header);
        for (List<String> item:values){
            base.addAll(item);
        }

        holder.adapter = new BigDataDetailsGridAdapter(holder.mGridView,base,context);

        holder.mGridView.setAdapter(holder.adapter);

        holder.adapter.notifyDataSetChanged();
    }
}
