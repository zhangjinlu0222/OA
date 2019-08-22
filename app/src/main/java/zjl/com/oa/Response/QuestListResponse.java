package zjl.com.oa.Response;

import java.util.List;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class QuestListResponse extends BaseResponse{

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
        private List<Data> Data ;

        public int getCreate_wf() {
            return create_wf;
        }

        public void setCreate_wf(int create_wf) {
            this.create_wf = create_wf;
        }

        private int create_wf;

        public void setData(List<Data> Data){
            this.Data = Data;
        }
        public List<Data> getData(){
            return this.Data;
        }

        /**
         * Created by Administrator on 2018/3/2.
         */
        public static class Data {
            private List<Next_approval_list> next_approval_list ;

            private String workflow_content_id;

            private String start_time;

            private String user_name;

            private String car_type;

            private String proc_type_name;

            private int detail_flag = -1;

            private int process_flag = -1;

            private String customer_name;
            private String status;

            private int oper_flag = -1;

            private String refinance = "";

            public int getOper_flag() {
                return oper_flag;
            }

            public void setOper_flag(int oper_flag) {
                this.oper_flag = oper_flag;
            }
            public String getProc_type_name() {
                return proc_type_name;
            }

            public void setProc_type_name(String proc_type_name) {
                this.proc_type_name = proc_type_name;
            }
            public int getDetail_flag() {
                return detail_flag;
            }

            public void setDetail_flag(int detail_flag) {
                this.detail_flag = detail_flag;
            }

            public int getProcess_flag() {
                return process_flag;
            }

            public void setProcess_flag(int process_flag) {
                this.process_flag = process_flag;
            }

            public void setNext_approval_list(List<Next_approval_list> next_approval_list){
                this.next_approval_list = next_approval_list;
            }
            public List<Next_approval_list> getNext_approval_list(){
                return this.next_approval_list;
            }
            public void setWorkflow_content_id(String workflow_content_id){
                this.workflow_content_id = workflow_content_id;
            }
            public String getWorkflow_content_id(){
                return this.workflow_content_id;
            }
            public void setStart_time(String start_time){
                this.start_time = start_time;
            }
            public String getStart_time(){
                return this.start_time;
            }
            public void setUser_name(String user_name){
                this.user_name = user_name;
            }
            public String getUser_name(){
                return this.user_name;
            }
            public void setCar_type(String car_type){
                this.car_type = car_type;
            }
            public String getCar_type(){
                return this.car_type;
            }
            public void setCustomer_name(String customer_name){
                this.customer_name = customer_name;
            }
            public String getCustomer_name(){
                return this.customer_name;
            }
            public void setStatus(String status){
                this.status = status;
            }
            public String getStatus(){return this.status;}

            public String getRefinance() {
                return refinance;
            }

            public void setRefinance(String refinance) {
                this.refinance = refinance;
            }

            /**
             * Created by Administrator on 2018/3/2.
             */
            public static class Next_approval_list {
                private String workflow_point_id;

                private String workflow_name;

                private String status;

                private int role_flag;

                private String role_id_now;
                private int point_oper_flag;

                private String  point_oper_name;

                public int getPoint_oper_flag() {
                    return point_oper_flag;
                }

                public void setPoint_oper_flag(int point_oper_flag) {
                    this.point_oper_flag = point_oper_flag;
                }

                public String getPoint_oper_name() {
                    return point_oper_name;
                }

                public void setPoint_oper_name(String point_oper_name) {
                    this.point_oper_name = point_oper_name;
                }

                public int getRole_flag() {
                    return role_flag;
                }

                public void setRole_flag(int role_flag) {
                    this.role_flag = role_flag;
                }

                public String getRole_id_now() {
                    return role_id_now;
                }

                public void setRole_id_now(String role_id_now) {
                    this.role_id_now = role_id_now;
                }

                public void setWorkflow_point_id(String workflow_point_id){
                    this.workflow_point_id = workflow_point_id;
                }
                public String getWorkflow_point_id(){
                    return this.workflow_point_id;
                }
                public void setWorkflow_name(String workflow_name){
                    this.workflow_name = workflow_name;
                }
                public String getWorkflow_name(){
                    return this.workflow_name;
                }
                public void setStatus(String status){
                    this.status = status;
                }
                public String getStatus(){
                    return this.status;
                }

            }
        }

    }
}
