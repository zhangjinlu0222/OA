package zjl.com.oa2.Login.Presenter;

/**
 * Created by Administrator on 2018/2/11.
 */

public interface ILoginModel {
    public void login(String account, String pwd, ILoginListener listener);
}
