package zjl.com.oa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjl.com.oa2.Bean.LoanTop;
import zjl.com.oa2.Bean.Repayment;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.LoanDetailResponse;

public class LoanInfoAdapter extends BaseAdapter {

    public static final int TYPE_TOP = 1;
    public static final int TYPE_NORMAL = 2;
    /**
     * 上下滑动listview
     */
    public ListView listview;

    /**
     * 上下文
     */
    public Context context;

    /**
     * view展开器
     */
    public LayoutInflater inflater;

    /**
     * 数据源
     */
    public List repayments = new ArrayList<>();

    public LoanInfoAdapter(Context context, ListView view) {
        this.context = context;
        this.listview = view;
        this.inflater = LayoutInflater.from(context);
    }

    public void UpdateDataSource(List<Repayment> groupSource) {
        this.repayments.clear();
        this.repayments.addAll(groupSource);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (repayments.get(position) instanceof LoanTop) {
            return TYPE_TOP;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return repayments.size();
    }

    @Override
    public Object getItem(int position) {
        return repayments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder1 holder1 = null;
        ViewHolder holder = null;
        convertView = null;
        if (convertView == null) {
            switch (type) {
                case 1:
                    convertView = inflater.inflate(R.layout.item_loaninfo_header, null);
                    holder1 = new ViewHolder1(convertView);
                    convertView.setTag(1);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.item_loaninfo, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(2);
                    break;
            }
        } else {
            switch (type) {
                case 1:
                    holder1 = (ViewHolder1) convertView.getTag(1);
                    break;
                case 2:
                    holder = (ViewHolder) convertView.getTag(2);
                    break;
            }
        }

        switch (type) {
            case 1:
                setData(holder1, position);
                break;
            case 2:
                setData(holder, position);
                break;
        }
        return convertView;
    }


    private void setData(ViewHolder holder, final int position) {

        if (position > 1) {
            holder.rlLoaninfoTop.setVisibility(View.GONE);
        } else {
            holder.rlLoaninfoTop.setVisibility(View.VISIBLE);
        }
        Repayment repayment = (Repayment) repayments.get(position);

        holder.tvTime.setText(repayment.getIssue());
        holder.tvPayAmount.setText(repayment.getRepayment_amount());
        holder.tvActualDate.setText(repayment.getDate());
    }

    private void setData(ViewHolder1 holder1, final int position) {
        LoanTop loanTop = (LoanTop) repayments.get(position);
        holder1.tvAmount.setText(loanTop.getData().get(0).getName());
        holder1.tvAmountValue.setText(loanTop.getData().get(0).getName_value());
        holder1.tvMonthInterest.setText(loanTop.getData().get(1).getName());
        holder1.tvMonthInterestValue.setText(loanTop.getData().get(1).getName_value());
        holder1.tvActualMoney.setText(loanTop.getData().get(2).getName());
        holder1.tvActualMoneyValue.setText(loanTop.getData().get(2).getName_value());
    }

    static class ViewHolder1 {
        @Bind(R.id.tv_amount)
        TextView tvAmount;
        @Bind(R.id.tv_amount_value)
        TextView tvAmountValue;
        @Bind(R.id.tv_month_interest)
        TextView tvMonthInterest;
        @Bind(R.id.tv_month_interest_value)
        TextView tvMonthInterestValue;
        @Bind(R.id.tv_actual_money)
        TextView tvActualMoney;
        @Bind(R.id.tv_actual_money_value)
        TextView tvActualMoneyValue;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ViewHolder {
        @Bind(R.id.rl_loaninfo_top)
        RelativeLayout rlLoaninfoTop;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_pay_amount)
        TextView tvPayAmount;
        @Bind(R.id.tv_actual_date)
        TextView tvActualDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
