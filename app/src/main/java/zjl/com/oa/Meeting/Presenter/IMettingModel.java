package zjl.com.oa.Meeting.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IMettingModel {
    void LookInterview(String token, String w_con_id,IMettingListener listener);
    void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,IMettingListener listener);
    void Interview(String token, int workflow_content_id, int wk_point_id,

                   List<LocalMedia> photos, String remark,
//                   List<LocalMedia> photos, String product_type_name, String remark,

                   String customer_name, String identity, String customer_phone,
                   String address, String bank_code, String bank_name,

                   String purpose,
//                   double loan_rate, String purpose, int loan_length, String return_amount_method,
                   String car_license, String car_registration, String car_engine_no, String car_vin,

                   IMettingListener listener);
    void InputInfo(String token, int w_con_id, int w_pot_id,

                   String customer_name, String identity, String customer_phone,
                   String address, String bank_code, String bank_name,

                   String purpose,
                   String car_license, String car_registration, String car_engine_no, String car_vin,

                   String remark, IMettingListener listener);





















}
