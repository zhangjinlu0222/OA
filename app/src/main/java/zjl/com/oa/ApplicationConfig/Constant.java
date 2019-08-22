package zjl.com.oa.ApplicationConfig;

import android.os.Looper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/11.
 */

public class Constant {

    public static List<String> VisitorOriginal = new LinkedList<>(Arrays.asList("自有客户","同行介绍","400电话","其他"));
    public static List<String> MettingMortgageType = new LinkedList<>(Arrays.asList("抵押","质押全款","质押按揭","质押二押"));
    public static List<String> LoanPayBackWay = new LinkedList<>(Arrays.asList("等本等息","先息后本"));

    public static String WordsLeft = "/100字";

//   本地环境
//    public static final String BASE_URL = "http://192.168.1.119:4806/";
//    public static final String BASE_URL = "http://192.168.1.200:9000/";
//   测试环境
    public static final String BASE_URL = "https://api.clxchina.cn:8200/";
//   正式环境
//    public static final String BASE_URL = "https://api.clxchina.cn:8100/";

    public static final int MAXCOUNT = 50;
    public static final int MINCOUNT = 1;

    /*
    * Mob ShareSDK 配置信息
    * */
    public static final String Appkey = "27ebc50f43115";
    public static final String AppSecret = "88deb9058f4e5db152e2eaa4d23c1b7a";

    //操作成功
    public static int Succeed = 10000;
    public static int LoginAnotherPhone = 20016;
    public static int noData = 30001;
    public static int noMoreData = 30002;
    public static String Action_Login = "账号在其他设备已登录，请重新登录";

    public static final String Login = "WebApi/V1/User/Login";
    public static final String LoginOut = "WebApi/V1/User/LoginOut";
    public static final String ModifyPwd  = "WebApi/V1/User/ModifyPwd";
    public static final String UpgradeVersion = "WebApi/V1/Base/UpgradeVersion";

    public static final String Coming = "WebApi/V1/Workflow/Coming";
    public static final String WorkList = "WebApi/V1/Workflow/WrokflowList";
    public static final String WrokflowListOrder = "WebApi/V1/Workflow/WorkflowListOrder";
    public static final String WorkListPage = "WebApi/V1/Workflow/WrokflowListPage";
    public static final String WorkflowListAdvPage = "WebApi/V1/Workflow/WorkflowListAdvPage";
    public static final String Interview = "WebApi/V1/Workflow/Interview";
    public static final String InputInfo = "WebApi/V1/Workflow/InputInfo";
    public static final String EndWorkflow = "WebApi/V1/Workflow/EndWorkflow";
    public static final String InfoCheck = "WebApi/V1/Workflow/InfoCheck";
    public static final String Assess = "WebApi/V1/Workflow/Assess";
    public static final String UploadCarPhoto = "WebApi/V1/Workflow/UploadCarPhoto";
    public static final String CarPhoto = "WebApi/V1/Workflow/CarPhoto";
    public static final String SureAmount = "WebApi/V1/Workflow/SureAmount";
    public static final String LookSureAmount = "WebApi/V1/Workflow/LookSureAmount";
    public static final String SureAmountReturn = "WebApi/V1/Workflow/SureAmountReturn";
    public static final String BusFeedback = "WebApi/V1/Workflow/BusFeedback";
    public static final String BusFirstFeedback = "WebApi/V1/Workflow/BusFirstFeedback";
    public static final String LookFeedbackResult = "WebApi/V1/Workflow/LookFeedbackResult";
    public static final String FeedbackResult = "WebApi/V1/Workflow/FeedbackResult";
    public static final String LookInformSigned = "WebApi/V1/Workflow/LookInformSigned";
    public static final String InformSigned = "WebApi/V1/Workflow/InformSigned";
    public static final String LookBeginSigned = "WebApi/V1/Workflow/LookBeginSigned";
    public static final String BeginSigned = "WebApi/V1/Workflow/BeginSigned";
    public static final String LookLoanApplication = "WebApi/V1/Workflow/LookLoanApplication";
    public static final String LoanApplication = "WebApi/V1/Workflow/LoanApplication";
    public static final String Flowprocess = "WebApi/V1/Workflow/flowprocess";
    public static final String Detail = "WebApi/V1/Workflow/Detail";
    public static final String LookFirstCharge = "WebApi/V1/Workflow/LookFirstCharge";
    public static final String PointEdit = "WebApi/V1/Workflow/PointEdit";
    public static final String PhotoVideoDetail = "WebApi/V1/Workflow/PhotoVideoDetail";
    public static final String PleDgeAssess = "WebApi/V1/Workflow/PleDgeAssess";
    public static final String PleDgeAssessTwo = "WebApi/V1/Workflow/PleDgeAssessTwo";
    public static final String FirstSureAmount = "WebApi/V1/Workflow/FirstSureAmount";
    public static final String LookFirstSureAmount = "WebApi/V1/Workflow/LookFirstSureAmount";
    public static final String PointOpertState = "WebApi/V1/Workflow/PointOpertState";
    public static final String CloseFlow = "WebApi/V1/Workflow/CloseFlow";
    public static final String FirstFeedbackResult = "WebApi/V1/Workflow/FirstFeedbackResult";
    public static final String LookFirstFeedbackResult  = "WebApi/V1/Workflow/LookFirstFeedbackResult";
    public static final String LookInterview  = "WebApi/V1/Workflow/LookInterview";
    public static final String AdvanceSecInfo  = "WebApi/V1/Base/AdvanceSecInfo";
    public static final String CreateRefinance  = "WebApi/V1/Refinance/CreateRefinance";
    public static final String InfoCheckRefinance  = "WebApi/V1/Refinance/InfoCheckRefinance";
    public static final String InfoCheckRefinanceTwo  = "WebApi/V1/Refinance/InfoCheckRefinanceTwo";
    public static final String AssessRefinance  = "WebApi/V1/Refinance/AssessRefinance";
    public static final String AuditRefinance  = "WebApi/V1/Refinance/AuditRefinance";
    public static final String ContractDetail  = "WebApi/V1/Refinance/ContractDetail";
    public static final String Form  = "WebApi/V1/FormControl/Form";
    public static final String RecoverWorkflow  = "WebApi/V1/Workflow/RecoverWorkflow";
    public static final String QueryCarAssess  = "WebApi/V1/Assess/QueryCarAssess";
    public static final String ApplyforRefinance  = "WebApi/V1/Refinance/ApplyforRefinance";
}
