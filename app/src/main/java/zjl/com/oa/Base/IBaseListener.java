package zjl.com.oa.Base;

/**
 * Created by Administrator on 2018/4/9.
 */

public interface IBaseListener {
    void onFail();
    void onSucceed();
    void onFail(String msg);
    void relogin();
}
