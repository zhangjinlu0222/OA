package zjl.com.oa2.Bean;

/**
 * Created by Administrator on 2018/3/16.
 */

public class UploadCarPhotosType {
    private static int type_id;
    //1=上传车照片
    //2=收车
    //3=装GPS
    //4=签约
    //5=通知业务首扣
    //6=放款结束（不传照片，备注给空格）
    //7=实地考察
    //8=抵押登记
    //9=风控审核（不传照片，备注给空格）
    //11=展期费（不传照片，备注给空格）
    //11=等待签约续贷（不传照片，备注给空格）
    //12=签约续贷

    public static int getType_id(int wk_point_id) {
        switch (wk_point_id){
            case 4://上传照片
                type_id = 1;
                break;
            case 16://收车
                type_id = 2;
                break;
            case 17://钥匙,GPS
                type_id = 3;
                break;
            case 15://签约
                type_id = 4;
                break;
            case 21://首扣通知
                type_id = 5;
                break;
            case 22:
                type_id = 6;
                break;
            case 8://实地考察
                type_id = 7;
                break;
            case 18://抵押登记
                type_id = 8;
                break;
            case 19://风控审核
                type_id = 9;
                break;
            case 28://等待签约续贷
                type_id = 11;
                break;
            case 29://签约续贷
                type_id = 4;
                break;
        }
        return type_id;
    }
}
