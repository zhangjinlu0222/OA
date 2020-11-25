package zjl.com.oa2.Appraisal.Presenter;

import okhttp3.RequestBody;
import retrofit2.http.Part;

public interface IAppraisalPresenter {
    void QueryCarAssess(String token,String car_branch,String car_model,int car_year,
                        int page_count,int order);
}
