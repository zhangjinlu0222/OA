package zjl.com.oa2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjl.com.oa2.ApplicationConfig.Activitys;
import zjl.com.oa2.Bean.QuestState;
import zjl.com.oa2.QuestAndSetting.View.QuestAndSetting;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.QuestListResponse;
import zjl.com.oa2.WorkFlow.View.WorkDetailActivity;
import zjl.com.oa2.WorkFlow.View.WorkFlowActivity;

/**
 * Created by Administrator on 2017/5/26.
 */

public class QuestListAdapter extends BaseAdapter {

    public List<QuestListResponse.Result.Data> data;
    public QuestAndSetting context;
    private PullToRefreshListView view;
    private LayoutInflater inflater;
    private String workflow_point_id = "";

    public QuestListAdapter(List<QuestListResponse.Result.Data> data, QuestAndSetting context, PullToRefreshListView view) {
        this.data = data;
        this.context = context;
        this.view = view;
        this.inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        Context context;
        @Bind(R.id.project_ig_icon)
        ImageView projectIgIcon;
        @Bind(R.id.project_time)
        TextView projectTime;
        @Bind(R.id.project_detail)
        TextView projectDetail;
        @Bind(R.id.project_workflow)
        TextView projectWorkflow;
        @Bind(R.id.project_tv_customer_name)
        TextView projectTvCustomerName;
        @Bind(R.id.project_customer_name)
        TextView projectCustomerName;
        @Bind(R.id.project_tv_point1)
        TextView projectTvPoint1;
        @Bind(R.id.project_ig_point1)
        ImageView projectIgPoint1;
        @Bind(R.id.project_rl_point1)
        RelativeLayout projectRlPoint1;
        @Bind(R.id.project_tv_point2)
        TextView projectTvPoint2;
        @Bind(R.id.project_ig_point2)
        ImageView projectIgPoint2;
        @Bind(R.id.project_rl_point2)
        RelativeLayout projectRlPoint2;
        @Bind(R.id.project_tv_point3)
        TextView projectTvPoint3;
        @Bind(R.id.project_ig_point3)
        ImageView projectIgPoint3;
        @Bind(R.id.project_rl_point3)
        RelativeLayout projectRlPoint3;
        @Bind(R.id.project_ig_customer)
        ImageView projectIgCustomer;
        @Bind(R.id.project_tv_manager)
        TextView projectTvManager;
        @Bind(R.id.project_manager)
        TextView projectManager;
        @Bind(R.id.project_car)
        TextView projectCar;
        @Bind(R.id.project_state)
        TextView projectState;
        @Bind(R.id.tv_proc_type)
        TextView tvProcType;
        @Bind(R.id.rl_top)
        RelativeLayout rlTop;
        @Bind(R.id.rl_bottom)
        RelativeLayout rlBottom;
        @Bind(R.id.ll_center)
        LinearLayout llCenter;
        @Bind(R.id.project_tv_point3_name)
        TextView projectTvPoint3Name;
        @Bind(R.id.project_tv_point2_name)
        TextView projectTvPoint2Name;
        @Bind(R.id.project_tv_point1_name)
        TextView projectTvPoint1Name;
        @Bind(R.id.tv_finance)
        TextView tvFinance;
        @Bind(R.id.tv_project_provider)
        TextView tvProjectProvider;

        ViewHolder(Context context,View view) {
            this.context = context;
            ButterKnife.bind(this, view);
        }
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

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = (View) inflater.inflate(R.layout.project_quest_item, null);

            holder = new ViewHolder(context,convertView);
            holder.rlTop = convertView.findViewById(R.id.rl_top);
            holder.llCenter = convertView.findViewById(R.id.ll_center);
            holder.rlBottom = convertView.findViewById(R.id.rl_bottom);
            holder.projectTime = convertView.findViewById(R.id.project_time);
            holder.projectDetail = convertView.findViewById(R.id.project_detail);
            holder.projectWorkflow = convertView.findViewById(R.id.project_workflow);

            holder.projectCustomerName = convertView.findViewById(R.id.project_customer_name);
            holder.tvProcType = convertView.findViewById(R.id.tv_proc_type);
            holder.tvFinance = convertView.findViewById(R.id.tv_finance);

            holder.projectRlPoint1 = convertView.findViewById(R.id.project_rl_point1);
            holder.projectRlPoint2 = convertView.findViewById(R.id.project_rl_point2);
            holder.projectRlPoint3 = convertView.findViewById(R.id.project_rl_point3);

            holder.projectTvPoint1 = convertView.findViewById(R.id.project_tv_point1);
            holder.projectTvPoint2 = convertView.findViewById(R.id.project_tv_point2);
            holder.projectTvPoint3 = convertView.findViewById(R.id.project_tv_point3);

            holder.projectIgPoint1 = convertView.findViewById(R.id.project_ig_point1);
            holder.projectIgPoint2 = convertView.findViewById(R.id.project_ig_point2);
            holder.projectIgPoint3 = convertView.findViewById(R.id.project_ig_point3);

            holder.projectTvPoint1Name = convertView.findViewById(R.id.project_tv_point1_name);
            holder.projectTvPoint2Name = convertView.findViewById(R.id.project_tv_point2_name);
            holder.projectTvPoint3Name = convertView.findViewById(R.id.project_tv_point3_name);

            holder.projectManager = convertView.findViewById(R.id.project_manager);
            holder.projectCar = convertView.findViewById(R.id.project_car);
            holder.projectState = convertView.findViewById(R.id.project_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int detail_flag = -1;
        detail_flag = data.get(position).getDetail_flag();

        if (detail_flag != -1 && detail_flag ==1){
            holder.projectDetail.setVisibility(View.VISIBLE);
            holder.projectDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        String workflow_content_id = data.get(position).getWorkflow_content_id();
                        String wk_point_id = data.get(position).getNext_approval_list().get(0).getWorkflow_point_id();

                        Intent intent = new Intent();
                        intent.setClass(context, WorkDetailActivity.class);
                        intent.putExtra("workflow_content_id",workflow_content_id);
                        intent.putExtra("wk_point_id",wk_point_id);
                        intent.putExtra("date",data.get(position).getStart_time());
                        intent.putExtra("manager",data.get(position).getUser_name());
                        intent.putExtra("proc_type_id",data.get(position).getProc_type_id());
                        context.startActivity(intent);
                }
            });
        }else if (detail_flag != -1 && detail_flag == 0){
            holder.projectDetail.setVisibility(View.GONE);
        }

        int oper_flag= -1;
        oper_flag = data.get(position).getOper_flag();

        if (oper_flag == 3){
            holder.rlTop.setBackgroundColor(Color.parseColor("#e1e1e1"));//已办的颜色
            holder.llCenter.setBackgroundColor(Color.parseColor("#e1e1e1"));//已办的颜色
            holder.rlBottom.setBackgroundColor(Color.parseColor("#e1e1e1"));//已办的颜色
            changeTextColor(holder,false);
        }else
        if (oper_flag == 2){
            holder.rlTop.setBackgroundColor(Color.parseColor("#f7b732"));//驳回的颜色
            holder.llCenter.setBackgroundColor(Color.parseColor("#f7b732"));//驳回的颜色
            holder.rlBottom.setBackgroundColor(Color.parseColor("#f7b732"));//驳回的颜色
            changeTextColor(holder,true);
        }else if (oper_flag == 1){
            holder.rlTop.setBackgroundColor(Color.parseColor("#fdf6f0"));//拒件和接单的颜色
            holder.llCenter.setBackgroundColor(Color.parseColor("#fdf6f0"));//拒件和接单的颜色
            holder.rlBottom.setBackgroundColor(Color.parseColor("#fdf6f0"));//拒件和接单的颜色
            changeTextColor(holder,false);
        }else{
            holder.rlTop.setBackgroundColor(Color.parseColor("#ffffff"));//未接单的颜色
            holder.llCenter.setBackgroundColor(Color.parseColor("#ffffff"));//未接单的颜色
            holder.rlBottom.setBackgroundColor(Color.parseColor("#ffffff"));//未接单的颜色
            changeTextColor(holder,false);
        }

        int process_flag = -1;
        process_flag = data.get(position).getProcess_flag();

        if (process_flag != -1 && process_flag ==1){
            holder.projectWorkflow.setVisibility(View.VISIBLE);
            holder.projectWorkflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        String workflow_content_id = data.get(position).getWorkflow_content_id();
                        Intent intent = new Intent();
                        intent.setClass(context, WorkFlowActivity.class);
                        intent.putExtra("workflow_content_id", workflow_content_id);
                        intent.putExtra("date",data.get(position).getStart_time());
                        intent.putExtra("manager",data.get(position).getUser_name());
                        intent.putExtra("state",data.get(position).getStatus());
                        intent.putExtra("proc_type_id",data.get(position).getProc_type_id());
                        context.startActivity(intent);
                    }
            });
        }else if (process_flag != -1 && process_flag == 0){
            holder.projectWorkflow.setVisibility(View.GONE);
        }

        String proc_type = data.get(position).getProc_type_name();
        if (proc_type != null && proc_type.length() > 0){
            holder.tvProcType.setVisibility(View.VISIBLE);
            holder.tvProcType.setText(proc_type);
        }else{
            holder.tvProcType.setVisibility(View.INVISIBLE);
        }
        String provider = data.get(position).getDocking_com_name();
        if (provider != null && provider.length() > 0){
            holder.tvProjectProvider.setVisibility(View.VISIBLE);
            holder.tvProjectProvider.setText(provider);
        }else{
            holder.tvProjectProvider.setVisibility(View.INVISIBLE);
        }

        String refinance = data.get(position).getRefinance();
        if (refinance != null && refinance.length() > 0){
            holder.tvFinance.setVisibility(View.VISIBLE);
            holder.tvFinance.setText(refinance);
        }else{
            holder.tvFinance.setVisibility(View.INVISIBLE);
        }

        holder.projectRlPoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    goOperation(position,v);
            }
        });
        holder.projectRlPoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goOperation(position,v);
            }
        });
        holder.projectRlPoint3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOperation(position,v);
            }
        });

        holder.projectTime.setText(data.get(position).getStart_time());
        holder.projectCustomerName.setText(data.get(position).getCustomer_name());
        holder.projectIgPoint1 = convertView.findViewById(R.id.project_ig_point1);
        holder.projectIgPoint2 = convertView.findViewById(R.id.project_ig_point2);
        holder.projectIgPoint3 = convertView.findViewById(R.id.project_ig_point3);

        holder.projectManager.setText(data.get(position).getUser_name().trim());
        holder.projectCar.setText(data.get(position).getCar_type().trim());

        //直接获取状态内容，通过内容获取颜色，不在通过status的值来获取内容和颜色
//        String state = data.get(position).getStatus().trim();
//        QuestState questState = new QuestState();
//        holder.projectState.setText(questState.getStateString(state));
//        holder.projectState.setTextColor(questState.getStateColor(state));
//
        String status_name = data.get(position).getStatus_Name().trim();
        QuestState questState = new QuestState();
        holder.projectState.setText(status_name);
        holder.projectState.setTextColor(questState.getStatusNameColor(status_name));

        List<QuestListResponse.Result.Data.Next_approval_list> list = data.get(position).getNext_approval_list();


        holder.projectRlPoint1.setVisibility(View.INVISIBLE);
        holder.projectRlPoint2.setVisibility(View.INVISIBLE);
        holder.projectRlPoint3.setVisibility(View.INVISIBLE);

        if (list != null){
            switch (list.size()){
                case 1:
                    if (list.get(0).getWorkflow_name() != null && list.get(0).getWorkflow_name().length() >= 0){
                        holder.projectTvPoint1.setText(list.get(0).getWorkflow_name().trim());
                    }
                    if (list.get(0).getPoint_oper_name() != null && list.get(0).getPoint_oper_name().length() >= 0){
                        holder.projectTvPoint1Name.setText(list.get(0).getPoint_oper_name().trim());
                    }

                    if (list.get(0).getStatus() != null && list.get(0).getStatus().equals("2")){
                        holder.projectTvPoint1Name.setText("");
                        Glide.with(context).load(R.mipmap.continue_loan).into(holder.projectIgPoint1);
                    }else if (list.get(0).getStatus() != null && list.get(0).getStatus().equals("1")){
                        Glide.with(context).load(R.mipmap.project_ig_finished).into(holder.projectIgPoint1);
                    }else{
                        Glide.with(context).load(R.mipmap.project_ig_unfinished).into(holder.projectIgPoint1);
                    }

                    holder.projectRlPoint1.setVisibility(View.VISIBLE);

                    break;
                case 2:

                    if (list.get(0).getWorkflow_name() != null && list.get(0).getWorkflow_name().length() >= 0){
                        holder.projectTvPoint1.setText(list.get(0).getWorkflow_name().trim());
                    }
                    if (list.get(0).getPoint_oper_name() != null && list.get(0).getPoint_oper_name().length() >= 0){
                        holder.projectTvPoint1Name.setText(list.get(0).getPoint_oper_name().trim());
                    }

                    if (list.get(1).getWorkflow_name() != null && list.get(1).getWorkflow_name().length() >= 0){
                        holder.projectTvPoint2.setText(list.get(1).getWorkflow_name().trim());
                    }
                    if (list.get(1).getPoint_oper_name() != null && list.get(1).getPoint_oper_name().length() >= 0){
                        holder.projectTvPoint2Name.setText(list.get(1).getPoint_oper_name().trim());
                    }

                    if (list.get(0).getStatus() != null && list.get(0).getStatus().equals("2")){
                        holder.projectTvPoint1Name.setText("");
                        Glide.with(context).load(R.mipmap.continue_loan).into(holder.projectIgPoint1);
                    }else if (list.get(0).getStatus() != null && list.get(0).getStatus().equals("1")){
                        Glide.with(context).load(R.mipmap.project_ig_finished).into(holder.projectIgPoint1);
                    }else{
                        Glide.with(context).load(R.mipmap.project_ig_unfinished).into(holder.projectIgPoint1);
                    }

                    if (list.get(1).getStatus() != null && list.get(1).getStatus().equals("2")){
                        holder.projectTvPoint2Name.setText("");
                        Glide.with(context).load(R.mipmap.continue_loan).into(holder.projectIgPoint2);
                    }else if (list.get(1).getStatus() != null && list.get(1).getStatus().equals("1")){
                        Glide.with(context).load(R.mipmap.project_ig_finished).into(holder.projectIgPoint2);
                    }else{
                        Glide.with(context).load(R.mipmap.project_ig_unfinished).into(holder.projectIgPoint2);
                    }

                    holder.projectRlPoint1.setVisibility(View.VISIBLE);
                    holder.projectRlPoint2.setVisibility(View.VISIBLE);

                    break;
                case 3:
                    if (list.get(0).getWorkflow_name() != null && list.get(0).getWorkflow_name().length() >= 0){
                        holder.projectTvPoint1.setText(list.get(0).getWorkflow_name().trim());
                    }
                    if (list.get(0).getPoint_oper_name() != null && list.get(0).getPoint_oper_name().length() >= 0){
                        holder.projectTvPoint1Name.setText(list.get(0).getPoint_oper_name().trim());
                    }

                    if (list.get(1).getWorkflow_name() != null && list.get(1).getWorkflow_name().length() >= 0){
                        holder.projectTvPoint2.setText(list.get(1).getWorkflow_name().trim());
                    }
                    if (list.get(1).getPoint_oper_name() != null && list.get(1).getPoint_oper_name().length() >= 0){
                        holder.projectTvPoint2Name.setText(list.get(1).getPoint_oper_name().trim());
                    }

                    if (list.get(2).getWorkflow_name() != null && list.get(2).getWorkflow_name().length() >= 0){
                        holder.projectTvPoint3.setText(list.get(2).getWorkflow_name().trim());
                    }
                    if (list.get(2).getPoint_oper_name() != null && list.get(2).getPoint_oper_name().length() >= 0){
                        holder.projectTvPoint3Name.setText(list.get(2).getPoint_oper_name().trim());
                    }

                    if (list.get(0).getStatus() != null && list.get(0).getStatus().equals("2")){
                        holder.projectTvPoint1Name.setText("");
                        Glide.with(context).load(R.mipmap.continue_loan).into(holder.projectIgPoint1);
                    }else if (list.get(0).getStatus() != null && list.get(0).getStatus().equals("1")){
                        Glide.with(context).load(R.mipmap.project_ig_finished).into(holder.projectIgPoint1);
                    }else{
                        Glide.with(context).load(R.mipmap.project_ig_unfinished).into(holder.projectIgPoint1);
                    }

                    if (list.get(1).getStatus() != null && list.get(1).getStatus().equals("2")){
                        holder.projectTvPoint2Name.setText("");
                        Glide.with(context).load(R.mipmap.continue_loan).into(holder.projectIgPoint2);
                    }else if (list.get(1).getStatus() != null && list.get(1).getStatus().equals("1")){
                        Glide.with(context).load(R.mipmap.project_ig_finished).into(holder.projectIgPoint2);
                    }else{
                        Glide.with(context).load(R.mipmap.project_ig_unfinished).into(holder.projectIgPoint2);
                    }

                    if (list.get(2).getStatus() != null && list.get(2).getStatus().equals("2")){
                        holder.projectTvPoint3Name.setText("");
                        Glide.with(context).load(R.mipmap.continue_loan).into(holder.projectIgPoint3);
                    }else if (list.get(2).getStatus() != null && list.get(2).getStatus().equals("1")){
                        Glide.with(context).load(R.mipmap.project_ig_finished).into(holder.projectIgPoint3);
                    }else{
                        Glide.with(context).load(R.mipmap.project_ig_unfinished).into(holder.projectIgPoint3);
                    }

                    holder.projectRlPoint1.setVisibility(View.VISIBLE);
                    holder.projectRlPoint2.setVisibility(View.VISIBLE);
                    holder.projectRlPoint3.setVisibility(View.VISIBLE);
                    break;
            }
        }

        return convertView;
    }

    private void changeTextColor(ViewHolder v,boolean b) {
        if (b){
            if (v.projectIgIcon != null){
                Glide.with(context).load(R.mipmap.time_white_icon).into(v.projectIgIcon);
            }
            v.projectTime.setTextColor(Color.parseColor("#ffffff"));
            v.projectDetail.setTextColor(Color.parseColor("#ffffff"));
            v.projectWorkflow.setTextColor(Color.parseColor("#ffffff"));
            v.projectTvCustomerName.setTextColor(Color.parseColor("#ffffff"));
            v.projectCustomerName.setTextColor(Color.parseColor("#ffffff"));

            v.tvProjectProvider.setTextColor(Color.parseColor("#ffffff"));
            v.tvProcType.setTextColor(Color.parseColor("#ffffff"));
            v.tvFinance.setTextColor(Color.parseColor("#ffffff"));
            if (v.projectTvPoint1Name != null){
                v.projectTvPoint1Name.setTextColor(Color.parseColor("#ffffff"));
            }
            if (v.projectTvPoint2Name != null){
                v.projectTvPoint2Name.setTextColor(Color.parseColor("#ffffff"));
            }
            if (v.projectTvPoint3Name != null){
                v.projectTvPoint3Name.setTextColor(Color.parseColor("#ffffff"));
            }


            if (v.projectIgCustomer != null){
                Glide.with(context).load(R.mipmap.people_white_icon).into(v.projectIgCustomer);
            }
            v.projectTvManager.setTextColor(Color.parseColor("#ffffff"));
            v.projectManager.setTextColor(Color.parseColor("#ffffff"));
            v.projectCar.setTextColor(Color.parseColor("#ffffff"));

        }else{
            if (v.projectIgIcon != null){
                Glide.with(context).load(R.mipmap.project_ig_time).into(v.projectIgIcon);
            }
            v.projectTime.setTextColor(Color.parseColor("#333333"));
            v.projectDetail.setTextColor(Color.parseColor("#f7b732"));
            v.projectWorkflow.setTextColor(Color.parseColor("#f7b732"));
            v.projectTvCustomerName.setTextColor(Color.parseColor("#333333"));
            v.projectCustomerName.setTextColor(Color.parseColor("#333333"));

            v.tvProjectProvider.setTextColor(Color.parseColor("#333333"));
            v.tvProcType.setTextColor(Color.parseColor("#333333"));
            v.tvFinance.setTextColor(Color.parseColor("#333333"));
            if (v.projectTvPoint1Name != null){
                v.projectTvPoint1Name.setTextColor(Color.parseColor("#333333"));
            }
            if (v.projectTvPoint2Name != null){
                v.projectTvPoint2Name.setTextColor(Color.parseColor("#333333"));
            }
            if (v.projectTvPoint3Name != null){
                v.projectTvPoint3Name.setTextColor(Color.parseColor("#333333"));
            }


            if (v.projectIgCustomer != null){
                Glide.with(context).load(R.mipmap.project_ig_manager).into(v.projectIgCustomer);
            }
            v.projectTvManager.setTextColor(Color.parseColor("#333333"));
            v.projectManager.setTextColor(Color.parseColor("#333333"));
            v.projectCar.setTextColor(Color.parseColor("#333333"));}
    }

    private void goOperation(int position,View view){
        int index = -1;
        switch (view.getId()){
            case R.id.project_rl_point1:
                index = 0;
                break;
            case R.id.project_rl_point2:
                index = 1;
                break;
            case R.id.project_rl_point3:
                index = 2;
                break;
        }

        //继续
        if (position >= 0 && index >= 0){
            String wk_pot_status = data.get(position).getNext_approval_list().get(index).getStatus();
            String wk_point_id = data.get(position).getNext_approval_list().get(index).getWorkflow_point_id();
            String workflow_content_id = data.get(position).getWorkflow_content_id();
            String workflow_name = data.get(position).getNext_approval_list().get(index).getWorkflow_name();
            String proc_type_id = data.get(position).getProc_type_id();
            int role_flag = data.get(position).getNext_approval_list().get(index).getRole_flag();

            int point_oper_flag = data.get(position).getNext_approval_list().get(index).getPoint_oper_flag();

            if (wk_pot_status != null && wk_pot_status.equals("2") && "续贷".equals(workflow_name)){

                //通知QuestFragment显示续贷确认
                Intent intent = new Intent();
                intent.setAction("showAlert");
                intent.putExtra("type","refinance");
                intent.putExtra("workflow_content_id",workflow_content_id);
                intent.putExtra("wk_point_id",wk_point_id);
                intent.putExtra("proc_type_id",proc_type_id);
                context.sendBroadcast(intent);
                return;
            }
            if (wk_pot_status != null && wk_pot_status.equals("2") && "结清".equals(workflow_name)){

                //通知QuestFragment显示续贷确认
                Intent intent = new Intent();
                intent.setAction("showAlert");
                intent.putExtra("type","endflow");
                intent.putExtra("workflow_content_id",workflow_content_id);
                intent.putExtra("wk_point_id",wk_point_id);
                intent.putExtra("proc_type_id",proc_type_id);
                context.sendBroadcast(intent);
                return;
            }

            //如果该步骤已经完成，则不能进行操作
            if (!"".equals(wk_pot_status) && QuestState.isWPDone(wk_pot_status)){
                Toast.makeText(context, "这个操作已经完成", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("workflow_content_id",workflow_content_id);
            intent.putExtra("wk_point_id",wk_point_id);
            intent.putExtra("workflow_name",workflow_name);
            intent.putExtra("proc_type_id",proc_type_id);

            //面谈节点步骤ID,添加日期和经理
            //录入订单节点步骤ID,添加日期和经理
            if (wk_point_id != null &&
                    ("30".equals(wk_point_id) || "2".equals(wk_point_id)))
            {
                intent.putExtra("date",data.get(position).getStart_time());
                intent.putExtra("manager",data.get(position).getUser_name());
            }

            //定额节点步骤ID,添加进入方式，驳回的时候不让改数据
            //定额界面，抵押类型，最终定额界面不能进行重新上数据
            if (wk_point_id != null &&
                    ("6".equals(wk_point_id)||"10".equals(wk_point_id))){
                intent.putExtra("type","");
                intent.putExtra("way",data.get(position).getProc_type_name());
            }

            //业务反馈步骤ID,添加进入方式，驳回的时候不让改数据
            if (wk_point_id != null &&
                    ("7".equals(wk_point_id)||"11".equals(wk_point_id))){
                intent.putExtra("type","");
            }

            //记录操作位置
            if (context.operations.contains(wk_point_id)){
                context.itemPosition = position;
                context.itemIndex = index;
            }

            //如果可操作权限不等于登录权限，则不能进入
            if (role_flag == 1){
                //如果没有接单，点击步骤弹出接单确认窗
                if (point_oper_flag != 1){
                    intent.setAction("showAlert");
                    intent.putExtra("type","takeproject");
                    //打开局部刷新后，接单状态在需要更新
                    intent.putExtra("position",position );
                    intent.putExtra("index",index );
                    context.sendBroadcast(intent);
                }else{
                    intent.setClass(context,Activitys.getClass(wk_point_id));
                    context.startActivity(intent);
                }
            }else{
                Toast.makeText(context, "您没有操作权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
