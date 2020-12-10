package zjl.com.oa.Setting.PayBack.Presenter;

import zjl.com.oa.Setting.Presenter.ISettingListener;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface IPayBackModel {
    void SearchName(String token,String name,IPayBackListener listener);
    void SearchCarType(String token,String w_con_id,IPayBackListener listener);
    void UpdateReturnSchedule(String token,String amount,String date,String schedule_id,IPayBackListener listener);
}
