package zjl.com.oa2.TransferVoucher.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.GetTransferVoucherResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface ITransferVoucherView extends IBaseView{
    void refreshData(List<GetTransferVoucherResponse.Result.Section> data);
    String getBakInfo();
    void showProgress();
    void hideProgress();
}
