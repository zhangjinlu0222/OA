package zjl.com.oa.Adapter;

import android.content.Context;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import zjl.com.oa.Bean.WorkFlowGroupData;
import zjl.com.oa.CustomView.MyExpandableListview;
import zjl.com.oa.R;
import zjl.com.oa.Response.GetWorkFlowResponse;

/**
 * Created by Administrator on 2018/3/20.
 */

public class WorkDetailAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<WorkFlowGroupData> groupData;
    private List<List<GetWorkFlowResponse.Result.Section.dict>> childData;
    private LayoutInflater layoutInflater;
    private MyExpandableListview expandableListView;

    ViewGroup.LayoutParams bakLayoutParams = null;

    public WorkDetailAdapter(Context context,List<WorkFlowGroupData> groupData, List<List<GetWorkFlowResponse.Result.Section.dict>> childData, MyExpandableListview expandableListView) {
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
        if (childData != null && childData.get(groupPosition) != null){
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
            convertView = layoutInflater.inflate(R.layout.item_workdetail_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.itemWorkDetailGroupTitle = convertView.findViewById(R.id.item_loan_request_group_title);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.itemWorkDetailGroupTitle.setText(groupData.get(groupPosition).getTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_workdetail_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.itemWorkDetailChildKey = convertView.findViewById(R.id.item_loan_request_child_key);
            childViewHolder.itemWorkDetailChildValue = convertView.findViewById(R.id.item_loan_request_child_value);
            convertView.setTag(childViewHolder);

            bakLayoutParams = childViewHolder.itemWorkDetailChildValue.getLayoutParams();

        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }


        childViewHolder.itemWorkDetailChildKey.setText(childData.get(groupPosition).get(childPosition).getL_key().trim());
        childViewHolder.itemWorkDetailChildValue.setText(childData.get(groupPosition).get(childPosition).getL_value().trim());
        childViewHolder.itemWorkDetailChildValue.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(childData.get(groupPosition).get(childPosition).getL_value().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        String value = childData.get(groupPosition).get(childPosition).getL_value().trim();

        if (value.contains("备注")){
            bakLayoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            childViewHolder.itemWorkDetailChildValue.setLayoutParams(bakLayoutParams);
            childViewHolder.itemWorkDetailChildValue.invalidate();
        }else{
            childViewHolder.itemWorkDetailChildValue.setLayoutParams(bakLayoutParams);
            childViewHolder.itemWorkDetailChildValue.invalidate();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView itemWorkDetailGroupTitle;
    }

    static class ChildViewHolder {
        TextView itemWorkDetailChildKey;
        TextView itemWorkDetailChildValue;
    }
}
