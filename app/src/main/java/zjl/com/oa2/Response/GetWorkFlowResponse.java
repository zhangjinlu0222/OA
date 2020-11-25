package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/1.
 */

public class GetWorkFlowResponse extends BaseResponse {
    private Result Result;

    public Result getResult() {
        return Result;
    }

    public void setResult(Result result) {
        this.Result = result;
    }

    public static class Result {
        public int edit;
        public String wx_share_content;

        public String proc_type_id;

        public String refuse_flag;

        public String getRefuse_flag() {
            return refuse_flag;
        }

        public void setRefuse_flag(String refuse_flag) {
            this.refuse_flag = refuse_flag;
        }

        public String getProc_type_id() {
            return proc_type_id;
        }

        public void setProc_type_id(String proc_type_id) {
            this.proc_type_id = proc_type_id;
        }

        public int getEdit() {
            return edit;
        }

        public void setEdit(int edit) {
            this.edit = edit;
        }

        public String getWx_share_content() {
            return wx_share_content;
        }

        public void setWx_share_content(String wx_share_content) {
            this.wx_share_content = wx_share_content;
        }

        public List<Section> list;

        public List<Section> getList() {
            return list;
        }

        public void setList(List<Section> list) {
            this.list = list;
        }

        public static class Section{
            private String title;
            private String date;
            private String state;
            private String w_pot_id;
            private int reedit_flag;

            public int getReedit_flag() {
                return reedit_flag;
            }

            public void setReedit_flag(int reedit_flag) {
                this.reedit_flag = reedit_flag;
            }

            private List<dict> dict_list;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getW_pot_id() {
                return w_pot_id;
            }

            public void setW_pot_id(String w_pot_id) {
                this.w_pot_id = w_pot_id;
            }

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

            public static class dict{
                private String l_key;

                public dict(String l_key, String l_value) {
                    this.l_key = l_key;
                    this.l_value = l_value;
                }

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
