package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class GPSResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    public class Result {
        private String lat;
        private String lon;
        private String is_online;
        private String gps_time;

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }

        public String getIs_online() {
            return is_online;
        }

        public String getGps_time() {
            return gps_time;
        }
    }
}