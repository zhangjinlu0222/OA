package zjl.com.oa.Response;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public class LoginResponse  extends BaseResponse{
    private Data Result;

    public Data getResult() {
        return Result;
    }

    /**
     * Created by Administrator on 2018/3/1.
     */

    public class Data {
        private String role_id;
        private String true_name;
        private String phone;
        private String dep_name;
        private String token;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        private int user_id;

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTrue_name() {
            return true_name;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }


        public String getRole_id() {
            return role_id;
        }

        public String getToken() {
            return token;
        }
    }
}
