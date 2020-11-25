package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

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
        private List<Seller> seller_list;

        public List<String> getProc_type_list() {
            return proc_type_list;
        }

        public List<String> getStatus_type_list() {
            return status_type_list;
        }

        public class Seller{
            String user_id;
            String name;

            public String getUser_id() {
                return user_id;
            }

            public String getName() {
                return name;
            }
        }

        public List<Seller> getSeller_list() {
            return seller_list;
        }
    }
}
