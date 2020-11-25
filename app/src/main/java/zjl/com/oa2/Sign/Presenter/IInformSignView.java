package zjl.com.oa2.Sign.Presenter;

import java.util.List;

import zjl.com.oa2.Base.IBaseView;
import zjl.com.oa2.Response.GetSigningResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInformSignView extends IBaseView{
    String getBakInfo();
    String getServiceFee();
    String getPontage();
    String getContractDate();
    void refreshData(List<GetSigningResponse.Result.Data> data);

    void showProgressBar();
    void hideProgressBar();
}
