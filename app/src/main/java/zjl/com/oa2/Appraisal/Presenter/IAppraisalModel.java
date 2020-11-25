package zjl.com.oa2.Appraisal.Presenter;

public interface IAppraisalModel {

    void QueryCarAssess(String token,String car_branch,String car_model,int car_year,
                        int page_count,int order,IAppraisalListener listener);
}
