package zjl.com.oa2.RiskSearch.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.TitleBarUtil;

public class RiskSearch extends BaseActivity{
    private ListView risksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_search);

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        initView();
    }


    private void initView(){
        risksView = findViewById(R.id.risksView);
        findViewById(R.id.ig_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
