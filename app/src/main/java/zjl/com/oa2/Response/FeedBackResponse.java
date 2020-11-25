package zjl.com.oa2.Response;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public class FeedBackResponse extends BaseResponse{
    public Data Result;

    public Data getResult() {
        return Result;
    }

    /**
     * Created by Administrator on 2018/3/1.
     */

    public static class Data {
        private String remark;

        public String getRemark() {
            return remark;
        }
    }
}
