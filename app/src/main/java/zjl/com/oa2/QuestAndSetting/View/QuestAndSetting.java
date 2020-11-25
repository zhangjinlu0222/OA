package zjl.com.oa2.QuestAndSetting.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa2.Appraisal.View.AppraisalFragment;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Quest.View.QuestFragment;
import zjl.com.oa2.R;
import zjl.com.oa2.Setting.View.SettingFragment;
import zjl.com.oa2.Utils.NetworkUtils;
import zjl.com.oa2.Utils.TitleBarUtil;

public class QuestAndSetting extends BaseActivity {
    @Bind(R.id.ig_project_list)
    ImageView igProjectList;
    @Bind(R.id.tv_project_list)
    TextView tvProjectList;
    @Bind(R.id.quests)
    RelativeLayout quests;
    @Bind(R.id.ig_setting)
    ImageView igSetting;
    @Bind(R.id.tv_setting)
    TextView tvSetting;
    @Bind(R.id.setting)
    RelativeLayout setting;
    @Bind(R.id.navigation)
    BottomNavigationView navigation;
    @Bind(R.id.container)
    RelativeLayout container;
    @Bind(R.id.ig_appraisal_list)
    ImageView igAppraisalList;
    @Bind(R.id.tv_appraisal_list)
    TextView tvAppraisalList;
    @Bind(R.id.appraisal)
    RelativeLayout appraisal;
    private FragmentManager fragmentManager;
    private FrameLayout content;
    private QuestFragment Quest;
    private SettingFragment Setting;
    private AppraisalFragment Appraisal;
    public int currentIndex;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentIndex = 0;
                    setNav(currentIndex);
                    return true;
                case R.id.navigation_dashboard:
                    currentIndex = 1;
                    setNav(currentIndex);
                    return true;
            }
            return false;
        }
    };

    //自动刷新,包含如下步骤：放款结束，通知财务，通知签约，合同详情续贷
    public List<String> operations = Arrays.asList("22","20","13","27");
    public List<String> workcheck = Arrays.asList("workflow","workdetail");
    public int itemPosition = -1,itemIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_and_setting);
        ButterKnife.bind(this);

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        currentIndex = 1;
        setNav(currentIndex);
        updateView(currentIndex);
    }

    private void setNav(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                if (Quest != null) {
                    fragmentTransaction.show(Quest);
                } else {
                    Quest = QuestFragment.newInstance("1", "2");
                    fragmentTransaction.add(R.id.content, Quest);
                }
                break;
            case 1:
                if (Setting != null) {
                    fragmentTransaction.show(Setting);
                } else {
                    Setting = SettingFragment.newInstance("1", "2");
                    fragmentTransaction.add(R.id.content, Setting);
                }
                break;
            case 2:
                if (Appraisal != null) {
                    fragmentTransaction.show(Appraisal);
                } else {
                    Appraisal = AppraisalFragment.newInstance("1", "2");
                    fragmentTransaction.add(R.id.content, Appraisal);
                }
                break;

        }
        hideFragments(fragmentTransaction);
        fragmentTransaction.commitAllowingStateLoss();

    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (currentIndex != 2 && Appraisal != null) {
            fragmentTransaction.remove(Appraisal);
            Appraisal = null;
        }
        if (currentIndex != 0 && Quest != null) {
            fragmentTransaction.remove(Quest);
            Quest = null;
        }

        if (currentIndex != 1 && Setting != null) {
            fragmentTransaction.remove(Setting);
            Setting = null;
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (Appraisal == null && fragment instanceof AppraisalFragment) {
            Appraisal = (AppraisalFragment) fragment;
        }
        if (Quest == null && fragment instanceof QuestFragment) {
            Quest = (QuestFragment) fragment;
        }
        if (Setting == null && fragment instanceof SettingFragment) {
            Setting = (SettingFragment) fragment;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @OnClick({R.id.ig_project_list, R.id.tv_project_list, R.id.quests,
            R.id.ig_setting, R.id.tv_setting, R.id.setting,
            R.id.ig_appraisal_list, R.id.tv_appraisal_list, R.id.appraisal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_project_list:
            case R.id.tv_project_list:
            case R.id.quests:
                currentIndex = 0;
                setNav(currentIndex);
                updateView(currentIndex);
                break;
            case R.id.ig_setting:
            case R.id.tv_setting:
            case R.id.setting:
                currentIndex = 1;
                setNav(currentIndex);
                updateView(currentIndex);
                break;
            case R.id.ig_appraisal_list:
            case R.id.tv_appraisal_list:
            case R.id.appraisal:
                currentIndex = 2;
                setNav(currentIndex);
                updateView(currentIndex);
                break;
        }
    }

    private void updateView(int flag) {
        if (flag == 0) {
            Glide.with(QuestAndSetting.this).load(R.mipmap.project_list_selected).into(igProjectList);
            Glide.with(QuestAndSetting.this).load(R.mipmap.project_setting_unselected).into(igSetting);
            Glide.with(QuestAndSetting.this).load(R.mipmap.appraisal_unselected).into(igAppraisalList);
            tvProjectList.setTextColor(Color.parseColor("#74a9ed"));
            tvAppraisalList.setTextColor(Color.parseColor("#666666"));
            tvSetting.setTextColor(Color.parseColor("#666666"));
        } else if (flag == 1) {
            Glide.with(QuestAndSetting.this).load(R.mipmap.project_list_unselected).into(igProjectList);
            Glide.with(QuestAndSetting.this).load(R.mipmap.project_setting_selected).into(igSetting);
            Glide.with(QuestAndSetting.this).load(R.mipmap.appraisal_unselected).into(igAppraisalList);
            tvProjectList.setTextColor(Color.parseColor("#666666"));
            tvAppraisalList.setTextColor(Color.parseColor("#666666"));
            tvSetting.setTextColor(Color.parseColor("#74a9ed"));
        } else if (flag == 2) {
            Glide.with(QuestAndSetting.this).load(R.mipmap.project_list_unselected).into(igProjectList);
            Glide.with(QuestAndSetting.this).load(R.mipmap.project_setting_unselected).into(igSetting);
            Glide.with(QuestAndSetting.this).load(R.mipmap.appraisal_selected).into(igAppraisalList);
            tvAppraisalList.setTextColor(Color.parseColor("#74a9ed"));
            tvProjectList.setTextColor(Color.parseColor("#666666"));
            tvSetting.setTextColor(Color.parseColor("#666666"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        netConnected = NetworkUtils.isNetworkConnected(context);
        setNav(currentIndex);
    }
}
