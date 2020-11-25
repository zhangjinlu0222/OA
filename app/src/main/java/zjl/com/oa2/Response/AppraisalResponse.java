package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

/**
 * Created by Administrator on 2018/3/2.
 */
public class AppraisalResponse extends BaseResponse{

    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    public class Result {

        private List<Data> Data;
        public void setData(List<Data> Data) {
            this.Data = Data;
        }
        public List<Data> getData() {
            return Data;
        }

        public class Data {

            private String car_branch;
            private String car_model;
            private String license_date;
            private String milage;
            private String price;
            private String estamate_date;
            public void setCar_branch(String car_branch) {
                this.car_branch = car_branch;
            }
            public String getCar_branch() {
                return car_branch;
            }

            public void setCar_model(String car_model) {
                this.car_model = car_model;
            }
            public String getCar_model() {
                return car_model;
            }

            public void setLicense_date(String license_date) {
                this.license_date = license_date;
            }
            public String getLicense_date() {
                return license_date;
            }

            public void setMilage(String milage) {
                this.milage = milage;
            }
            public String getMilage() {
                return milage;
            }

            public void setPrice(String price) {
                this.price = price;
            }
            public String getPrice() {
                return price;
            }

            public void setEstamate_date(String estamate_date) {
                this.estamate_date = estamate_date;
            }
            public String getEstamate_date() {
                return estamate_date;
            }

        }
    }
}
