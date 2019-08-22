package zjl.com.oa.Meeting.Presenter;

import zjl.com.oa.Base.IBaseView;

/**
 * Created by Administrator on 2018/3/22.
 */

public interface IEnterOrderView extends IBaseView{

    String getCustomerName();
    String getID();
    String getPhoneNumber();
    String getAddress();
    String getBankCode();
    String getBankCard();

    double getLoanRate();
    String getLoanDest();
    int getLoanDuration();
    String getPayBackWay();

    String getCarNumber();
    String getCarRegNumber();
    String getCarEngineNumber();
    String getCarFrameNumber();
    void showProgressBar();
    void hideProgressBar();
}
