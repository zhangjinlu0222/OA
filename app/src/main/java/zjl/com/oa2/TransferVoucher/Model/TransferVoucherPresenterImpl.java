package zjl.com.oa2.TransferVoucher.Model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import zjl.com.oa2.Bean.UserInfo;
import zjl.com.oa2.Response.GetTransferVoucherResponse;
import zjl.com.oa2.TransferVoucher.Presenter.ITransferVoucherListener;
import zjl.com.oa2.TransferVoucher.Presenter.ITransferVoucherModel;
import zjl.com.oa2.TransferVoucher.Presenter.ITransferVoucherPresenter;
import zjl.com.oa2.TransferVoucher.Presenter.ITransferVoucherView;

import static zjl.com.oa2.Base.BaseActivity.UPLOAD_TYPE_ADD;
import static zjl.com.oa2.Base.BaseActivity.request_end_flag;

/**
 * Created by Administrator on 2018/3/5.
 */

public class TransferVoucherPresenterImpl implements ITransferVoucherListener,ITransferVoucherPresenter{
    private ITransferVoucherView TransferVoucherView;
    private ITransferVoucherModel TransferVoucherModel;
    private String token, remark,workflow_content_id, wk_point_id,type_id;
    private List<LocalMedia> files = new ArrayList<>();
    private boolean isUploading = false;

    public TransferVoucherPresenterImpl(ITransferVoucherView TransferVoucherView) {
        this.TransferVoucherView = TransferVoucherView;
        this.TransferVoucherModel = new TransferVoucherModelImpl();
    }

    @Override
    public void getTransferVoucher(String token, String w_con_id) {
        if (TransferVoucherModel != null){
            TransferVoucherModel.getTransferVoucher(token,w_con_id,this);
        }

        if (TransferVoucherView != null){
            TransferVoucherView.showProgress();
        }
    }

    @Override
    public void uploadPhotos(String request_end_flag,String uploadType,String token, String remark,
                             String workflow_content_id, String wk_point_id, List<LocalMedia> files,
                             String type_id) {
        //如果不是放款结束，则必须选择上传文件
        if (!"6".equals(type_id) && files.size() <= 0
                && !TransferVoucherView.isUploadTypeAdd()){
            if (TransferVoucherView != null){
                TransferVoucherView.showFailureMsg("请选择上传文件");
            }
            return;
        }

        if (TransferVoucherView != null){
            TransferVoucherView.showProgress();
        }

        if (TransferVoucherModel != null){
            if (files.size() <= 1){
                TransferVoucherModel.uploadPhotos( request_end_flag, uploadType,token,remark,
                        workflow_content_id,wk_point_id,files,type_id,this);
                this.isUploading = false;
            }else{
                TransferVoucherModel.uploadPhotos( request_end_flag, uploadType,token,remark,
                        workflow_content_id,wk_point_id,files.subList(0,files.size() / 2),type_id,this);
                this.isUploading = true;
            }
        }
        if (TransferVoucherView != null){
            TransferVoucherView.showProgress();
        }

        this.token = token;
        this.remark = remark;
        this.workflow_content_id = workflow_content_id;
        this.wk_point_id = wk_point_id;
        this.files.addAll(files);
        this.type_id = type_id;
    }

    @Override
    public void onSucceed() {
        if (TransferVoucherView != null && !isUploading){
            TransferVoucherView.toMainActivity();
            TransferVoucherView.hideProgress();
            if (wk_point_id.equals("22")){
                TransferVoucherView.saveOperationState("1");
            }
        }else{
            TransferVoucherModel.uploadPhotos( request_end_flag, UPLOAD_TYPE_ADD,token,remark,
                    workflow_content_id,wk_point_id,files.subList(files.size() / 2,files.size()),type_id,this);
        }
        this.isUploading = false;
    }

    @Override
    public void onFail() {
        this.onFail("网络异常");
    }

    @Override
    public void onSucceed(List<GetTransferVoucherResponse.Result.Section> data) {
        if (TransferVoucherView != null){
            TransferVoucherView.refreshData(data);
        }
        if (TransferVoucherView != null){
            TransferVoucherView.hideProgress();
        }
    }

    @Override
    public void onFail(String msg) {
        if (TransferVoucherView != null){
            TransferVoucherView.showFailureMsg(msg);
            TransferVoucherView.hideProgress();
        }
        this.isUploading = false;
    }

    @Override
    public void relogin() {
        if (TransferVoucherView != null){
            TransferVoucherView.hideProgress();
            TransferVoucherView.relogin();
        }
        this.isUploading = false;
    }
}
