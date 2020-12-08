package zjl.com.oa2.LoanInfoList.Presenter;

public interface ILoanInfoListModel {

    void LoanInfoList(String token,int page_count,String start_date,String  end_date,
                      String  name,String  flag,int order,ILoanInfoListListener listListener);
}
