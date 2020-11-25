package zjl.com.oa2.Evaluation.Model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import zjl.com.oa2.Evaluation.Presenter.IEvaluationListener;
import zjl.com.oa2.Evaluation.Presenter.IEvaluationModel;
import zjl.com.oa2.Evaluation.Presenter.IEvaluationPresenter;
import zjl.com.oa2.Evaluation.Presenter.IEvaluationView;
import zjl.com.oa2.Response.FormResponse;

import static zjl.com.oa2.Base.BaseActivity.UPLOAD_TYPE_ADD;
import static zjl.com.oa2.Base.BaseActivity.request_end_flag;

/**
 * Created by Administrator on 2018/3/5.
 */

public class EvaluationPresenterImpl implements IEvaluationPresenter,IEvaluationListener {

    private IEvaluationView evaluationView;
    private IEvaluationModel evaluationModel;
    private String token, car_type,  car_style, milage, remark,  market_amount,take_amount;
    private int car_year,workflow_content_id,  wk_point_id;
    private List<LocalMedia> files = new ArrayList<>();
    private boolean isUploading = false;

    public EvaluationPresenterImpl(IEvaluationView evaluationView) {
        this.evaluationView = evaluationView;
        this.evaluationModel = new EvaluationModelImpl();
    }

    @Override
    public void onSucceed() {
        if (evaluationView != null && !isUploading){
            evaluationView.hideProgress();
            evaluationView.toMainActivity();
        }else{
            evaluationModel.uploadMsg( request_end_flag, UPLOAD_TYPE_ADD,
                    token,car_year,car_type,car_style,milage,
                    remark,market_amount,take_amount,workflow_content_id,wk_point_id,
                    files.subList(files.size() / 2,files.size()),this);
            this.isUploading = false;
        }
    }

    @Override
    public void onFail(String msg) {

        if (evaluationView != null){
            evaluationView.hideProgress();
            evaluationView.showFailureMsg(msg);
        }
        this.isUploading = false;
    }

    @Override
    public void relogin() {

        if (evaluationView != null){
            evaluationView.hideProgress();
            evaluationView.relogin();
        }
        this.isUploading = false;
    }

    @Override
    public void onFail() {
        this.onFail("操作失败，请重试");
    }


    @Override
    public void uploadMsg(String request_end_flag,String uploadType,String token, int car_year,
                          String car_type, String car_style,String milage,
                          String remark, String market_amount,
                          String take_amount, int workflow_content_id, int wk_point_id,
                          List<LocalMedia> files) {

            if (files.size() <=0 && !evaluationView.isUploadTypeAdd()){
                evaluationView.showFailureMsg("请选择上传文件");
                return;
            }

            if (car_year <= 0){
                evaluationView.showFailureMsg("请输入车辆年份");
                return;
            }

            if ("".equals(car_type)){
                evaluationView.showFailureMsg("请输入车辆品牌");
                return;
            }

            if ("".equals(car_style)){
                evaluationView.showFailureMsg("请输入车辆型号");
                return;
            }
            if ("".equals(milage)){
                evaluationView.showFailureMsg("请输入公里数");
                return;
            }

            if ("".equals(take_amount)){
                evaluationView.showFailureMsg("请输入收车价");
                return;
            }

            if ("".equals(market_amount)){
                evaluationView.showFailureMsg("请输入市场价");
                return;
            }

            if (evaluationModel != null){
                if (files.size() <= 1){
                    evaluationModel.uploadMsg( request_end_flag, uploadType,
                            token,car_year,car_type,car_style,milage,
                            remark,market_amount,take_amount,
                            workflow_content_id,wk_point_id,files,this);

                    this.isUploading = false;
                }else{
                    evaluationModel.uploadMsg( request_end_flag, uploadType,
                            token,car_year,car_type,car_style,milage,
                            remark,market_amount,take_amount,
                            workflow_content_id,wk_point_id,files.subList(0,files.size() / 2),this);

                    this.isUploading = true;

                }
            }

            if (evaluationView != null){
                evaluationView.showProgress();
            }

            this.token = token;
            this.car_year = car_year;
            this.car_type = car_type;
            this.car_style = car_style;
            this.milage = milage;
            this.remark = remark;
            this.market_amount = market_amount;
            this.take_amount = take_amount;
            this.workflow_content_id = workflow_content_id;
            this.wk_point_id = wk_point_id;
            this.files.addAll(files);
    }

    @Override
    public void Form(String token, int workflow_content_id, int wk_point_id) {
        if (evaluationView != null){
            evaluationModel.Form( token,  workflow_content_id, wk_point_id,this);
        }
        if (evaluationView != null){
            evaluationView.showProgress();
        }
    }

    @Override
    public void onSucceed(FormResponse.Result result) {
        if (evaluationView != null){
            evaluationView.loadForms(result);
            evaluationView.hideProgress();
        }
    }
}
