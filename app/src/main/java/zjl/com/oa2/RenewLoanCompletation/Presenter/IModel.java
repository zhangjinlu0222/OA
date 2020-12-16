package zjl.com.oa2.RenewLoanCompletation.Presenter;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IModel{
    void RefinanceFinishFlow(String token,String w_con_id,String w_pot_id,IListener listener);
}
