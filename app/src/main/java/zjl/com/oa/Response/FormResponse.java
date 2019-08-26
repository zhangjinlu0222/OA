package zjl.com.oa.Response;

import java.util.List;

import zjl.com.oa.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class FormResponse extends BaseResponse{

    public Result Result;
    public void setResult(Result Result) {
        this.Result = Result;
    }
    public Result getResult() {
        return Result;
    }

    /**
     * Created by Administrator on 2018/3/2.
     */
    public class Result {

        public List<Form> FormList;
        public void setFormList(List<Form> FormList) {
            this.FormList = FormList;
        }
        public List<Form> getFormList() {
            return FormList;
        }

        public class Form {

            public int w_pot_id;
            private String title_img;
            public String control_title;
            public int sequence;
            public String data_field;
            public int keyboard_style;
            public String place_holder;
            public String unit;
            public String bg_color;
            public String front_color;
            public int control_style;
            public boolean read_only;
            public int con_lenth;
            public String data_con;
            public String submit_field;
            public int status;
            public List<String> imgs;

            public String getTitle_img() {
                return title_img;
            }

            public void setTitle_img(String title_img) {
                this.title_img = title_img;
            }

            public void setW_pot_id(int w_pot_id) {
                this.w_pot_id = w_pot_id;
            }
            public int getW_pot_id() {
                return w_pot_id;
            }

            public void setControl_title(String control_title) {
                this.control_title = control_title;
            }
            public String getControl_title() {
                return control_title;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }
            public int getSequence() {
                return sequence;
            }

            public void setData_field(String data_field) {
                this.data_field = data_field;
            }
            public String getData_field() {
                return data_field;
            }

            public void setKeyboard_style(int keyboard_style) {
                this.keyboard_style = keyboard_style;
            }
            public int getKeyboard_style() {
                return keyboard_style;
            }

            public void setPlace_holder(String place_holder) {
                this.place_holder = place_holder;
            }
            public String getPlace_holder() {
                return place_holder;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
            public String getUnit() {
                return unit;
            }

            public void setBg_color(String bg_color) {
                this.bg_color = bg_color;
            }
            public String getBg_color() {
                return bg_color;
            }

            public void setFront_color(String front_color) {
                this.front_color = front_color;
            }
            public String getFront_color() {
                return front_color;
            }

            public void setControl_style(int control_style) {
                this.control_style = control_style;
            }
            public int getControl_style() {
                return control_style;
            }

            public boolean isRead_only() {
                return read_only;
            }

            public void setRead_only(boolean read_only) {
                this.read_only = read_only;
            }

            public void setCon_lenth(int con_lenth) {
                this.con_lenth = con_lenth;
            }
            public int getCon_lenth() {
                return con_lenth;
            }

            public void setData_con(String data_con) {
                this.data_con = data_con;
            }
            public String getData_con() {
                return data_con;
            }
            public void setSubmit_field(String submit_field) {
                this.submit_field = submit_field;
            }
            public String getSubmit_field() {
                return submit_field;
            }

            public void setStatus(int status) {
                this.status = status;
            }
            public int getStatus() {
                return status;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }
            public List<String> getImgs() {
                return imgs;
            }

        }
    }
}
