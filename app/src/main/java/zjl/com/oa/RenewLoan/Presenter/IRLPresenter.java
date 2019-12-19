package zjl.com.oa.RenewLoan.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import zjl.com.oa.BusinessFeedBack.Presenter.IBusFeedBackListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.Meeting.Presenter.IMettingListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLPresenter{

    void FinishFlow(HashMap<String ,Object> map);
//    void loanApplication(String token,int w_con_id,int w_pot_id,String remark);
    void loanApplication(HashMap<String ,Object> map);

//    void InformSigned(String token,String workflow_content_id,String wk_point_id,
//                        String service_fee,String pontage,
//                        String contract_date,String remark);
    void InformSigned(HashMap<String ,Object> map);

    void BusFeedback(HashMap<String ,Object> map);
    //业务反馈 下一步 不传remark，提交反馈信息的时候传remark

//    void FirstFeedback(String token, String w_con_id, String w_pot_id, String remark);
    void FirstFeedback(HashMap<String ,Object> map);
    //初步反馈 下一步 不传remark，提交反馈信息的时候传remark

//    void PleDgeAssess(String request_end_flag,String uploadType,String token,
//                      int  car_year, String car_type,String car_style,String milage,
//                      String remark, String market_amount, String take_amount,
//                      int workflow_content_id,int wk_point_id,List<LocalMedia> files);
    void PleDgeAssess(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                      List<LocalMedia> files);
    void Coming(HashMap<String ,Object > map);

    void ApplyforRefinance(HashMap<String ,Object > map);

//    void ApplyforRefinance(String token, String w_con_id, String w_pot_id,
//                           String loan_length, String remark);

//    void CarPhoto(String request_end_flag,String uploadType,
//                  String token, String remark,
//                  int workflow_content_id, int wk_point_id,
//                  List<LocalMedia> files,String type_id,
//                  String loan_length);
    void CarPhoto(String request_end_flag,String uploadType,HashMap<String ,Object > map,String type_id,
                  List<LocalMedia> files);
    void FirstSureAmount(HashMap<String ,Object> map);
//    void SureAmount(String token, String w_con_id, String w_pot_id,
//                    String  amount,String assure_amount, String derating_opinion,String remark);
    void SureAmount(HashMap<String ,Object> map);
    void SureAmountReturn(String token,String w_con_id,String w_pot_id,String type_id);
//    void InputInfo(String token, int w_con_id, int w_pot_id,
//
//                   String customer_name, String identity, String customer_phone,
//                   String address, String bank_code, String bank_name,
//
//                   String purpose,
//                   String car_license, String car_registration, String car_engine_no, String car_vin,
//
//                   String remark);
    void InputInfo(HashMap<String ,Object> map);

//    void UploadCarPhoto(String request_end_flag,String uploadType,
//                        String token, int workflow_content_id, String remark, int wk_point_id,
//                        String type_id, List<LocalMedia> files);
    void UploadCarPhoto(String request_end_flag,String uploadType,
                        HashMap<String,Object> map,
                        String type_id, List<LocalMedia> files);

//    void ContractDetail(String token, int workflow_content_id, int wk_point_id, String remark,
//                        String amount,String loan_rate,String loan_length,String pontage,
//                        String service_fee,String insurance,String car_break_rules,String contract_date);
    void ContractDetail(HashMap<String ,Object> map);

//    void AuditRefinance(String token, int workflow_content_id, int wk_point_id, String remark);
    void AuditRefinance(HashMap<String ,Object> map);

//    void AssessRefinance(String token, int workflow_content_id, int wk_point_id, String file_info,
//                         String derate_info, String remark);
    void AssessRefinance(HashMap<String ,Object> map);

//    void InfoCheckRefinance(String request_end_flag ,String uploadType,String token, int workflow_content_id,
//                            int wk_point_id,String persion_court, String car_break_rules,
//                            String insurance,String remark,List<LocalMedia> files);
    void InfoCheckRefinance(String request_end_flag ,String uploadType,HashMap<String ,Object> map,List<LocalMedia> files);

    void Form(String token, int workflow_content_id, int wk_point_id);
}
