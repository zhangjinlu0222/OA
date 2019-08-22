package zjl.com.oa.Sign.Presenter;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IBeginSignPresenter{
    void getBeginSign(String token,String workflow_content_id);
    void postBeginSign(String token,String workflow_content_id,String wk_point_id);

    void onDestory();
}
