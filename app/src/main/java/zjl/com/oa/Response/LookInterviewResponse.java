package zjl.com.oa.Response;

import java.util.ArrayList;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public class LookInterviewResponse extends BaseResponse{

    public Result Result;

    public Result getResult() {
        return Result;
    }
    public void setResult(LookInterviewResponse.Result result) {
        Result = result;
    }

    /**
     * Created by Administrator on 2018/3/1.
     */

    public class Result {
        public void setPoint_fee(String point_fee) {
            this.point_fee = point_fee;
        }

        private String point_fee;

        private ArrayList<String> source_from;
        private ArrayList<String> proc_type;

        private ArrayList<Integer> source_from_new;
        private ArrayList<Integer> proc_type_new;

        public String getPoint_fee() {
            return point_fee;
        }

        public ArrayList<String> getSource_from() {
            return source_from;
        }

        public void setSource_from(ArrayList<String> source_from) {
            this.source_from = source_from;
        }

        public ArrayList<String> getProc_type() {
            return proc_type;
        }

        public void setProc_type(ArrayList<String> proc_type) {
            this.proc_type = proc_type;
        }

        public ArrayList<Integer> getSource_from_new() {
            return source_from_new;
        }

        public void setSource_from_new(ArrayList<Integer> source_from_new) {
            this.source_from_new = source_from_new;
        }

        public ArrayList<Integer> getProc_type_new() {
            return proc_type_new;
        }

        public void setProc_type_new(ArrayList<Integer> proc_type_new) {
            this.proc_type_new = proc_type_new;
        }
    }
}
