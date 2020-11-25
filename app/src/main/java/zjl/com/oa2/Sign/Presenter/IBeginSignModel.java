package zjl.com.oa2.Sign.Presenter;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IBeginSignModel {
    void getBeginSign(String token,String workflow_content_id,IBeginSignListener listener);

    void postBeginSign(String token,String workflow_content_id,String wk_point_id,IBeginSignListener listener);
}
