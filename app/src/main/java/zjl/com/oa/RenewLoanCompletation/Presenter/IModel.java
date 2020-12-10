package zjl.com.oa.RenewLoanCompletation.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.Part;
import zjl.com.oa.BusinessFeedBack.Presenter.IBusFeedBackListener;
import zjl.com.oa.Evaluation.Presenter.IEvaluationListener;
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckModel;
import zjl.com.oa.LoanRequest.Presenter.ILoanRequestListener;
import zjl.com.oa.Meeting.Presenter.IMettingListener;
import zjl.com.oa.RenewLoan.Presenter.IRLListener;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUploadListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IModel{
    void RefinanceFinishFlow(String token,String w_con_id,String w_pot_id,IListener listener);
}
