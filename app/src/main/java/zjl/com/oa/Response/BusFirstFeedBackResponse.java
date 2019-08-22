package zjl.com.oa.Response;

import java.util.List;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class BusFirstFeedBackResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */
    public class Result {

        //        car_break_rules	String	违章
//        insurance	String	保险
//        remark	String	备注
//        ass_imgs	List<string>	评估图片地址
//        car_imgs	List<string>	车辆照片集合
//        car_assess_amount	String	评估金额
//        sure_amount_remark	String	评估定额时的备注信息
//        market_amount	String	市场价
//        take_amount	String	收车价
//        first_assess_amount	String	初步评估费用范围
//        first_assess_amount_remark	String	初步评估的备注
//        home_photo_remark	String	实地考察的备注
//        home_imgs	string	折数表的图片地址

        private String car_break_rules;

        private String insurance;

        private String remark;
        private String car_assess_amount;

        private String sure_amount_remark;
        private String market_amount;
        private String take_amount;
        private String first_assess_amount;

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


        public String getFirst_assess_amount() {
            return first_assess_amount;
        }

        public void setFirst_assess_amount(String first_assess_amount) {
            this.first_assess_amount = first_assess_amount;
        }

        public String getFirst_assess_amount_remark() {
            return first_assess_amount_remark;
        }

        public void setFirst_assess_amount_remark(String first_assess_amount_remark) {
            this.first_assess_amount_remark = first_assess_amount_remark;
        }

        private String first_assess_amount_remark;





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

        public String getCar_assess_amount() {
            return car_assess_amount;
        }

        public String getSure_amount_remark() {
            return sure_amount_remark;
        }


        private String loan_length;

        public String getLoan_length() {
            return loan_length;
        }

        public void setLoan_length(String loan_length) {
            this.loan_length = loan_length;
        }
    }
}
