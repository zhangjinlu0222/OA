package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjl.com.oa2.CustomView.MyListView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.BigDataResponse;

/**
 * Created by Administrator on 2018/3/14.
 */

public class BigDataAdapter extends BaseAdapter {
    private ListView listView;
    private List<BigDataResponse.Result.Detections> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public BigDataAdapter(ListView listView, List<BigDataResponse.Result.Detections> data, Context context) {
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
            convertView = layoutInflater.inflate(R.layout.item_risk_search, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setBigDataValue(viewHolder,position);

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_conclusion)
        TextView tvConclusion;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.tv_analysis)
        TextView tvAnalysis;
        @Bind(R.id.lv_values)
        MyListView lvValues;

        BigDataDetailsAdapter adapter;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    void setBigDataValue(ViewHolder holder,int pos){
        String name = data.get(pos).getName();
        holder.tvName.setText(name);
        String conclusion = data.get(pos).getConclusion();
        holder.tvConclusion.setText(conclusion);
        String description = data.get(pos).getDescription();
        holder.tvDescription.setText(description);
        String analysis = data.get(pos).getAnalysis();
        holder.tvAnalysis.setText(analysis);


        holder.adapter = new BigDataDetailsAdapter(holder.lvValues,data.get(pos).getDetails(),context);

        holder.lvValues.setAdapter(holder.adapter);

        holder.adapter.notifyDataSetChanged();

    }
}
