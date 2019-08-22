package zjl.com.oa.ApplicationConfig;

/**
 * Created by Administrator on 2018/4/2.
 */

public class Authority {
    public static String FKJL = "fkjl";//风控经理
    public static String ADMIN = "admin"; //管理员
    public static String YWJL = "ywjl";//业务经理
    public static String CW = "cw";//财务
    public static String MQ = "mq";//面签

    public static boolean isAdmin(String arg){
        if ( !"".equals(arg) && arg.equals(ADMIN)){
            return true;
        }
        return false;
    }
    public static  boolean isFKJL(String arg){
        if ( !"".equals(arg) && arg.equals(FKJL)){
            return true;
        }
        return false;
    }
    public static  boolean isYWJL(String arg){
        if ( !"".equals(arg) && arg.equals(YWJL)){
            return true;
        }
        return false;
    }
    public static  boolean isCW(String arg){
        if ( !"".equals(arg) && arg.equals(CW)){
            return true;
        }
        return false;
    }
    public static  boolean isMQ(String arg){
        if ( !"".equals(arg) && arg.equals(MQ)){
            return true;
        }
        return false;
    }
}
