package zjl.com.oa.RenewLoan.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.Meeting.Presenter.IMettingListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLPresenter{
    void ApplyforRefinance(String token, String w_con_id, String w_pot_id,
                           String loan_length, String remark);
    void CarPhoto(String request_end_flag,String uploadType,
                  String token, String remark,
                  int workflow_content_id, int wk_point_id,
                  List<LocalMedia> files,String type_id,
                  String loan_length);

    void FirstSureAmount(String token, String w_con_id, String w_pot_id,
                         String amount, String remark);
    void SureAmount(String token, String w_con_id, String w_pot_id,
                    String  amount,String assure_amount, String derating_opinion,String remark);
    void SureAmountReturn(String token,String w_con_id,String w_pot_id,String type_id);
    void InputInfo(String token, int w_con_id, int w_pot_id,

                   String customer_name, String identity, String customer_phone,
                   String address, String bank_code, String bank_name,

                   String purpose,
                   String car_license, String car_registration, String car_engine_no, String car_vin,

                   String remark);
    void UploadCarPhoto(String request_end_flag,String uploadType,
                        String token, int workflow_content_id, String remark, int wk_point_id,
                        String type_id, List<LocalMedia> files);
    void ContractDetail(String token, int workflow_content_id, int wk_point_id, String remark,
                        String amount,String loan_rate,String loan_length,String pontage,
                        String service_fee,String insurance,String car_break_rules,String contract_date);
    void AuditRefinance(String token, int workflow_content_id, int wk_point_id, String remark);
    void AssessRefinance(String token, int workflow_content_id, int wk_point_id, String file_info,
                         String derate_info, String remark);
    void InfoCheckRefinance(String request_end_flag ,String uploadType,String token, int workflow_content_id,
                            int wk_point_id,String persion_court, String car_break_rules,
                            String insurance,String remark,List<LocalMedia> files);
    void Form(String token, int workflow_content_id, int wk_point_id);
}
