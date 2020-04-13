package zjl.com.oa.RenewLoan.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.Part;
import zjl.com.oa.BusinessFeedBack.Presenter.IBusFeedBackListener;
import zjl.com.oa.Evaluation.Presenter.IEvaluationListener;
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckModel;
import zjl.com.oa.LoanRequest.Presenter.ILoanRequestListener;
import zjl.com.oa.Meeting.Presenter.IMettingListener;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUploadListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLModel{

    void HRInterview(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                   List<LocalMedia> files,IRLListener listener);

    void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,String proc_type_id,IRLListener listener);
    void InfoCheck(String request_end_flag, String uploadType, HashMap<String ,Object> map, List<LocalMedia> files, IRLListener listener);

    void FinishFlow(HashMap<String ,Object> map,IRLListener listener);

    void loanApplication(HashMap<String ,Object> map,IRLListener listener);

//    void InformSigned(String token,String workflow_content_id,String wk_point_id,
//                      String service_fee,String pontage,
//                      String contract_date,String remark, IRLListener listener);

    void InformSigned(HashMap<String ,Object> map, IRLListener listener);

    void BusFeedback(HashMap<String ,Object> map, IRLListener listener);
    //业务反馈 下一步 不传remark，提交反馈信息的时候传remark

//    void FirstFeedback(String token, String w_con_id, String w_pot_id, String remark, IRLListener listener);
    void FirstFeedback(HashMap<String ,Object> map, IRLListener listener);
    //初步反馈 下一步 不传remark，提交反馈信息的时候传remark

//    void PleDgeAssess(String request_end_flag,String uploadType,String token,
//                   int car_year, String car_type,String car_style,String milage,
//                   String remark, String market_amount, String take_amount,
//                   String  workflow_content_id,String wk_point_id,List<LocalMedia> files,
//                         IRLListener listener);

    void PleDgeAssess(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                      List<LocalMedia> files,IRLListener listener);
    void Coming(HashMap<String ,Object> map, IRLListener listener);

    void ApplyforRefinance(HashMap<String ,Object> map, IRLListener listener);
//    void ApplyforRefinance(String token, String w_con_id, String w_pot_id,
//                         String loan_length, String remark,IRLListener listener);

    void HRUploadData(String request_end_flag,String uploadType,HashMap<String ,Object > map,
                      List<LocalMedia> files, IRLListener listener);

    void CarPhoto(String request_end_flag,String uploadType,HashMap<String ,Object > map,String type_id,
                  List<LocalMedia> files, IRLListener listener);

    void FirstSureAmount(HashMap<String ,Object> map,IRLListener listener);

//    void SureAmount(String token,String w_con_id,String w_pot_id,
//                    String amount,String assure_amount,String derating_opinion,String remark,IRLListener listener);
    void SureAmount(HashMap<String ,Object>map,IRLListener listener);
    void HRSureAmount(HashMap<String ,Object>map,IRLListener listener);

    void SureAmountReturn(String token,String w_con_id,String w_pot_id,
                          String type_id,IRLListener listener);
    void InputInfo(HashMap<String ,Object> map, IRLListener listener);

    void UploadCarPhoto(String request_end_flag,String uploadType,
                        HashMap<String,Object> map,
                        String type_id, List<LocalMedia> files, IRLListener listener);

//    void ContractDetail(String token, int workflow_content_id, int wk_point_id, String remark,
//                        String amount,String loan_rate,String loan_length,String pontage,
//                        String service_fee,String insurance,String car_break_rules,String contract_date,
//                        IRLListener listener);
    void ContractDetail(HashMap<String ,Object> map, IRLListener listener);

//    void AuditRefinance(String token, int workflow_content_id, int wk_point_id, String remark,
//                        IRLListener listener);
    void AuditRefinance(HashMap<String ,Object> map, IRLListener listener);

//    void AssessRefinance(String token, int workflow_content_id, int wk_point_id, String file_info,
//                         String derate_info, String remark, IRLListener listener);
    void AssessRefinance(HashMap<String ,Object> map, IRLListener listener);

//    void InfoCheckRefinance(String request_end_flag ,String uploadType,String token, String workflow_content_id,
//                            String wk_point_id,String persion_court, String car_break_rules,
//                            String insurance,String remark,List<LocalMedia> files, IRLListener listener);

    void InfoCheckRefinance(String request_end_flag ,String uploadType,HashMap<String ,Object> map,
                            List<LocalMedia> files, IRLListener listener);
    void Form(String token, int workflow_content_id, int wk_point_id,String proc_type_id, IRLListener listener);

}
