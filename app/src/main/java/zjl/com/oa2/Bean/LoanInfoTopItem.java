package zjl.com.oa2.Bean;

import java.io.Serializable;

public class LoanInfoTopItem implements Serializable {
    private String name;
    private String name_value;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName_value(String name_value) {
        this.name_value = name_value;
    }
    public String getName_value() {
        return name_value;
    }
}
