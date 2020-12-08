package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.LoanInfosResponse;

/**
 * Created by Administrator on 2017/5/26.
 */

public class LoanInfosListAdapter extends BaseAdapter {

    public List<LoanInfosResponse.Result.LoanInfo> data;
    public Context context;
    private PullToRefreshListView view;
    private LayoutInflater inflater;

    public LoanInfosListAdapter(Context context, List<LoanInfosResponse.Result.LoanInfo> data, PullToRefreshListView view) {
        this.data = data;
        this.context = context;
        this.view = view;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_loaninfos, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.index.setText(data.get(position).getInit());
        viewHolder.name.setText(data.get(position).getName());
        viewHolder.carPrice.setText(data.get(position).getAmount());
        viewHolder.startDate.setText(data.get(position).getContract_date());

        String carmodel = data.get(position).getCar_type();
        if (carmodel == null || carmodel.length() <= 0){
            viewHolder.rlCarType.setVisibility(View.GONE);
        }else{
            viewHolder.carModel.setText(carmodel);
            viewHolder.rlCarType.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.index)
        TextView index;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.carModel)
        TextView carModel;
        @Bind(R.id.rlCarModel)
        RelativeLayout rlCarType;
        @Bind(R.id.carPrice)
        TextView carPrice;
        @Bind(R.id.startDate)
        TextView startDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
