package zjl.com.oa2.FeedBack.Presenter;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IFeedBackModel {
    void LookFeedbackResult(String token, String w_con_id, IFeedBackListener listener);
    void FeedbackResult(String token, String w_con_id, String w_pot_id, String remark, IFeedBackListener listener);

    void LookFirstFeedbackResult(String token, String w_con_id, IFeedBackListener listener);
    void FirstFeedbackResult(String token, String w_con_id, String w_pot_id, String remark, IFeedBackListener listener);
}
