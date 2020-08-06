package zjl.com.oa.WorkFlow.View;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.R;
import zjl.com.oa.Utils.TitleBarUtil;

public class Contract extends BaseActivity {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.webView)
    WebView webView;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        url = getIntent().getStringExtra("url");
        Log.e("TAG",url );

        webView.loadUrl(url);
    }

    @OnClick(R.id.ig_back)
    public void onViewClicked() {
        this.finish();
    }

}
