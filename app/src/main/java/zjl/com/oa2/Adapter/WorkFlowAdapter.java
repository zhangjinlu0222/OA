package zjl.com.oa2.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zjl.com.oa2.Bean.WorkFlowGroupData;
import zjl.com.oa2.CustomView.MyExpandableListview;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.GetWorkFlowResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public class WorkFlowAdapter extends BaseExpandableListAdapter implements View.OnClickListener {
    private Context context;
    private List<WorkFlowGroupData> groupData;
    private List<List<GetWorkFlowResponse.Result.Section.dict>> childData;
    private LayoutInflater layoutInflater;
    private MyExpandableListview expandableListView;
    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public WorkFlowAdapter(Context context, List<WorkFlowGroupData> groupData, List<List<GetWorkFlowResponse.Result.Section.dict>> childData, MyExpandableListview expandableListView) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
        this.layoutInflater = LayoutInflater.from(context);
        this.expandableListView = expandableListView;
    }

    @Override
    public int getGroupCount() {
        if (groupData != null){
            return groupData.size();
        }else{
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (childData != null && childData.get(groupPosition) != null){
            return childData.get(groupPosition).size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupData != null){
            return groupData.get(groupPosition);
        }else{
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (childData != null&& childData.get(groupPosition) != null){
            return childData.get(groupPosition).get(childPosition);
        }else{
            return null;
        }
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
            convertView = layoutInflater.inflate(R.layout.item_loan_requst_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.itemLoanRequestGroupButton = convertView.findViewById(R.id.item_loan_request_group_button);
            groupViewHolder.itemLoanRequestGroupTitle = convertView.findViewById(R.id.item_loan_request_group_title);
            groupViewHolder.itemLoanRequestGroupDate = convertView.findViewById(R.id.date);
            groupViewHolder.itemLoanRequestGroupReEdit = convertView.findViewById(R.id.reedit);
//            groupViewHolder.itemLoanRequestGroupRefuse = convertView.findViewById(R.id.refuse);
            groupViewHolder.itemLoanRequestGroupReEdit.setTag(groupPosition);
//            groupViewHolder.itemLoanRequestGroupRefuse.setTag(groupPosition);
            groupViewHolder.relativeLayout = convertView.findViewById(R.id.relativelayout);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.itemLoanRequestGroupTitle.setText(groupData.get(groupPosition).getTitle());
        groupViewHolder.itemLoanRequestGroupDate.setText(groupData.get(groupPosition).getDate());

        if (isExpanded){
            Glide.with(convertView).load(R.mipmap.expanded).into(groupViewHolder.itemLoanRequestGroupButton);
        }else{
            Glide.with(convertView).load(R.mipmap.collapsed).into(groupViewHolder.itemLoanRequestGroupButton);
        }

        int reedit_flag = groupData.get(groupPosition).getReedit_flag();
        String date = groupData.get(groupPosition).getDate();
        if (reedit_flag == 0){
            groupViewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#f7b732"));//驳回颜色
        }else{
            if ( date != null && date.equals("未开始")){
                groupViewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#b2b2b2"));//未开始颜色
            }else if (date != null && date.equals("待处理")){
                groupViewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#3edea9"));//待处理颜色
            }else{
                groupViewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#0f4b88"));//完成颜色
            }
        }

        int edit_flag = groupData.get(groupPosition).getEdit();
        if (edit_flag == 1 && "2".equals(groupData.get(groupPosition).getState())){
            groupViewHolder.itemLoanRequestGroupReEdit.setVisibility(View.VISIBLE);
            groupViewHolder.itemLoanRequestGroupReEdit.setOnClickListener(this);
//            groupViewHolder.itemLoanRequestGroupRefuse.setVisibility(View.VISIBLE);
//            groupViewHolder.itemLoanRequestGroupRefuse.setOnClickListener(this);
        }else{
            groupViewHolder.itemLoanRequestGroupReEdit.setVisibility(View.GONE);
//            groupViewHolder.itemLoanRequestGroupRefuse.setVisibility(View.GONE);
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
        String value = childData.get(groupPosition).get(childPosition).getL_value().trim();
        String key = childData.get(groupPosition).get(childPosition).getL_key().trim();
        childViewHolder.itemLoanRequestChildKey.setText(key);
        childViewHolder.itemLoanRequestChildValue.setText(value);

        ViewGroup.LayoutParams bakLayoutParams = childViewHolder.itemLoanRequestChildValue.getLayoutParams();

//        if (key.contains("备注") && value.length() >= 200){
        if (value.length() >= 20){
            bakLayoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            childViewHolder.itemLoanRequestChildValue.setLayoutParams(bakLayoutParams);
            childViewHolder.itemLoanRequestChildValue.invalidate();
        }else{
            bakLayoutParams.height = 80;
            childViewHolder.itemLoanRequestChildValue.setLayoutParams(bakLayoutParams);
            childViewHolder.itemLoanRequestChildValue.invalidate();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView itemLoanRequestGroupTitle;
        ImageView itemLoanRequestGroupButton;
        TextView itemLoanRequestGroupDate;
        TextView itemLoanRequestGroupReEdit;
//        TextView itemLoanRequestGroupRefuse;
        RelativeLayout relativeLayout;
    }

    static class ChildViewHolder {
        TextView itemLoanRequestChildKey;
        TextView itemLoanRequestChildValue;
    }
}
