package zjl.com.oa.Response;

import java.util.List;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class SearchResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */
    public class Result {
        private List<String> proc_type_list;
        private List<String> status_type_list;

        public void setProc_type_list(List<String> proc_type_list) {
            this.proc_type_list = proc_type_list;
        }

        public List<String> getProc_type_list() {
            return proc_type_list;
        }

        public void setStatus_type_list(List<String> status_type_list) {
            this.status_type_list = status_type_list;
        }

        public List<String> getStatus_type_list() {
            return status_type_list;
        }
    }
}
