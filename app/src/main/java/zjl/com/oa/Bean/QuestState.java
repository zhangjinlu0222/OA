package zjl.com.oa.Bean;

import android.graphics.Color;

/**
 * Created by Administrator on 2018/3/6.
 */

public class QuestState {
    private String[] strings = new String[]{"待办","已办","拒件","结清","逾期"};
    private int[] colors = new int[]{
//            Color.rgb(4,174,15),
            Color.rgb(35,198,200),
            Color.rgb(116,169,237),
            Color.rgb(229,91,91),
            Color.rgb(226,129,25),
    };

    public  String getStateString(String state){
        String str = "";
        switch (state){
            case "1":
                str = strings[0];
                break;
            case "2":
                str = strings[1];
                break;
            case "3":
                str = strings[2];
                break;
            case "4":
                str = strings[3];
                break;
        }
        return str;
    }
    public  int getStateColor(String state){
        int color = 0x74ea9d;
        switch (state){
            case "1":
                color = Color.rgb(35,198,200);
                break;
            case "2":
                color = Color.rgb(116,169,237);
                break;
            case "3":
                color = Color.rgb(229,91,91);
                break;
            case "4":
                color = Color.rgb(169,169,169);
                break;
            case "5":
                color = Color.rgb(226,129,25);
                break;
        }
        return color;
    }
    public  int getStatusNameColor(String name){
        int color = 0x74ea9d;
        switch (name){
            case "待办":
                color = Color.rgb(35,198,200);
                break;
            case "已办":
                color = Color.rgb(116,169,237);
                break;
            case "拒件":
                color = Color.rgb(229,91,91);
                break;
            case "结清":
                color = Color.rgb(169,169,169);
                break;
            case "逾期":
                color = Color.rgb(226,129,25);
                break;
        }
        return color;
    }
    public  static  boolean isQuestDone(String state){
        boolean result = false;
        switch (state){
            case "1":
                result =  false;
                break;
            case "2":
            case "4":
                result =  true;
                break;
            case "3":
                result =  false;
                break;
        }
        return result;
    }
    public  static  boolean isWPDone(String state){
        boolean result = false;
        switch (state){
            case "1":
                result =  true;
                break;
            default:
                result = false;
        }
        return result;
    }
    public  static  boolean isRefused(String state){
        boolean result = false;
        switch (state){
            case "1":
                result =  false;
                break;
            case "2":
            case "4":
                result =  false;
                break;
            case "3":
                result =  true;
                break;
        }
        return result;
    }
}
