package zjl.com.oa.Sign.Presenter;

import java.util.List;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.GetSigningResponse;

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
