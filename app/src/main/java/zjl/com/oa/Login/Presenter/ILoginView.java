package zjl.com.oa.Login.Presenter;

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
