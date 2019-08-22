package zjl.com.oa.EvaluationQuota.Model;

import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaListener;
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaModel;
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaPresenter;
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaView;
import zjl.com.oa.Response.EvaluationQuotaResponse;

/**
 * Created by Administrator on 2018/3/13.
 */

public class EvaluationQuotaPresenterImpl implements IEvaluationQuotaPresenter,IEvaluationQuotaListener {
    private IEvaluationQuotaView evaluationQuotaView;
    private IEvaluationQuotaModel evaluationQuotaModel;

    public EvaluationQuotaPresenterImpl(IEvaluationQuotaView evaluationQuotaView) {
        this.evaluationQuotaView = evaluationQuotaView;
        this.evaluationQuotaModel = new EvaluationQuotaModelImpl();
    }

    @Override
    public void SureAmountReturn(String token, String w_con_id, String w_pot_id, String type_id) {
        if ("0".equals(type_id)){
            if (evaluationQuotaView != null){
                evaluationQuotaView.showFailureMsg("请选择需要上传的类型");
            }
            return;
        }
        if (evaluationQuotaModel != null){
            evaluationQuotaModel.SureAmountReturn(token,w_con_id, w_pot_id,  type_id,this);
        }
        if (evaluationQuotaView != null){
            evaluationQuotaView.showProgressBar();
        }
    }

    @Override
    public void LookFirstSureAmount(String token, String w_con_id) {

        if (evaluationQuotaModel != null){
            evaluationQuotaModel.LookFirstSureAmount(token,w_con_id,this);
        }

        if (evaluationQuotaView != null){
            evaluationQuotaView.showProgressBar();
        }
    }

    @Override
    public void FirstSureAmount(String token, String w_con_id, String w_pot_id, String amount, String remark) {
        if ("".equals(amount)){
            if (evaluationQuotaView != null){
                evaluationQuotaView.showFailureMsg("请输入评估范围");
            }
            return;
        }

        if (evaluationQuotaModel != null){
            evaluationQuotaModel.FirstSureAmount(token,w_con_id,w_pot_id,amount,remark,this);
        }

        if (evaluationQuotaView != null){
            evaluationQuotaView.showProgressBar();
        }
    }

    @Override
    public void SureAmount(String token, String w_con_id, String w_pot_id, Double amount, String remark) {
        if (amount <= 0){
            if (evaluationQuotaView != null){
                evaluationQuotaView.showFailureMsg("请输入评估定额");
            }
            return;
        }

        if (evaluationQuotaModel != null){
            evaluationQuotaModel.SureAmount(token,w_con_id,w_pot_id,amount,remark,this);
        }

        if (evaluationQuotaView != null){
            evaluationQuotaView.showProgressBar();
        }
    }

    @Override
    public void onDestoryView() {
        if (evaluationQuotaView != null){
            evaluationQuotaView = null;
        }
    }

    @Override
    public void onSucceed() {
        if (evaluationQuotaView != null){
            evaluationQuotaView.toMainActivity();
        }
        if (evaluationQuotaView != null){
            evaluationQuotaView.hideProgressBar();
        }
    }

    @Override
    public void onFail(String msg) {
        if (evaluationQuotaView != null){
            evaluationQuotaView.showFailureMsg(msg);
        }

        if (evaluationQuotaView != null){
            evaluationQuotaView.hideProgressBar();
        }
    }

    @Override
    public void relogin() {
        if (evaluationQuotaView != null){
            evaluationQuotaView.relogin();
        }
        if (evaluationQuotaView != null){
            evaluationQuotaView.hideProgressBar();
        }
    }

    @Override
    public void onSucceed(EvaluationQuotaResponse.Result result) {

        if (evaluationQuotaView != null){
            evaluationQuotaView.refreshData(result);
        }
        if (evaluationQuotaView != null){
            evaluationQuotaView.hideProgressBar();
        }
    }

    @Override
    public void onFail() {
        this.onFail("操作失败，请重新操作");
    }
}
