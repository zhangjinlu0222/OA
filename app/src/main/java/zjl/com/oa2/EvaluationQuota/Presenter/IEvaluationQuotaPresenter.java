package zjl.com.oa2.EvaluationQuota.Presenter;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IEvaluationQuotaPresenter {
    void SureAmountReturn(String token,String w_con_id,String w_pot_id,String type_id);
    void LookFirstSureAmount(String token,String w_con_id);
    void FirstSureAmount(String token, String w_con_id, String w_pot_id, String amount, String remark);
    void SureAmount(String token, String w_con_id, String w_pot_id, Double amount, String remark);

    void onDestoryView();
}
