package zjl.com.oa2.Utils;

/**
 * Created by Administrator on 2018/4/10.
 */

public class VideoUtil {
    public static boolean isVideo(String path){
        if (path.endsWith("mp4")
                || path.endsWith("3gp")){
            return true;
        }else{
            return false;
        }
    }
}
