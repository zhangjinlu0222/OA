package zjl.com.oa.LoanRequest.Presenter;

/**
 * Created by Administrator on 2018/3/20.
 */

public interface ILoanRequestPresenter {

    void lookLoanApplication(String token,String workflow_content_id);
    void loanApplication(String token,String w_con_id,String w_pot_id,String remark);
}
