package zjl.com.oa.InformationCheck.Presenter;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import zjl.com.oa.Base.IBaseView;
import zjl.com.oa.Response.FormResponse;

/**
 * Created by Administrator on 2018/3/5.
 */

public interface IInfoCheckView extends IBaseView{
    void showProgress();
    void hideProgress();
    void loadForms(FormResponse.Result result);
    String getData_Con(String arg);//获取参数对应的控件值
}
