package zjl.com.oa.Setting.PayBack.Presenter;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface IPayBackPresenter {
    void onDestory();
    void SearchName(String token,String name);
    void SearchCarType(String token,String w_con_id);
    void UpdateReturnSchedule(String token,String amount,String date,String schedule_id);
}
