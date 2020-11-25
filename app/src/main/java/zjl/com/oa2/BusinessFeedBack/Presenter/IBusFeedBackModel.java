package zjl.com.oa2.BusinessFeedBack.Presenter;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IBusFeedBackModel {
//    void LookSureAmount(String token, String w_con_id, IBusFeedBackListener listener);//查看业务反馈信息
    void LookFirstSureAmount(String token, String w_con_id, IBusFeedBackListener listener);//查看初步业务反馈信息

    void BusFeedback(String token, String w_con_id, String w_pot_id, int loan_length, float loan_rate,
                     String return_amount_method,String remark, IBusFeedBackListener listener);//业务反馈 下一步 不传remark，提交反馈信息的时候传remark

    void BusFirstFeedback(String token, String w_con_id, String w_pot_id, String remark, IBusFeedBackListener listener);//初步业务反馈 下一步 不传remark，提交反馈信息的时候传remark
}
