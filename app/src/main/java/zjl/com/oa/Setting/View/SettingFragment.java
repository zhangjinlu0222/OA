package zjl.com.oa.Setting.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.QuestAndSetting.View.QuestAndSetting;
import zjl.com.oa.R;
import zjl.com.oa.Response.UserInfoResponse;
import zjl.com.oa.Setting.Model.SettingPresenterImpl;
import zjl.com.oa.Setting.PayBack.View.PayBackActivity;
import zjl.com.oa.Setting.Presenter.ISettingView;
import zjl.com.oa.Utils.VersionUtils;

/**
 */
public class SettingFragment extends Fragment implements ISettingView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.setting_ig_sex)
    ImageView settingIgSex;
    @Bind(R.id.top_name)
    TextView topname;
    @Bind(R.id.top_dep_name)
    TextView topdepname;
    @Bind(R.id.setting_tv_name)
    TextView settingTvName;
    @Bind(R.id.setting_name)
    TextView settingName;
    @Bind(R.id.setting_ig_right)
    ImageView settingIgRight;
    @Bind(R.id.setting_tv_department)
    TextView settingTvDepartment;
    @Bind(R.id.setting_department)
    TextView settingDepartment;
    @Bind(R.id.setting_ig_right2)
    ImageView settingIgRight2;
    @Bind(R.id.setting_tv_phone)
    TextView settingTvPhone;
    @Bind(R.id.setting_phone)
    TextView settingPhone;
    @Bind(R.id.setting_ig_right3)
    ImageView settingIgRight3;
    @Bind(R.id.setting_tv_fixLoginPwd)
    TextView settingTvFixLoginPwd;
    @Bind(R.id.setting_tv_bangke)
    TextView settingTvBangKe;
    @Bind(R.id.setting_tv_payback)
    TextView settingTvPayBack;
    @Bind(R.id.rl_payback)
    RelativeLayout rlPayBack;
    @Bind(R.id.version)
    TextView version;
    @Bind(R.id.setting_ig_right4)
    ImageView settingIgRight4;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.sb_update)
    SwitchButton sbUpdate;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private SettingPresenterImpl logoutPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        logoutPresenter = new SettingPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);

        init();

        String token = UserInfo.getInstance(getContext()).getUserInfo(UserInfo.TOKEN);
        logoutPresenter.getUserInfo(token);

        sbUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    UserInfo.getInstance(getContext()).setUserInfo(UserInfo.AUTOUPDATE,"1");
                }else{
                    UserInfo.getInstance(getContext()).setUserInfo(UserInfo.AUTOUPDATE,"0");
                    UserInfo.getInstance(getContext()).setUserInfo(UserInfo.OPERATION,"");
                    UserInfo.getInstance(getContext()).setUserInfo(UserInfo.OPERATIONSTATE,"");
                }
            }
        });
        return view;
    }

    private void init() {
        String name = UserInfo.getInstance(getContext()).getUserInfo(UserInfo.TRUE_NAME);
        String dep_name = UserInfo.getInstance(getContext()).getUserInfo(UserInfo.DEP_NAME);
        String phone = UserInfo.getInstance(getContext()).getUserInfo(UserInfo.PHONE);
        String isAutoUpdate = UserInfo.getInstance(getContext()).getUserInfo(UserInfo.AUTOUPDATE);
        String versionCode = VersionUtils.getLocalVersion(getContext());

        topname.setText(name);
        settingName.setText(name);
        topdepname.setText(dep_name);
        settingDepartment.setText(dep_name);
        settingPhone.setText(phone);

        if (isAutoUpdate != null && isAutoUpdate.equals("1")){
            sbUpdate.setChecked(true);
        }else{
            sbUpdate.setChecked(false);
        }

        version.setText(versionCode);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.setting_ig_sex, R.id.setting_tv_name, R.id.setting_name, R.id.setting_ig_right,
            R.id.setting_tv_department, R.id.setting_department, R.id.setting_ig_right2,
            R.id.setting_tv_phone, R.id.setting_phone, R.id.setting_ig_right3,
            R.id.setting_tv_fixLoginPwd, R.id.setting_ig_right4, R.id.button2,
            R.id.setting_ig_right7,R.id.setting_tv_bangke,
            R.id.setting_ig_right8,R.id.setting_tv_payback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_ig_sex:
                break;
            case R.id.setting_tv_name:
                break;
            case R.id.setting_name:
                break;
            case R.id.setting_ig_right:
                break;
            case R.id.setting_tv_department:
                break;
            case R.id.setting_department:
                break;
            case R.id.setting_ig_right2:
                break;
            case R.id.setting_tv_phone:
                break;
            case R.id.setting_phone:
                break;
            case R.id.setting_ig_right7:
            case R.id.setting_tv_bangke:
                Intent bangke = new Intent(getActivity(),BangkeActivity.class);
                String phone = UserInfo.getInstance(getContext()).getUserInfo(UserInfo.PHONE);
                bangke.putExtra("phone", phone);
                startActivity(bangke);
                break;
            case R.id.setting_ig_right8:
            case R.id.setting_tv_payback:
                Intent toPayBackActivity = new Intent(getActivity(),PayBackActivity.class);
                startActivity(toPayBackActivity);
                break;
            case R.id.setting_ig_right3:
            case R.id.setting_tv_fixLoginPwd:
                Intent intent = new Intent(getActivity(),ModifyPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_ig_right4:
                break;
            case R.id.button2:
                logoutPresenter.logout(UserInfo.getInstance(getActivity()).getUserInfo(UserInfo.TOKEN));
                UserInfo.getInstance(getActivity()).cleanUserInfo();
                //发送退出登录请求后，主动返回，不管是否得到反馈信息
                this.toMainActivity();
                break;
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void relogin() {

    }

    @Override
    public void saveUserInfo(UserInfoResponse.Data data){
        String schedule_flag = data.getSchedule_flag();

        UserInfo.getInstance(getContext()).setUserInfo(UserInfo.SCHEDULEFLAG,schedule_flag);

        if ("1".equals(schedule_flag) && rlPayBack != null){
            rlPayBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void toMainActivity() {
        QuestAndSetting Activity = (QuestAndSetting) getActivity();
        if (Activity != null){
            Activity.finish();
        }
    }

    @Override
    public void saveOperationState(String wk_pot_id) {

    }

    @Override
    public void showFailureMsg(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isUploadTypeAdd() {
        return false;
    }

    @Override
    public void onDestory() {
        logoutPresenter.onDestory();
    }

    @Override
    public void refreshToken(String arg) {

    }
}
