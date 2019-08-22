package zjl.com.oa.Response;

import java.util.List;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public class GetLoanRequestResponse extends BaseResponse {
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
        public List<Section> list;

        public List<Section> getList() {
            return list;
        }

        public void setList(List<Section> list) {
            this.list = list;
        }

        public class Section{
            private String title;

            private List<dict> dict_list;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<dict> getDict_list() {
                return dict_list;
            }

            public void setDict_list(List<dict> dict_list) {
                this.dict_list = dict_list;
            }

            public class dict{
                private String l_key;

                public String getL_key() {
                    return l_key;
                }

                public void setL_key(String l_key) {
                    this.l_key = l_key;
                }

                public String getL_value() {
                    return l_value;
                }

                public void setL_value(String l_value) {
                    this.l_value = l_value;
                }

                private String l_value;
            }
        }
    }
}
