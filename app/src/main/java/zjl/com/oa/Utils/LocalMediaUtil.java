package zjl.com.oa.Utils;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DebugUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalMediaUtil {

    public static List<String> getStringList(List<LocalMedia> arg){
        List<String> list = new ArrayList<>();
        for (LocalMedia media:arg){
            if (media.getCompressPath()!= null && media.getCompressPath().length() >0){
                list.add(media.getCompressPath());

            }else{
                list.add(media.getPath());}
        }
        return list;
    }
    public static List<LocalMedia> getMediaList(List<String> arg){
        List<LocalMedia> list = new ArrayList<>();
        for (String path:arg){

            LocalMedia media = new LocalMedia();
            media.setPath(path);

            list.add(media);
        }
        return list;
    }

    public static String mediaType(String path) {
        if (path != null && path.length() > 0) {
            if (path.endsWith(".mp4") || path.endsWith(".avi")
                    || path.endsWith(".3gpp") || path.endsWith(".3gp") || path.startsWith(".mov")) {
                return "video/mp4";
            } else if (path.endsWith(".PNG") || path.endsWith(".png") || path.endsWith(".jpeg")
                    || path.endsWith(".gif") || path.endsWith(".GIF") || path.endsWith(".jpg")
                    || path.endsWith(".webp") || path.endsWith(".WEBP") || path.endsWith(".JPEG")) {
                return "image/jpeg";
            } else if (path.endsWith(".mp3") || path.endsWith(".amr")
                    || path.endsWith(".aac") || path.endsWith(".war")
                    || path.endsWith(".flac")) {
                return "audio/mpeg";
            }
        }
        return "image/jpeg";
    }
}
