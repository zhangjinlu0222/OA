package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public class GetSigningResponse extends BaseResponse{
    private Result Result;

    public Result getResult() {
        return Result;
    }

    public void setResult(Result result) {
        this.Result = result;
    }

    /**
     * Created by Administrator on 2018/3/1.
     */


    public static class Result {
        private List<Data> list ;

        public void setList(List<Data> list) {
            this.list = list;
        }

        public List<Data> getList(){
            return this.list;
        }
        public  static class Data {
            private String l_key;
            private String l_value;

            public void setL_key(String l_key) {
                this.l_key = l_key;
            }

            public void setL_value(String l_value) {
                this.l_value = l_value;
            }

            public String getKey() {
                return l_key;
            }

            public String getValue() {
                return l_value;
            }
        }
    }
}
