package zjl.com.oa2.LoanInfoList.Presenter;

import okhttp3.RequestBody;
import retrofit2.http.Part;

public interface ILoanInfoListPresenter {
    void LoanInfoList(String token,int page_count,String start_date,String end_date,
                        String  name,String  flag,int order);
}
