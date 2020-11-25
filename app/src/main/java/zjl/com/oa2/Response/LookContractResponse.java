package zjl.com.oa2.Response;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class LookContractResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */
    public class Result {
        private String url;

        private String android_url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAndroid_url() {
            return android_url;
        }

        public void setAndroid_url(String android_url) {
            this.android_url = android_url;
        }
    }
}
