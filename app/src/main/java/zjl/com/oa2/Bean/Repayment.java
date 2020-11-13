package zjl.com.oa2.Bean;

import java.io.Serializable;

public class Repayment implements Serializable{
    private String issue;
    private String repayment_amount;
    private String date;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getRepayment_amount() {
        return repayment_amount;
    }

    public void setRepayment_amount(String repayment_amount) {
        this.repayment_amount = repayment_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
