package zjl.com.oa2.EvaluationQuota.Presenter;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IEvaluationQuotaModel {
    void SureAmountReturn(String token,String w_con_id,String w_pot_id,String type_id,IEvaluationQuotaListener listener);
    void LookFirstSureAmount(String token,String w_con_id,IEvaluationQuotaListener listener);
    void FirstSureAmount(String token,String w_con_id,String w_pot_id,String amount,String remark,IEvaluationQuotaListener listener);
    void SureAmount(String token,String w_con_id,String w_pot_id,Double amount,String remark,IEvaluationQuotaListener listener);


}
