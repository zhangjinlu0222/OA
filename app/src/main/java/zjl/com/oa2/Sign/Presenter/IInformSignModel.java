package zjl.com.oa2.Sign.Presenter;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInformSignModel {
    void getInformSign(String token,String workflow_content_id,IInformSignListener listener);

    void postInformSign(String token,String workflow_content_id,String wk_point_id,
                        String service_fee,String pontage,
                        String contract_date,String remark,IInformSignListener listener);
}
