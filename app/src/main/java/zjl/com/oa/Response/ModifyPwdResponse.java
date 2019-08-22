package zjl.com.oa.Response;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class ModifyPwdResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */
    public class Result {
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private String token;
    }
}
