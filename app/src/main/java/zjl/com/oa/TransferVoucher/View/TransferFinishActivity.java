package zjl.com.oa.TransferVoucher.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dou361.dialogui.DialogUIUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjl.com.oa.Base.BaseActivity;
import zjl.com.oa.Bean.UploadCarPhotosType;
import zjl.com.oa.Bean.UserInfo;
import zjl.com.oa.R;
import zjl.com.oa.Response.GetTransferVoucherResponse;
import zjl.com.oa.TransferVoucher.Model.TransferVoucherPresenterImpl;
import zjl.com.oa.TransferVoucher.Presenter.ITransferVoucherPresenter;
import zjl.com.oa.TransferVoucher.Presenter.ITransferVoucherView;
import zjl.com.oa.Utils.TitleBarUtil;

import static zjl.com.oa.Utils.ButtonUtils.isFastDoubleClick;

public class TransferFinishActivity extends BaseActivity implements ITransferVoucherView {

    @Bind(R.id.ig_back)
    ImageView igBack;
    @Bind(R.id.next)
    Button next;
    private ITransferVoucherPresenter transferVoucherPresenter;

    private String token, workflow_content_id, wk_point_id;
    private int type_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_finish);
        ButterKnife.bind(this);
        TitleBarUtil.setTitleBarColor(this, Color.rgb(116, 169, 237));

        workflow_content_id = getIntent().getStringExtra("workflow_content_id");
        wk_point_id = getIntent().getStringExtra("wk_point_id");
        type_id = new UploadCarPhotosType().getType_id(Integer.parseInt(wk_point_id));
        token = UserInfo.getInstance(context).getUserInfo(UserInfo.TOKEN);

        if (wk_point_id != null && wk_point_id.equals("22")){
            String autoUpdate = UserInfo.getInstance(context).getUserInfo(UserInfo.AUTOUPDATE);
            if (autoUpdate != null && autoUpdate.equals("1")){
                saveOperation(wk_point_id);
            }
        }
        transferVoucherPresenter = new TransferVoucherPresenterImpl(this);
    }

    @Override
    public void refreshData(List<GetTransferVoucherResponse.Result.Section> data) {

    }

    @Override
    public String getBakInfo() {
        return null;
    }

    @Override
    public void showProgress() {
        if (submitDialog != null){
            submitDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (submitDialog != null){
            DialogUIUtils.dismiss(submitDialog);
        }
    }

    @OnClick({R.id.ig_back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ig_back:
                this.toMainActivity();
                break;
            case R.id.next:
                if (!netConnected){
                    showNetError();
                }else if (isFastDoubleClick(R.id.next)){
                    return;
                }else{
                    transferVoucherPresenter.uploadPhotos(request_start_flag,UPLOAD_TYPE_NORMAL,
                            token,"",workflow_content_id,wk_point_id,
                            new ArrayList<LocalMedia>(),Integer.toString(type_id));
                }
                break;
        }
    }
}
