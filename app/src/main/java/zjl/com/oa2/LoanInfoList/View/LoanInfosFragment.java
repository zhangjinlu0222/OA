package zjl.com.oa2.LoanInfoList.View;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Adapter.LoanInfosListAdapter;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.LoanInfoList.Model.LoanInfoListPresenterImpl;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoList;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoListPresenter;
import zjl.com.oa2.LoanInfoList.Presenter.ILoanInfoListView;
import zjl.com.oa2.Login.View.LoginActivity;
import zjl.com.oa2.Quest.View.QuestFragment;
import zjl.com.oa2.QuestAndSetting.View.QuestAndSetting;
import zjl.com.oa2.QuestAndSetting.View.SearchLoanInfoActivity;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.LoanInfosResponse;
import zjl.com.oa2.Utils.NetworkUtils;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanInfosFragment extends Fragment implements ILoanInfoListView, PullToRefreshBase.OnPullEventListener<ListView>, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.normal)
    Button normal;
    @Bind(R.id.overdue)
    Button overdue;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.carModel)
    TextView carModel;
    @Bind(R.id.carPrice)
    TextView carPrice;
    @Bind(R.id.startDate)
    TextView startDate;
    @Bind(R.id.loanInfos)
    PullToRefreshListView loanInfos;
    @Bind(R.id.rlCarModel)
    RelativeLayout rlCarType;
    @Bind(R.id.rlPrice)
    RelativeLayout rlCarPrice;
    @Bind(R.id.igOrder)
    ImageView mIgOrder;
    @Bind(R.id.rlDate)
    RelativeLayout rlDate;
    @Bind(R.id.igSearch)
    ImageView mIgSearch;
    @Bind(R.id.igSearchCancel)
    ImageView mIgSearchCancel;

    private QuestAndSetting questAndSetting;
    private Context context;
    /**加载提示框*/
    private BuildBean dialog;

    private ILoanInfoListPresenter mPresenter;

    private LoanInfosListAdapter adapter;

    private List<LoanInfosResponse.Result.LoanInfo> data = new ArrayList<>();

//    请求数据页数
    private int page_count = 1;

//    1代表正常，2代表逾期
    private String flag = "1";
//    1代表降序，0代表升序
    private int order = 1;

//    false表示不过滤，true表示过滤
    private boolean filter = false;

    private String token = "";

//    高级查找
    private static final int SearchAction = 10001;
//    查找项：姓名，起止日期
    private String search_name = "";
    private String search_start_date = "";
    private String search_end_date ="";

    public LoanInfosFragment() {
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
    public static LoanInfosFragment newInstance(String param1, String param2) {
        LoanInfosFragment fragment = new LoanInfosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_infos, container, false);
        ButterKnife.bind(this, view);

        normal.setOnClickListener(this);
        overdue.setOnClickListener(this);
        mIgOrder.setOnClickListener(this);
        rlDate.setOnClickListener(this);
        mIgSearch.setOnClickListener(this);
        mIgSearchCancel.setOnClickListener(this);

        token = UserInfo.getInstance(context).getUserInfo("token");

        questAndSetting = (QuestAndSetting) getActivity();
        context = questAndSetting;

        dialog = DialogUIUtils.showLoading(context,"加载中",true,false,false,true);

        adapter = new LoanInfosListAdapter(context,data,loanInfos);
        loanInfos.setAdapter(adapter);
        loanInfos.setOnPullEventListener(this);

        mPresenter = new LoanInfoListPresenterImpl(this);

        updateOrderImage(order);
        updateSearchFilter(filter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        从高级搜索返回不进行新的请求
        if (!filter){
            requestData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            this.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.hideProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
    public void onFailure(String msg) {
        Toast.makeText(getActivity(),msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadLoanInfos(LoanInfosResponse.Result result) {

        if(result == null){
            showFailureMsg("暂无数据");
            return;
        }

        List<LoanInfosResponse.Result.LoanInfo> loanInfos = result.getData();

        if (loanInfos == null || loanInfos.size() <=0 ){
            showFailureMsg("暂无数据");
            return;
        }

        String carType = loanInfos.get(0).getCar_type();

        if (carType == null || carType.length() <= 0){
            rlCarType.setVisibility(View.GONE);
        }else{
            rlCarType.setVisibility(View.VISIBLE);
        }

        rlCarType.requestLayout();

        this.data.clear();
        this.data.addAll(loanInfos);
        this.adapter.notifyDataSetChanged();
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
    public void saveOperationState(String wk_pot_id) {

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.normal, R.id.overdue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.normal:
                break;
            case R.id.overdue:
                break;
        }
    }

    @Override
    public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
        if (direction == PullToRefreshBase.Mode.PULL_FROM_START && state == PullToRefreshBase.State.RELEASE_TO_REFRESH){

            if (!getNetState()){
                questAndSetting.showNetError();
                return;
            }

            page_count = 1;
            requestData();

        }else if (direction == PullToRefreshBase.Mode.PULL_FROM_END && state == PullToRefreshBase.State.RELEASE_TO_REFRESH){

            if (!getNetState()){
                questAndSetting.showNetError();
                return;
            }

            page_count++;
            requestData();
        }
    }


    public boolean getNetState() {
        return NetworkUtils.isNetworkConnected(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.normal:
                normal.setTextColor(Color.parseColor("#FFFFFF"));
                normal.setBackgroundResource(R.drawable.border_blue_tl_bl);
                overdue.setTextColor(Color.parseColor("#A5A5A5"));
                overdue.setBackgroundResource(R.drawable.border_white_tr_br);

                flag = "1";
                page_count = 1;

                startDate.setText("起租日期");
                rlCarType.setVisibility(View.VISIBLE);

                requestData();

                break;
            case R.id.overdue:
                normal.setTextColor(Color.parseColor("#A5A5A5"));
                normal.setBackgroundResource(R.drawable.border_white_tl_bl);
                overdue.setTextColor(Color.parseColor("#FFFFFF"));
                overdue.setBackgroundResource(R.drawable.border_blue_tr_br);

                flag = "2";
                page_count = 1;

                startDate.setText("逾期期数");
                rlCarType.setVisibility(View.GONE);
                requestData();

                break;
            case R.id.rlDate:

                if (order == 1){
                    order = 0;
                }else if (order == 0){
                    order = 1;
                }

                updateOrderImage(order);

                requestData();
                break;
            case R.id.igSearch:

                filter = true;

                updateSearchFilter(filter);

                toSearchActivity();
                break;
            case R.id.igSearchCancel:

                filter = false;

                updateSearchFilter(filter);

//                flag不变，数据重置，重新请求当前类型的数据
                order = 1;
                page_count = 1;

                search_start_date = "";
                search_end_date = "";
                search_name = "";

                requestData();
                break;
        }
    }

    private void updateOrderImage(int order){
        if (order == 1){
            mIgOrder.setBackgroundResource(R.mipmap.order_down);
        }else if (order == 0){
            mIgOrder.setBackgroundResource(R.mipmap.order_up);
        }
    }
    private void updateSearchFilter(boolean filter){
        if (filter){
            mIgSearchCancel.setVisibility(View.VISIBLE);
        }else{
            mIgSearchCancel.setVisibility(View.GONE);
        }
    }

    private void toSearchActivity(){
        Intent intent = new Intent(getActivity(), SearchLoanInfoActivity.class);
        intent.putExtra("flag",flag);
        startActivityForResult(intent,SearchAction);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SearchAction && resultCode == RESULT_OK){
            filter = true;
            search_name = data.getStringExtra("name");
            search_start_date = data.getStringExtra("start_date");
            search_end_date = data.getStringExtra("end_date");

            this.data.clear();
            this.adapter.notifyDataSetChanged();

            requestData();
        }else{
            filter = false;
        }

        updateSearchFilter(filter);
    }

    private void requestData(){
        if (mPresenter != null){
            mPresenter.LoanInfoList(token,page_count ,search_start_date ,
                    search_end_date, search_name, flag, order);
        }
    }
}
