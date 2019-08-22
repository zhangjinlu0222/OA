package zjl.com.oa.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/30.
 */

public class DateCheck {
    public static boolean isValidDate(String str) {
           boolean convertSuccess=true;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            try {
                  format.setLenient(false);
                  format.parse(str);
                } catch (ParseException e) {
                  convertSuccess=false;
              }
          return convertSuccess;
     }
}
