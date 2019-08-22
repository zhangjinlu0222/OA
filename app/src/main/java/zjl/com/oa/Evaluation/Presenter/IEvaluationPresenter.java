package zjl.com.oa.Evaluation.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IEvaluationPresenter{
    void uploadMsg(String request_end_flag,String uploadType,String token,  int car_year,
                   String car_type,String car_style,String milage,
                   String remark, String market_amount, String take_amount,
                   int workflow_content_id,int wk_point_id,List<LocalMedia> files);

    void Form(String token, int workflow_content_id, int wk_point_id);
}
