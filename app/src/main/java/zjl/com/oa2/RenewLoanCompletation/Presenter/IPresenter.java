package zjl.com.oa2.RenewLoanCompletation.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import zjl.com.oa2.BusinessFeedBack.Presenter.IBusFeedBackListener;
import zjl.com.oa2.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa2.Meeting.Presenter.IMettingListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IPresenter{
    void RefinanceFinishFlow(String token,String w_con_id,String w_pot_id);
}
