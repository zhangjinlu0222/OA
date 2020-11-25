package zjl.com.oa2.Login.Presenter;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ILoginView {
    String getUsername();
    String getUserPwd();

    void showProgressBar();
    void hideProgressBar();

    void toMainActivity();
    void showFailureMsg(String msg);
    void onDestoryView();
    void clearData();
}
