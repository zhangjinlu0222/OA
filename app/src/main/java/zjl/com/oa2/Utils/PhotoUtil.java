package zjl.com.oa2.Utils;

/**
 * Created by Administrator on 2018/4/10.
 */

public class PhotoUtil {
    public static boolean isPhoto(String path){
        if (path != null && path.length() >0){
            String filePath = path.toLowerCase();
            if (filePath.endsWith("jpg")
                    || filePath.endsWith("png")
                    || filePath.endsWith("bmp")
                    || filePath.endsWith("jpeg")){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
