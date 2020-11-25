package zjl.com.oa2.Setting.View;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.R;
import zjl.com.oa2.Utils.QRCodeUtil;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class BangkeActivity extends BaseActivity {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.ig_qrcode)
    ImageView igQrcode;
    @Bind(R.id.btn_share)
    Button btnShare;

    public Bitmap qrCode = null;

    public String phone;

    public String bangkeUrl = "https://www.clxchina.com/wxxcx/oathird/a?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangke);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        Intent intent = getIntent();
        String arg = intent.getStringExtra("phone");
        if(arg != null && arg.length() >0){
            phone = arg;
            createQRCode(phone);
        }else{
            this.showFailureMsg("手机号异常，请重试");
        }
    }

    @OnClick({R.id.ig_back, R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.btn_share:
                if (isFastDoubleClick(R.id.btn_share)){
                    return;
                }
                WXShare(qrCode);
                break;
        }
    }

    public void createQRCode(String phone){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_bangke);
        qrCode = QRCodeUtil.createQRCodeBitmapWithLogo(bangkeUrl+"phone="+phone,400, 400,bitmap);

        if (qrCode != null){
            igQrcode.setImageBitmap(qrCode);
        }
    }

    private void WXShare(Bitmap bitmap) {
        OnekeyShare oks = new OnekeyShare();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("车励享OA系统");
        // text是分享文本，所有平台都需要这个字段
        oks.setImageData(bitmap);

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Toast.makeText(context, "分享完成", Toast.LENGTH_SHORT).show();
//                Log.e("TAG", "OnekeyShare onComplete");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "OnekeyShare onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "OnekeyShareonCancel");
            }
        });

// 启动分享GUI
        oks.show(this);
    }

}
