package zjl.com.oa.Response;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class SearchNameResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */

    public class Result {
        public WorkFlow[] car_list;
        public String profit_amount;
        public String actual_profit_amount;
        public String schedule_id;
        public String return_date;
        public String name;

        public WorkFlow[] getCar_list() {
            return car_list;
        }

        public String getProfit_amount() {
            return profit_amount;
        }

        public String getActual_profit_amount() {
            return actual_profit_amount;
        }

        public String getSchedule_id() {
            return schedule_id;
        }

        public String getReturn_date() {
            return return_date;
        }

        public String getName(){return this.name;}
    }

    public class WorkFlow{
        public String w_con_id;
        public String car;

        public String getW_con_id() {
            return w_con_id;
        }

        public String getCar() {
            return car;
        }
    }
}
