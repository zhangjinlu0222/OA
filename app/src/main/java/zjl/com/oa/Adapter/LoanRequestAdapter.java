package zjl.com.oa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zjl.com.oa.CustomView.MyExpandableListview;
import zjl.com.oa.R;
import zjl.com.oa.Response.GetLoanRequestResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public class LoanRequestAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupData;
    private List<List<GetLoanRequestResponse.Result.Section.dict>> childData;
    private LayoutInflater layoutInflater;
    private MyExpandableListview expandableListView;
    private ViewGroup.LayoutParams btnParams,tvParams,relativeParas;

    public LoanRequestAdapter(Context context, List<String> groupData, List<List<GetLoanRequestResponse.Result.Section.dict>> childData, MyExpandableListview expandableListView) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
        this.layoutInflater = LayoutInflater.from(context);
        this.expandableListView = expandableListView;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.itemLoanRequestGroupButton = convertView.findViewById(R.id.item_loan_request_group_button);
            groupViewHolder.itemLoanRequestGroupTitle = convertView.findViewById(R.id.item_loan_request_group_title);
            groupViewHolder.relativeLayout = convertView.findViewById(R.id.relativelayout);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.itemLoanRequestGroupTitle.setText(groupData.get(groupPosition));

        if (isExpanded){
            Glide.with(convertView).load(R.mipmap.expanded).into(groupViewHolder.itemLoanRequestGroupButton);
        }else{
            Glide.with(convertView).load(R.mipmap.collapsed).into(groupViewHolder.itemLoanRequestGroupButton);
        }

        if (groupData.get(groupPosition).equals("")){
            groupViewHolder.itemLoanRequestGroupTitle.setVisibility(View.GONE);
            groupViewHolder.itemLoanRequestGroupButton.setVisibility(View.GONE);

            ViewGroup.LayoutParams params =  groupViewHolder.relativeLayout.getLayoutParams();
            relativeParas =  params;
            btnParams = groupViewHolder.itemLoanRequestGroupButton.getLayoutParams();
            tvParams = groupViewHolder.itemLoanRequestGroupTitle.getLayoutParams();
            params.height = 0;
            groupViewHolder.relativeLayout.setLayoutParams(params);

        }else{
            groupViewHolder.relativeLayout.setVisibility(View.VISIBLE);
            groupViewHolder.itemLoanRequestGroupTitle.setVisibility(View.VISIBLE);
            groupViewHolder.itemLoanRequestGroupButton.setVisibility(View.VISIBLE);

            groupViewHolder.relativeLayout.setLayoutParams(relativeParas);
            groupViewHolder.itemLoanRequestGroupTitle.setLayoutParams(tvParams);
            groupViewHolder.itemLoanRequestGroupButton.setLayoutParams(btnParams);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_loan_requst_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.itemLoanRequestChildKey = convertView.findViewById(R.id.item_loan_request_child_key);
            childViewHolder.itemLoanRequestChildValue = convertView.findViewById(R.id.item_loan_request_child_value);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.itemLoanRequestChildKey.setText(childData.get(groupPosition).get(childPosition).getL_key().trim());
        childViewHolder.itemLoanRequestChildValue.setText(childData.get(groupPosition).get(childPosition).getL_value().trim());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView itemLoanRequestGroupTitle;
        ImageView itemLoanRequestGroupButton;
        RelativeLayout relativeLayout;
    }

    static class ChildViewHolder {
        TextView itemLoanRequestChildKey;
        TextView itemLoanRequestChildValue;
    }
}
