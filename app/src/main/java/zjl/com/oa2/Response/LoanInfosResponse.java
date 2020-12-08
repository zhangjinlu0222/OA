package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class LoanInfosResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    public class Result {
        public List<LoanInfo> Data;

        public List<LoanInfo> getData() {
            return Data;
        }

        public class LoanInfo{
            String amount;
            String car_type;
            String contract_date;
            String init;
            String name;

            public String getAmount() {
                return amount;
            }

            public String getInit() {
                return init;
            }

            public String getName() {
                return name;
            }

            public String getCar_type() {
                return car_type;
            }

            public String getContract_date() {
                return contract_date;
            }
            }
    }
}
