package zjl.com.oa2.Quest.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luck.picture.lib.entity.LocalMedia;
import com.mob.wrappers.UMSSDKWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Adapter.QuestListAdapter;
import zjl.com.oa2.ApplicationConfig.Activitys;
import zjl.com.oa2.ApplicationConfig.Authority;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.Login.View.LoginActivity;
import zjl.com.oa2.Quest.Model.QuestListPresenterImpl;
import zjl.com.oa2.Quest.Presenter.IQuestListPresenter;
import zjl.com.oa2.Quest.Presenter.IQuestListView;
import zjl.com.oa2.QuestAndSetting.View.QuestAndSetting;
import zjl.com.oa2.QuestAndSetting.View.SearchActivity;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.QuestListResponse;
import zjl.com.oa2.Utils.NetworkUtils;

import static android.app.Activity.RESULT_OK;

public class QuestFragment extends Fragment implements IQuestListView, PullToRefreshBase.OnPullEventListener<ListView> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.project_btn_new)
    Button projectBtnNew;
    @Bind(R.id.project_tv_title)
    TextView projectTvTitle;
    @Bind(R.id.project_search_cancel)
    ImageView projectSearchCancel;
    @Bind(R.id.project_ig_search)
    ImageView projectIgSearch;
    @Bind(R.id.project_tv_default)
    TextView projectTvDefault;
    @Bind(R.id.project_tv_status)
    TextView projectTvStatus;
    @Bind(R.id.project_status_order)
    ImageView projectStatusOrder;
    @Bind(R.id.project_tv_type)
    TextView projectTvType;
    @Bind(R.id.project_type_order)
    ImageView projectTypeOrder;
    @Bind(R.id.project_tv_time)
    TextView projectTvTime;
    @Bind(R.id.project_time_order)
    ImageView projectTimeOrder;
    @Bind(R.id.quests)
    PullToRefreshListView quests;
    private Context context;
    private IQuestListPresenter questListPresenter;
    private QuestListAdapter adapter;
    private List<QuestListResponse.Result.Data> data = new ArrayList<>();
    private String token;
    private Set<String> roleset;
    private int currentPageCount = 1; //请求从第一页开始
    /**
     * 0, 默认排序
     * 1，业务状态排序
     * 2，业务类型排序
     * 3，时间排序
     */
    private int currentFliter = 0;
    /*
     * false,升序
     * true，降序*/
    private boolean currentOrder = false;
    /*
    * Order1到4分别对应四中类型的排序**/
    private boolean Order1 = false;
    private boolean Order2 = false;
    private boolean Order3 = false;
    private boolean Order4 = false;

    /**
     * 高级搜索标志
     * false，未启用
     * true，启用*/
    private boolean highFilter = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private alertDialogReceiver receiver;
    /**接单提示框*/
    private BuildBean acceptDialog;
    /**加载提示框*/
    private BuildBean dialog;

    private QuestAndSetting questAndSetting;
    private static final int SearchAction = 10001;

    /**
     * 高级搜索项*/
    private String name;
    private String startdate;
    private String enddate;
    private String type;
    private String status;
    private boolean showRefinance = true;
    private int w_con_id;
    private int w_pot_id;
    private String date,manager,way,stepInType;
    private String workflow_name;
    private int scrollPosition;

    private String proc_type_id;


    public QuestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestFragment newInstance(String param1, String param2) {
        QuestFragment fragment = new QuestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quest, container, false);
        ButterKnife.bind(this, view);

        questAndSetting = (QuestAndSetting) getActivity();
        context = questAndSetting;

        roleset = UserInfo.getInstance(context).getUserInfoRoleId(UserInfo.ROLE_ID);

        questListPresenter = new QuestListPresenterImpl(this);

        adapter = new QuestListAdapter(data, questAndSetting, quests);
        quests.setAdapter(adapter);
        quests.setOnPullEventListener(this);

        receiver = new alertDialogReceiver();
        IntentFilter intentFilter = new IntentFilter("showAlert");
        context.registerReceiver(receiver, intentFilter);

        dialog = DialogUIUtils.showLoading(context,"加载中",true,false,false,true);

        currentFliter = 1;
        currentPageCount = 1;
        currentOrder = true;
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //防止密码修改完成，token过期的问题
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        /**
         * 从高级搜索返回，则不需要刷新数据*/
        if (highFilter){
            return;
        }

        //在进入节点操作后，返回后滚动到操作位置
        String autoUpdate = UserInfo.getInstance(getActivity()).getUserInfo(UserInfo.AUTOUPDATE);
        if (autoUpdate != null && autoUpdate.equals("1")){
            //在操作完成后，更新操作item的内容
            String wk_pot_id = UserInfo.getInstance(context).getUserInfo(UserInfo.OPERATION);
            String state = UserInfo.getInstance(context).getUserInfo(UserInfo.OPERATIONSTATE);
            if (questAndSetting.operations.contains(wk_pot_id) ||
                    questAndSetting.workcheck.contains(wk_pot_id)){

                int pos = questAndSetting.itemPosition;
                int index  = questAndSetting.itemIndex;
                if (pos != -1 && index != -1 && state.equals("1")){
                    data.get(pos).getNext_approval_list().get(index).setStatus("1");
                    String optName = UserInfo.getInstance(getActivity()).getUserInfo(UserInfo.TRUE_NAME);
                    data.get(pos).getNext_approval_list().get(index).setPoint_oper_name(optName);
                    adapter.notifyDataSetChanged();
                    questAndSetting.itemPosition = -1;
                    questAndSetting.itemIndex = -1;
                }
                //更新完成后清理原先数据
                UserInfo.getInstance(context).setUserInfo(UserInfo.OPERATION,"" );
                UserInfo.getInstance(context).setUserInfo(UserInfo.OPERATIONSTATE,"" );
                //滚动到操作位置
                scrollPosition = quests.getRefreshableView().getFirstVisiblePosition();
                quests.getRefreshableView().smoothScrollToPosition(scrollPosition + 1);
            }else{
                currentFliter = 1;
                currentPageCount = 1;
                currentOrder = true;
                questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
            }
        }else{
            currentFliter = 1;
            currentPageCount = 1;
            currentOrder = true;
            questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            this.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hideProgress();
        //在进入节点操作后，返回后是否滚动到操作位置
        String autoUpdate = UserInfo.getInstance(getActivity()).getUserInfo(UserInfo.AUTOUPDATE);
        if (autoUpdate != null && autoUpdate.equals("1")){
            scrollPosition = quests.getRefreshableView().getFirstVisiblePosition();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        context.unregisterReceiver(receiver);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void refreshView() {

        if (projectStatusOrder != null){
            Glide.with(context).load(R.mipmap.order_unselected).into(projectStatusOrder);
        }

        if (projectTypeOrder != null){
            Glide.with(context).load(R.mipmap.order_unselected).into(projectTypeOrder);
        }

        if (projectTimeOrder != null){
            Glide.with(context).load(R.mipmap.order_unselected).into(projectTimeOrder);
        }

        switch (currentFliter){
            case 1:
                break;
            case 2:
                if (projectStatusOrder != null){
                    if (currentOrder){
                        Glide.with(context).load(R.mipmap.order_down).into(projectStatusOrder);
                    }else{
                        Glide.with(context).load(R.mipmap.order_up).into(projectStatusOrder);
                    }
                }
                break;
            case 3:
                if (projectTimeOrder != null){
                    if (currentOrder){
                        Glide.with(context).load(R.mipmap.order_down).into(projectTimeOrder);
                    }else{
                        Glide.with(context).load(R.mipmap.order_up).into(projectTimeOrder);
                    }
                }
                break;

            case 4:
                if (projectTypeOrder != null){
                    if (currentOrder){
                        Glide.with(context).load(R.mipmap.order_down).into(projectTypeOrder);
                    }else{
                        Glide.with(context).load(R.mipmap.order_up).into(projectTypeOrder);
                    }
                }
                break;
        }
    }


    @Override
    public void QuestListRefresh(QuestListResponse.Result result) {
//        //下拉刷新的时候，数据清空，上拉加载的时候数据源添加数据
        if (currentPageCount == 1) {
            data.clear();
        }
        if (result != null && result.getData().size() > 0) {
            data.addAll(result.getData());
        }
        adapter.notifyDataSetChanged();

        if (result != null) {
            int create_wf = result.getCreate_wf();
            if (projectBtnNew != null) {
                if (create_wf == 1) {
                    projectBtnNew.setVisibility(View.VISIBLE);
                } else {
                    projectBtnNew.setVisibility(View.GONE);
                }
            }
        } else {
            roleset = UserInfo.getInstance(context).getUserInfoRoleId(UserInfo.ROLE_ID);
            this.previlege(roleset);
        }

        //更新tab状态显示
        refreshView();

        /**
         *若是第一页，如果不是在顶部则滚动到顶部 */
        if (currentPageCount == 1 && quests != null){
            ListView mlist = quests.getRefreshableView();
            if (!(mlist).isStackFromBottom()) {
                mlist.setStackFromBottom(true);
            }
            mlist.setStackFromBottom(false);
        }
    }

    @Override
    public void QuestListRefreshError() {
        if (currentPageCount >= 2){
            currentPageCount--;
        }
        //请求数据失败的时候，重新判断新建的权限
        roleset = UserInfo.getInstance(context).getUserInfoRoleId(UserInfo.ROLE_ID);
        this.previlege(roleset);
    }

    @Override
    public void toVisitorAcvitity(Context context) {
        Intent intent = new Intent(context, Activitys.getClass("1"));
        intent.putExtra("workflow_content_id", "0");
        intent.putExtra("wk_point_id", "1");
        intent.putExtra("workflow_name", "来访");
        intent.putExtra("proc_type_id", "0");
        startActivity(intent);
    }

    public void toSearchAcvitity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        if (highFilter){
            intent.putExtra("name",name);
            intent.putExtra("startdate",startdate);
            intent.putExtra("enddate",enddate);
            intent.putExtra("type",type);
            intent.putExtra("status",status);
            intent.putExtra("showRefinance",showRefinance);
        }
        startActivityForResult(intent, SearchAction);
    }

    @Override
    public void noData() {
        this.showFailureMsg("没有数据");
        data.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noMoreData() {
        this.showFailureMsg("没有更多数据");
    }

    @Override
    public void previlege(Set<String> role) {
        if (projectBtnNew != null) {
            if (role.contains(Authority.YWJL) || role.contains(Authority.ADMIN)) {
                projectBtnNew.setVisibility(View.VISIBLE);
            } else {
                projectBtnNew.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public void loadNewData() {
        /**
         * 加载新数据之前，清楚原有数据，但是不通知适配器去刷新
         * */
        currentPageCount = 1;

        if (questListPresenter != null){

            if (!getNetState()){
                questAndSetting.showNetError();
                return;
            }
            if (highFilter){
                questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                        currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
            }else{
                questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
            }
        }
    }

    @Override
    public void stepIn() {
        if (w_con_id == 0 || w_pot_id == 0){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("workflow_content_id",w_con_id+"");
        intent.putExtra("wk_point_id",w_pot_id+"");
        intent.putExtra("workflow_name",workflow_name+"");
        intent.putExtra("proc_type_id",proc_type_id);

        //录入订单节点步骤ID,添加日期和经理
        if (w_pot_id == 30 )
        {
            intent.putExtra("date",date);
            intent.putExtra("manager",manager);
        }

        //定额节点步骤ID,添加进入方式，驳回的时候不让改数据
        //定额界面，抵押类型，最终定额界面不能进行重新上数据
        if (w_pot_id == 6 || w_pot_id == 10){
            intent.putExtra("type","");
            intent.putExtra("way",way);
        }
        intent.setClass(context,Activitys.getClass(w_pot_id+""));
        context.startActivity(intent);
    }

    @Override
    public void showProgress() {
        if (dialog != null){
            dialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (dialog != null){
            DialogUIUtils.dismiss(dialog);
        }
    }

    @Override
    public void relogin() {
        /**
         * 重新登录，删除TOKEN
         */
        UserInfo.getInstance(context).cleanUserInfo();

        showFailureMsg(Constant.Action_Login);
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void toMainActivity() {
    }

    @Override
    public void showFailureMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isUploadTypeAdd() {
        return false;
    }

    @Override
    public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
        if (direction == PullToRefreshBase.Mode.PULL_FROM_START && state == PullToRefreshBase.State.RELEASE_TO_REFRESH){

            if (!getNetState()){
                questAndSetting.showNetError();
                return;
            }

            currentPageCount = 1;

            if (highFilter){
                questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                        currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
            }else{
                questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
            }
        }else if (direction == PullToRefreshBase.Mode.PULL_FROM_END && state == PullToRefreshBase.State.RELEASE_TO_REFRESH){

            if (!getNetState()){
                questAndSetting.showNetError();
                return;
            }

            currentPageCount++;

            if (highFilter){
                questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                        currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
            }else{
                questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
            }
        }
    }

    public void showAlertDialog(String type,int w_con_id, int w_pot_id,String proc_type_id,int position,int index) {

        View rootView = View.inflate(context, R.layout.workflow_reedit_dialog, null);
        acceptDialog = DialogUIUtils.showCustomAlert(context, rootView);


        TextView title = rootView.findViewById(R.id.title);

        if ("refinance".equals(type)){
            title.setText("确认续贷");
        }
        if ("takeproject".equals(type)){
            title.setText("确认开始执行");
        }
        if ("endflow".equals(type)){
            title.setText("结清");
        }

        TextView cancel = rootView.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(acceptDialog);
                acceptDialog = null;
            }
        });

        TextView confirm = rootView.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(acceptDialog);
                /**
                 * 弹框在关闭后设置为null，防止多次显示
                 */
                acceptDialog = null;
                if (!getNetState()) {
                    questAndSetting.showNetError();
                    return;
                }
                if (dialog != null){
                    dialog.msg = "确认中";
                }

                if ("refinance".equals(type)){
                    questListPresenter.CreateRefinance(token, w_con_id);
                }
                if ("takeproject".equals(type)){
                    questListPresenter.PointOpertState(token, w_con_id, w_pot_id,proc_type_id);
                }
                if ("endflow".equals(type)){
                    questListPresenter.CloseFlow(token, w_con_id);
                }

                //操作完成后更新本地数据,并将标记清除

                if (position != -1 && index != -1){
                    adapter.data.get(position).getNext_approval_list().get(index).setPoint_oper_flag(1);
                    String optName = UserInfo.getInstance(getActivity()).getUserInfo(UserInfo.TRUE_NAME);
                    adapter.data.get(position).getNext_approval_list().get(index).setPoint_oper_name(optName);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        if (acceptDialog!= null){
            acceptDialog.show();
        }
    }

    @OnClick({R.id.project_btn_new, R.id.project_tv_title, R.id.project_search_cancel, R.id.project_ig_search, R.id.project_tv_default, R.id.project_tv_status, R.id.project_status_order, R.id.project_tv_type, R.id.project_type_order, R.id.project_tv_time, R.id.project_time_order, R.id.quests})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.project_btn_new:
                toVisitorAcvitity(context);
                break;
            case R.id.project_tv_title:
                break;
            case R.id.project_ig_search:
                toSearchAcvitity(context);
                break;
            case R.id.project_search_cancel://取消高级搜索
                highFilter = false;
                updateHighFilterView(highFilter);

                currentFliter = 1;
                currentPageCount = 1;
                currentOrder = true;
                Order1 = Order2 = Order3 = Order4 = false;
                if (dialog != null){
                    dialog.msg = "加载中";
                }
                questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
//                refreshView();
                break;
            case R.id.project_tv_default:


                if (!getNetState()){
                    questAndSetting.showNetError();
                    return;
                }

                currentFliter = 1;
                currentPageCount = 1;
                currentOrder = true;
                Order1 = Order2 = Order3 = Order4 = false;
                if (dialog != null){
                    dialog.msg = "加载中";
                }
                if (highFilter){
                    questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                            currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
                }else{
                    questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
                }
//                refreshView();
                break;
            case R.id.project_tv_status:
            case R.id.project_status_order:


                if (!getNetState()){
                    questAndSetting.showNetError();
                    return;
                }

                currentFliter = 2;
                currentPageCount = 1;
                currentOrder = Order2 = !Order2;
                Order1 =  Order3 = Order4 = false;
                if (dialog != null){
                    dialog.msg = "加载中";
                }
                if (highFilter){
                    questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                            currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
                }else {
                    questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
                }
//                refreshView();
                break;
            case R.id.project_tv_type:
            case R.id.project_type_order:

                if (!getNetState()){
                    questAndSetting.showNetError();
                    return;
                }

                currentFliter = 4;
                currentPageCount = 1;
                currentOrder = Order3 = !Order3;
                Order1 = Order2 =  Order4 = false;
                if (dialog != null){
                    dialog.msg = "加载中";
                }
                if (highFilter){
                    questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                            currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
                }else{
                    questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
                }
//                refreshView();
                break;
            case R.id.project_tv_time:
            case R.id.project_time_order:

                if (!getNetState()){
                    questAndSetting.showNetError();
                    return;
                }

                currentFliter = 3;
                currentPageCount = 1;
                currentOrder = Order4 = !Order4;
                Order1 = Order2 = Order3 = false;
                if (dialog != null){
                    dialog.msg = "加载中";
                }
                if (highFilter){
                    questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                            currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
                }else{
                    questListPresenter.WorkflowListOrder(token, currentFliter, currentPageCount, currentOrder);
                }
//                refreshView();
                break;
            case R.id.quests:
                break;
        }
    }

    public class alertDialogReceiver extends BroadcastReceiver {

        public void onReceive(Context arg0, Intent intent) {
            //接收发送过来的广播内容
            String type = intent.getStringExtra("type");
            w_con_id = Integer.parseInt(intent.getStringExtra("workflow_content_id"));
            w_pot_id = Integer.parseInt(intent.getStringExtra("wk_point_id") == null? "0":intent.getStringExtra("wk_point_id"));
            workflow_name = intent.getStringExtra("workflow_name");

            proc_type_id = intent.getStringExtra("proc_type_id");

            //录入订单节点步骤ID,需要日期和经理
            if (w_pot_id == 30) {
                date = intent.getStringExtra("date");
                manager = intent.getStringExtra("manager");
            }

            //定额节点步骤ID,进入方式，驳回的时候不让改数据
            //定额界面，抵押类型，最终定额界面不能进行重新上数据
            if (w_pot_id == 6 || w_pot_id == 10){
                stepInType = intent.getStringExtra("type");
                way = intent.getStringExtra("way");
            }
            //接单的时候传入位置，接单成功后刷新本地数据
            questAndSetting.itemPosition = intent.getIntExtra("position",-1);
            questAndSetting.itemIndex = intent.getIntExtra("index",-1);
            showAlertDialog(type,w_con_id, w_pot_id,proc_type_id,questAndSetting.itemPosition,questAndSetting.itemIndex);
        }
    }

    public boolean getNetState() {
        return NetworkUtils.isNetworkConnected(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SearchAction && resultCode == RESULT_OK) {
            highFilter = true;

            name = data.getStringExtra("name");
            startdate = data.getStringExtra("startdate");
            enddate = data.getStringExtra("enddate");
            type = data.getStringExtra("type");
            status = data.getStringExtra("status");
            showRefinance = data.getBooleanExtra("showRefinance",true);


            currentFliter = 1;
            currentPageCount = 1;
            currentOrder = Order1 = false;

            Order2 = Order3 = Order4 = false;
            if (dialog != null){
                dialog.msg = "加载中";
            }
            if (questListPresenter != null){
                questListPresenter.WorkflowListAdvPage(token,status,type,currentPageCount,name,
                        currentFliter,currentOrder,startdate,enddate,showRefinance?0:1);
            }
        }else{
            highFilter = false;
        }
        /**
         * 刷新排序界面*/
//        refreshView();
        /**
         *刷新高级搜索界面*/
        updateHighFilterView(highFilter);
    }

    private void updateHighFilterView(boolean flag){
        if (flag){
            projectSearchCancel.setVisibility(View.VISIBLE);
        }else{
            projectSearchCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void saveOperationState(String state) {
    }
}
