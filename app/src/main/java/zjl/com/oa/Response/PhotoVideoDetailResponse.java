package zjl.com.oa.Response;

import java.util.List;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class PhotoVideoDetailResponse extends BaseResponse{

    private Result Result;
    public void setResult(Result Result){
        this.Result = Result;
    }
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */
    public static class Result {

        private List<String> thum;
        private List<String> big;
        public void setThum(List<String> thum) {
            this.thum = thum;
        }
        public List<String> getThum() {
            return thum;
        }

        public void setBig(List<String> big) {
            this.big = big;
        }
        public List<String> getBig() {
            return big;
        }
    }
}
