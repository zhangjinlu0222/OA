package zjl.com.oa.Login.Presenter;

/**
 * Created by Administrator on 2018/2/11.
 */

public interface ILoginModel {
    public void login(String account, String pwd, ILoginListener listener);
}
