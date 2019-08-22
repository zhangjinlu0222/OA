package zjl.com.oa.RenewLoan.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.Part;
import zjl.com.oa.EvaluationQuota.Presenter.IEvaluationQuotaListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckListener;
import zjl.com.oa.InformationCheck.Presenter.IInfoCheckModel;
import zjl.com.oa.Meeting.Presenter.IMettingListener;
import zjl.com.oa.UploadPhotos.Presenter.IPhotoUploadListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRLModel{
    void ApplyforRefinance(String token, String w_con_id, String w_pot_id,
                         String loan_length, String remark,IRLListener listener);
    void CarPhoto(String request_end_flag,String uploadType,
                  String token, String remark,
                  int workflow_content_id, int wk_point_id,
                  List<LocalMedia> files,String type_id,
                  String loan_length, IRLListener listener);
    void FirstSureAmount(String token, String w_con_id, String w_pot_id,
                         String amount, String remark,IRLListener listener);
    void SureAmount(String token,String w_con_id,String w_pot_id,
                    String amount,String assure_amount,String derating_opinion,String remark,IRLListener listener);
    void SureAmountReturn(String token,String w_con_id,String w_pot_id,
                          String type_id,IRLListener listener);
    void InputInfo(String token, int w_con_id, int w_pot_id,

                   String customer_name, String identity, String customer_phone,
                   String address, String bank_name, String bank_code,

                   String purpose,
                   String car_license, String car_registration, String car_engine_no, String car_vin,

                   String remark, IRLListener listener);
    void UploadCarPhoto(String request_end_flag,String uploadType,
                        String token,  int workflow_content_id, String remark,int wk_point_id,
                        String type_id, List<LocalMedia> files, IRLListener listener);
    void ContractDetail(String token, int workflow_content_id, int wk_point_id, String remark,
                        String amount,String loan_rate,String loan_length,String pontage,
                        String service_fee,String insurance,String car_break_rules,String contract_date,
                        IRLListener listener);
    void AuditRefinance(String token, int workflow_content_id, int wk_point_id, String remark,
                        IRLListener listener);
    void AssessRefinance(String token, int workflow_content_id, int wk_point_id, String file_info,
                         String derate_info, String remark, IRLListener listener);
    void InfoCheckRefinance(String request_end_flag ,String uploadType,String token, int workflow_content_id,
                            int wk_point_id,String persion_court, String car_break_rules,
                            String insurance,String remark,List<LocalMedia> files, IRLListener listener);
    void Form(String token, int workflow_content_id, int wk_point_id, IRLListener listener);

}
