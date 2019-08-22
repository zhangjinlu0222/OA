package zjl.com.oa.Response;

import java.util.List;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class EvaluationQuotaResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */
    public class Result {

        private String car_break_rules;

        private String insurance;

        private String remark;

        public String getMarket_amount() {
            return market_amount;
        }

        public void setMarket_amount(String market_amount) {
            this.market_amount = market_amount;
        }

        public String getTake_amount() {
            return take_amount;
        }

        public void setTake_amount(String take_amount) {
            this.take_amount = take_amount;
        }

        private String market_amount;
        private String take_amount;


        private List<String> ass_imgs ;

        private List<String> car_imgs ;

        public String getCar_break_rules(){
            return this.car_break_rules;
        }
        public String getInsurance(){
            return this.insurance;
        }
        public String getRemark(){
            return this.remark;
        }
        public List<String> getAss_imgs(){
            return this.ass_imgs;
        }
        public List<String> getCar_imgs(){
            return this.car_imgs;
        }
    }
}
