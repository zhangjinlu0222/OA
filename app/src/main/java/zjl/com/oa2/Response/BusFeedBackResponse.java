package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class BusFeedBackResponse extends BaseResponse{

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
        private String car_assess_amount;

        private String sure_amount_remark;


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
    }
}
