package zjl.com.oa.Setting.Presenter;

import zjl.com.oa.Base.IBaseView;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ISettingView extends IBaseView{
    void showProgressBar();
    void hideProgressBar();
    void onDestory();
    void refreshToken(String arg);
}
