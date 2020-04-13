package zjl.com.oa.Bean;

/**
 * Created by Administrator on 2018/4/3.
 */

public class WorkFlowGroupData {
    private String title;
    private String date;
    private String state;//步骤状态

    private String proc_type_id;

    private String status;//表单状态
    private String w_pot_id;
    private int reedit_flag;
    private int edit; //是否是管理员登录

    public String getProcTypeId() {
        return proc_type_id;
    }

    public void setProcTypeId(String proc_type_id) {
        this.proc_type_id = proc_type_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEdit() {
        return edit;
    }

    public void setEdit(int edit) {
        this.edit = edit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public int getReedit_flag() {
        return reedit_flag;
    }

    public void setReedit_flag(int reedit_flag) {
        this.reedit_flag = reedit_flag;
    }
}
