package zjl.com.oa2.Response;

import java.io.Serializable;
import java.util.List;

import zjl.com.oa2.Base.BaseResponse;
import zjl.com.oa2.Bean.LoanInfoTopItem;
import zjl.com.oa2.Bean.Repayment;

/**
 * Created by Administrator on 2018/3/2.
 */
public class LoanDetailResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */

    public class Result implements Serializable {
        public String amount;
        public String repayment_type;
        public String month_rate;
        public String loan_length;
        public String actual_amount;
        public List<Repayment> list;
        public List<LoanInfoTopItem> cal_list;

        public String getAmount() {
            return amount;
        }

        public String getRepayment_type() {
            return repayment_type;
        }

        public String getMonth_rate() {
            return month_rate;
        }

        public String getLoan_length() {
            return loan_length;
        }

        public String getActual_amount() {
            return actual_amount;
        }

        public List<Repayment> getList() {
            return list;
        }

        public List<LoanInfoTopItem> getCal_list() {
            return cal_list;
        }
    }
}
