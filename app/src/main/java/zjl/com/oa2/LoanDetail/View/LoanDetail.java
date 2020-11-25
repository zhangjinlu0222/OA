package zjl.com.oa2.LoanDetail.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zjl.com.oa2.Adapter.LoanInfoAdapter;
import zjl.com.oa2.Base.BaseActivity;
import zjl.com.oa2.Bean.LoanTop;
import zjl.com.oa2.R;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Utils.BitmapUtils;
import zjl.com.oa2.Utils.PermissionUtils;
import zjl.com.oa2.Utils.TitleBarUtil;

import static zjl.com.oa2.Utils.ButtonUtils.isFastDoubleClick;

public class LoanDetail extends BaseActivity{
    private ListView listView;

    private LoanInfoAdapter adapter;

    private Button btnSaveLoanInfo;

    private LoanDetailResponse.Result loanDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_info);

        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));
        initView();

        loanDetail = (LoanDetailResponse.Result) getIntent().getSerializableExtra("loanDetail");
        initData(loanDetail);
        PermissionUtils.verifyStoragePermissions(this);
    }


    private void initView(){
        listView = findViewById(R.id.lv_loaninfo);
        View footer = LayoutInflater.from(context).inflate(R.layout.item_loaninfo_footer,listView,false );
        findViewById(R.id.ig_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSaveLoanInfo = footer.findViewById(R.id.btn_save_loan_info);
        btnSaveLoanInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFastDoubleClick(R.id.btn_save_loan_info)){
                    return;
                }

                /* 回滚到顶部，防止view错乱*/
                listView.setSelection(0);

                Bitmap bitmap = BitmapUtils.shotListView(listView);

                /* 保存图片到Camera路径下，名称是loaninfo*/
                BitmapUtils.saveBmp2Gallery(context,bitmap,"loandetail");

                /*调用系统分享*/
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(Intent.createChooser(intent,"分享给好友"));
            }
        });
        listView.addFooterView(footer);
        adapter = new LoanInfoAdapter(context,listView);
        listView.setAdapter(adapter);

    }
    private void initData(LoanDetailResponse.Result result){
        if (result == null){
            return;
        }
        LoanTop loanTop = new LoanTop();
        loanTop.setData(null);
        loanTop.setData(result.getCal_list());


        List data = new ArrayList<>();
        data.add(loanTop);
        data.addAll(result.getList());
        adapter.UpdateDataSource(data);
    }
}
