package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.AppraisalResponse;

/**
 * Created by Administrator on 2017/5/26.
 */

public class AppraisalListAdapter extends BaseAdapter {

    public List<AppraisalResponse.Result.Data> data;
    public Context context;
    private PullToRefreshListView view;
    private LayoutInflater inflater;

    public AppraisalListAdapter(Context context, List<AppraisalResponse.Result.Data> data, PullToRefreshListView view) {
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
            convertView = inflater.inflate(R.layout.appraisal_result_item, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.tvAppraisalDate = convertView.findViewById(R.id.tv_appraisal_date);
            viewHolder.tvAppraisalCarBranch = convertView.findViewById(R.id.tv_appraisal_car_branch);
            viewHolder.tvAppraisalCarModel = convertView.findViewById(R.id.tv_appraisal_car_model);
            viewHolder.tvAppraisalCarMilage = convertView.findViewById(R.id.tv_appraisal_car_milage);
            viewHolder.tvAppraisalCarDate = convertView.findViewById(R.id.tv_appraisal_car_date);
            viewHolder.tvAppraisalCarPrice = convertView.findViewById(R.id.tv_appraisal_car_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAppraisalDate.setText(data.get(position).getEstamate_date());
        viewHolder.tvAppraisalCarBranch.setText(data.get(position).getCar_branch());
        viewHolder.tvAppraisalCarModel.setText(data.get(position).getCar_model());
        viewHolder.tvAppraisalCarMilage.setText(data.get(position).getMilage());
        viewHolder.tvAppraisalCarDate.setText(data.get(position).getLicense_date());
        viewHolder.tvAppraisalCarPrice.setText(data.get(position).getPrice());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_appraisal_date)
        TextView tvAppraisalDate;
        @Bind(R.id.tv_appraisal_car_branch)
        TextView tvAppraisalCarBranch;
        @Bind(R.id.tv_appraisal_car_model)
        TextView tvAppraisalCarModel;
        @Bind(R.id.tv_appraisal_car_milage)
        TextView tvAppraisalCarMilage;
        @Bind(R.id.tv_appraisal_car_date)
        TextView tvAppraisalCarDate;
        @Bind(R.id.tv_appraisal_car_price)
        TextView tvAppraisalCarPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
