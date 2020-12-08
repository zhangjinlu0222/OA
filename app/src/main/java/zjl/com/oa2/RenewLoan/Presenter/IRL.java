package zjl.com.oa2.RenewLoan.Presenter;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjl.com.oa2.ApplicationConfig.Constant;
import zjl.com.oa2.Base.ResponseWithNoData;
import zjl.com.oa2.InformationCheck.Presenter.IInfoCheck;
import zjl.com.oa2.Response.FormResponse;
import zjl.com.oa2.Response.LoanDetailResponse;
import zjl.com.oa2.Response.ManagersResponse;
import zjl.com.oa2.Response.SearchResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IRL {


    @POST(Constant.ManagerList)
    Call<ManagersResponse> ManagerList(@Body RequestBody body);

    @POST(Constant.AdvanceSecInfo)
    Call<SearchResponse> AdvanceSecInfo();

    @POST(Constant.Sign)
    @Multipart
    Call<ResponseWithNoData> Sign(
            @Header("request_end_flag") String request_end_flag ,
            @Part("token") RequestBody token,
            @Part("workflow_content_id") RequestBody w_con_id,
            @Part("wk_point_id") RequestBody w_pot_id,
            @Part("customer_name") RequestBody customer_name,
            @Part("identity") RequestBody identity,
            @Part("customer_phone") RequestBody customer_phone,
            @Part("address") RequestBody address,
            @Part("bank_code") RequestBody bank_code,
            @Part("bank_name") RequestBody bank_name,
            @Part("purpose") RequestBody purpose,
            @Part("car_license") RequestBody car_license,
            @Part("car_registration") RequestBody car_registration,
            @Part("car_engine_no") RequestBody car_engine_no,
            @Part("car_vin") RequestBody car_vin,
            @Part("remark") RequestBody remark,
            @Part("type") RequestBody type,
            @Part("loan_length") RequestBody loan_length,
            @Part("contract_date") RequestBody contract_date,
            @Part List<MultipartBody.Part> filesBody);

    @POST(Constant.LookLoanDetail)
    Call<LoanDetailResponse> LookLoanDetail(@Body RequestBody body);

    @POST(Constant.TransferOwnership)
    @Multipart
    Call<ResponseWithNoData> TransferOwnership(
    @Header("request_end_flag") String request_end_flag ,
    @Part("token") RequestBody token,
    @Part("workflow_content_id") RequestBody w_con_id,
    @Part("wk_point_id") RequestBody w_pot_id,
    @Part("new_car_license") RequestBody license,
    @Part("insurance_enddate") RequestBody date,
    @Part("car_check_enddate") RequestBody car_check_enddate,
    @Part("remark") RequestBody phone,
    @Part("type") RequestBody purpose,
    @Part List<MultipartBody.Part> filesBody);

    @POST(Constant.Interview)
    @Multipart
    Call<ResponseWithNoData> HRInterview(
    @Header("request_end_flag") String request_end_flag ,
    @Part("token") RequestBody token,
    @Part("w_con_id") RequestBody w_con_id,
    @Part("w_pot_id") RequestBody w_pot_id,
    @Part("length") RequestBody length,
    @Part("identity_id") RequestBody identity_id,
    @Part("phone") RequestBody phone,
    @Part("purpose") RequestBody purpose,
    @Part("amount") RequestBody amount,
    @Part("a_name") RequestBody a_name,
    @Part("a_desc") RequestBody a_desc,
    @Part("a_phone") RequestBody a_phone,
    @Part("b_name") RequestBody b_name,
    @Part("b_desc") RequestBody b_desc,
    @Part("b_phone") RequestBody b_phone,
    @Part("c_name") RequestBody c_name,
    @Part("c_desc") RequestBody c_desc,
    @Part("c_phone") RequestBody c_phone,
    @Part("diya_desc") RequestBody diya_desc,
    @Part("zhiya_desc") RequestBody zhiya_desc,
    @Part("situation") RequestBody situation,
    @Part("remark") RequestBody remark,
    @Part("type") RequestBody typebody,
    @Part List<MultipartBody.Part> filesBody);

    @POST(Constant.FinishFlow)
    Call<ResponseWithNoData> FinishFlow(@Body RequestBody body);

    @POST(Constant.LoanApplication)
    Call<ResponseWithNoData> loanApplication(@Body RequestBody body);

    @POST(Constant.InformSigned)
    Call<ResponseWithNoData> InformSigned(@Body RequestBody body);

    @POST(Constant.BusFeedback)
    Call<ResponseWithNoData> BusFeedback(@Body RequestBody body);

    @POST(Constant.BusFirstFeedback)
    Call<ResponseWithNoData> FirstFeedback(@Body RequestBody body);
    @POST(Constant.PleDgeAssessTwo)
    @Multipart
    Call<ResponseWithNoData> PleDgeAssess(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("car_year") RequestBody car_year,
            @Part("car_type") RequestBody car_type,
            @Part("car_style") RequestBody car_style,
            @Part("milage") RequestBody milage,
            @Part("remark") RequestBody remark,
            @Part("market_amount") RequestBody market_amount,
            @Part("discount") RequestBody discount,
            @Part("discount_reason") RequestBody discount_reason,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part List<MultipartBody.Part> files);
    @POST(Constant.Coming)
    Call<ResponseWithNoData> Coming(@Body RequestBody body);
    @POST(Constant.ApplyforRefinance)
    Call<ResponseWithNoData> ApplyforRefinance(@Body RequestBody body);
    @POST(Constant.CarPhoto)
    @Multipart
    Call<ResponseWithNoData> post(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("remark") RequestBody remark,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part("type_id") RequestBody type_id,
            @Part("loan_length") RequestBody loan_length,
            @Part List<MultipartBody.Part> filesBody);
    @POST(Constant.FirstSureAmount)
    Call<ResponseWithNoData> FirstSureAmount(@Body RequestBody body);

    @POST(Constant.HRSureAmount)
    Call<ResponseWithNoData> HRSureAmount(@Body RequestBody body);
    @POST(Constant.SureAmount)
    Call<ResponseWithNoData> SureAmount(@Body RequestBody body);

    @POST(Constant.SureAmountReturn)
    Call<ResponseWithNoData> SureAmountReturn(@Body RequestBody body);
    @POST(Constant.InputInfo)
    Call<ResponseWithNoData> InputInfo(@Body RequestBody body);

    @POST(Constant.UploadPhoto)
    @Multipart
    Call<ResponseWithNoData> HRUploadData(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("remark") RequestBody remark,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part List<MultipartBody.Part> filesBody);

    @POST(Constant.UploadCarPhoto)
    @Multipart
    Call<ResponseWithNoData> UploadCarPhoto(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("remark") RequestBody remark,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part("type_id") RequestBody type_id,
            @Part List<MultipartBody.Part> filesBody);

    @POST(Constant.ContractDetailTwo)
    Call<ResponseWithNoData> ContractDetail(@Body RequestBody body);
    @POST(Constant.AuditRefinance)
    Call<ResponseWithNoData> AuditRefinance(@Body RequestBody body);
    @POST(Constant.AssessRefinance)
    Call<ResponseWithNoData> AssessRefinance(@Body RequestBody body);
    @POST(Constant.InfoCheckRefinanceTwo)
    @Multipart
    Call<ResponseWithNoData> InfoCheckRefinance(
            @Header("request_end_flag") String request_end_flag ,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("persion_court") RequestBody persion_court,
            @Part("car_break_rules") RequestBody car_break_rules,
            @Part("car_break_rules_amount") RequestBody car_break_rules_amount,
            @Part("car_check_amount") RequestBody car_check_amount,
            @Part("car_check_enddate") RequestBody car_check_enddate,
            @Part("insurance") RequestBody insurance,
            @Part("insurance_amount") RequestBody insurance_amount,
            @Part("insurance_enddate") RequestBody insurance_enddate,
            @Part("overtime_fee") RequestBody overtime_fee,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part List<MultipartBody.Part> files,
            @Part("remark") RequestBody remark);
    @POST(Constant.InfoCheck)
    @Multipart
    Call<ResponseWithNoData> InfoCheck(
            @Header("request_end_flag") String request_end_flag ,
            @Part("identity") RequestBody identity,
            @Part("customer_name") RequestBody customer_name,
            @Part("customer_phone") RequestBody customer_phone,
            @Part("type") RequestBody typebody,
            @Part("token") RequestBody token,
            @Part("workflow_content_id") RequestBody workflow_content_id,
            @Part("persion_court") RequestBody persion_court,
            @Part("car_break_rules") RequestBody car_break_rules,
            @Part("car_break_rules_amount") RequestBody car_break_rules_amount,
            @Part("car_check_amount") RequestBody car_check_amount,
            @Part("car_check_enddate") RequestBody car_check_enddate,
            @Part("insurance") RequestBody insurance,
            @Part("insurance_amount") RequestBody insurance_amount,
            @Part("insurance_enddate") RequestBody insurance_enddate,
            @Part("legal_person") RequestBody legal_person,
            @Part("overtime_fee") RequestBody overtime_fee,
            @Part("wk_point_id") RequestBody wk_point_id,
            @Part("remark") RequestBody remark,
            @Part("is_litigation") RequestBody is_litigation,
            @Part List<MultipartBody.Part> files);
    @POST(Constant.HRRefuseProject)
    Call<ResponseWithNoData> endWorkFlow(@Body RequestBody body);
    @POST(Constant.Form)
    Call<FormResponse> Form(@Body RequestBody body);

}
