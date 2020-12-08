package zjl.com.oa2.RiskSearch.Presenter;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface IRiskSearchPresenter {
    void GetBigDatas(String token, String w_con_id, String name, String phone, String identity_id);
    void LookBigDatas(String token, String w_con_id);
}
