package zjl.com.oa.Base;

/**
 * Created by Administrator on 2018/4/9.
 */

public interface IBaseView {
    void relogin();
    void toMainActivity();
    void saveOperationState(String  wk_pot_id);
    void showFailureMsg(String msg);
    boolean isUploadTypeAdd();
}
