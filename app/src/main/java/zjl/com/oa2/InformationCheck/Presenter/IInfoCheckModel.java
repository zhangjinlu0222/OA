package zjl.com.oa2.InformationCheck.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;

import zjl.com.oa2.RenewLoan.Presenter.IRLListener;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInfoCheckModel {
    void endWorkFlow(String token, int workflow_content_id,int wk_point_id,String remark,IInfoCheckListener listener);
    void uploadMsg(String request_end_flag, String uploadType, HashMap<String ,Object> map, List<LocalMedia> files, IInfoCheckListener listener);
//    void uploadMsg(String request_end_flag,String uploadType,String token, int workflow_content_id,
//                   String persion_court, String credit,
//                   String car_break_rules, String insurance, String legal_person,
//                   int wk_point_id, List<LocalMedia> files,String remark, IInfoCheckListener listener);
    void Form(String token, int workflow_content_id, int wk_point_id, IInfoCheckListener listener);
}
