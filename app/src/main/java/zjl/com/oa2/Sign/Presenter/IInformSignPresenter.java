package zjl.com.oa2.Sign.Presenter;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInformSignPresenter{
    void getInformSign(String token,String workflow_content_id);

    void postInformSign(String token,String workflow_content_id,String wk_point_id,
                        String service_fee,String pontage,
                        String contract_date,String remark);

    String getPontage();
    String getServiceFee();
    String getContractDate();

    void onDestory();
}
