package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import zjl.com.oa2.CustomView.MyListView;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.GetBeginSignResponse;

/**
 * Created by Administrator on 2018/3/14.
 */

public class BeginSignAdapter extends BaseAdapter {
    private MyListView listView;
    private List<GetBeginSignResponse.Result.Data> dataList;
    private Context context;
    private LayoutInflater layoutInflater;

    public BeginSignAdapter(MyListView listView, List<GetBeginSignResponse.Result.Data> dataList, Context context) {
        this.listView = listView;
        this.dataList = dataList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    class ViewHolder {

        TextView name;
        EditText value;

        public ViewHolder(View convertView) {
            name = convertView.findViewById(R.id.name);
            value = convertView.findViewById(R.id.value);
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
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
            convertView = layoutInflater.inflate(R.layout.signing_item, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.value = convertView.findViewById(R.id.value);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name = dataList.get(position).getKey();
        viewHolder.name.setText(name);


        String value = dataList.get(position).getValue();

        if (name.contains("还款方式") && value.equals("")){
            viewHolder.value.setHint("");
        }else{
            viewHolder.value.setText(value);
        }
        viewHolder.value.setEnabled(false);
        viewHolder.value.setBackground(null);

        return convertView;
    }
}
