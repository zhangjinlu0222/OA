package zjl.com.oa2.TransferVoucher.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseListener;
import zjl.com.oa2.Response.GetTransferVoucherResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface ITransferVoucherListener extends IBaseListener{
    void onSucceed(List<GetTransferVoucherResponse.Result.Section> data);
}
