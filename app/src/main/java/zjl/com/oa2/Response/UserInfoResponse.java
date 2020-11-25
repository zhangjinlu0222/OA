package zjl.com.oa2.Response;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public class UserInfoResponse extends BaseResponse{
    public Data Result;

    public Data getResult() {
        return Result;
    }

    /**
     * Created by Administrator on 2018/3/1.
     */

    public class Data {
        public String role_id;
        public String true_name;
        public String user_id;
        public String dep_name;
        public String role_name;
        public String phone;
        public String sex;
        public String create_wf;
        public String schedule_flag;

        public String getRole_id() {
            return role_id;
        }

        public String getTrue_name() {
            return true_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getDep_name() {
            return dep_name;
        }

        public String getRole_name() {
            return role_name;
        }

        public String getPhone() {
            return phone;
        }

        public String getSex() {
            return sex;
        }

        public String getCreate_wf() {
            return create_wf;
        }

        public String getSchedule_flag() {
            return schedule_flag;
        }
    }
}
