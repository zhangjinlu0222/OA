package zjl.com.oa.TransferVoucher.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface ITransferVoucherPresenter{
    void getTransferVoucher(String token,String w_con_id);

    void uploadPhotos(String request_end_flag,String uploadType,String token, String remark, String workflow_content_id, String wk_point_id, List<LocalMedia> files, String type_id);
}
