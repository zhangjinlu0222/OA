package zjl.com.oa2.ApplicationConfig;

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

    //本地环境 OA2
//    public static final String BASE_URL = "http://192.168.1.210:8200/";
    //测试环境 OA2
    public static final String BASE_URL = "https://api.clxchina.cn:8800/";
    //正式环境 OA2
//    public static final String BASE_URL = "https://api.clxchina.cn:9600/";

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
    public static final String GetUserInfo  = "WebApi/V1//User/GetUserInfo";
    public static final String UpgradeVersion = "WebApi/V1/Base/UpgradeVersion";

    public static final String Coming = "WebApi/V1/Workflow/Coming";
    public static final String WorkList = "WebApi/V1/Workflow/WrokflowList";

    //增加华融车贷，接口更新，2020.3.25
//    public static final String WorkflowListOrder = "WebApi/V1/Workflow/WorkflowListOrder";

    public static final String WorkflowListOrder = "WebApi/V1/HRCreditBus/WorkflowListOrder";
    public static final String WorkListPage = "WebApi/V1/Workflow/WrokflowListPage";

    //新增搜索参数，显示续贷单子，2020.3.26
//    public static final String WorkflowListAdvPage = "WebApi/V1/Workflow/WorkflowListAdvPage";
    public static final String WorkflowListAdvPage = "WebApi/V1/HRCreditBus/WorkflowListAdvPage";

    //新增面谈参数，2020.3.30
//    public static final String Interview = "WebApi/V1/Workflow/Interview";
    public static final String Interview = "WebApi/V1/HRCreditBus/Interview";

    public static final String InputInfo = "WebApi/V1/Workflow/InputInfo";

    //    public static final String EndWorkflow = "WebApi/V1/Workflow/EndWorkflow";
    public static final String HRRefuseProject = "WebApi/V1/HRCreditBus/EndWorkflow";

    public static final String InfoCheck = "WebApi/V1/Workflow/InfoCheck";
    public static final String Assess = "WebApi/V1/Workflow/Assess";
    public static final String UploadCarPhoto = "WebApi/V1/Workflow/UploadCarPhoto";
    public static final String CarPhoto = "WebApi/V1/Workflow/CarPhoto";

    //华融信贷定额接口，2020.4.2
    public static final String SureAmount = "WebApi/V1/Workflow/SureAmount";
    public static final String HRSureAmount  = "WebApi/V1/HRCreditBus/SureAmount";

    public static final String LookSureAmount = "WebApi/V1/Workflow/LookSureAmount";
    public static final String SureAmountReturn = "WebApi/V1/Workflow/SureAmountReturn";

    //2020.4.21,业务反馈接口更新为V2
//    public static final String BusFeedback = "WebApi/V1/Workflow/BusFeedback";
    public static final String BusFeedback = "WebApi/V1/Workflow/BusFeedback_V2";

    public static final String BusFirstFeedback = "WebApi/V1/Workflow/BusFirstFeedback";
    public static final String LookFeedbackResult = "WebApi/V1/Workflow/LookFeedbackResult";
    public static final String FeedbackResult = "WebApi/V1/Workflow/FeedbackResult";
    public static final String LookInformSigned = "WebApi/V1/Workflow/LookInformSigned";
    public static final String InformSigned = "WebApi/V1/Workflow/InformSigned";
    public static final String LookBeginSigned = "WebApi/V1/Workflow/LookBeginSigned";
    public static final String BeginSigned = "WebApi/V1/Workflow/BeginSigned";
    public static final String LookLoanApplication = "WebApi/V1/Workflow/LookLoanApplication";
    public static final String LoanApplication = "WebApi/V1/Workflow/LoanApplication";
//    public static final String Flowprocess = "WebApi/V1/Workflow/flowprocess";

//    public static final String Detail = "WebApi/V1/Workflow/Detail";
    public static final String HRProjectDetail = "WebApi/V1/HRCreditBus/Detail";

    public static final String LookFirstCharge = "WebApi/V1/Workflow/LookFirstCharge";

    //驳回添加业务类型参数，2020.4.7
//    public static final String PointEdit = "WebApi/V1/Workflow/PointEdit";
    public static final String HRRejection = "WebApi/V1/HRCreditBus/PointEdit";

//    public static final String PhotoVideoDetail = "WebApi/V1/Workflow/PhotoVideoDetail";
    public static final String PhotoVideoDetail = "WebApi/V1/HRCreditBus/PhotoVideoDetail";

    public static final String PleDgeAssess = "WebApi/V1/Workflow/PleDgeAssess";
    public static final String PleDgeAssessTwo = "WebApi/V1/Workflow/PleDgeAssessTwo";
    public static final String FirstSureAmount = "WebApi/V1/Workflow/FirstSureAmount";
    public static final String LookFirstSureAmount = "WebApi/V1/Workflow/LookFirstSureAmount";
    //查看贷款详细，2020.11.5
    public static final String LookLoanDetail = "WebApi/V1/Workflow/LookLoanDetail";
//    过户办理，2020.11.9
    public static final String TransferOwnership = "WebApi/V1/Workflow/TransferOwnership";
//    签约，2020.11.10
    public static final String Sign = "WebApi/V1//Workflow/Sign";



    //接单状态更新的新接口，20.3.27
//    public static final String PointOpertState = "WebApi/V1/Workflow/PointOpertState";
    public static final String PointOpertState = "WebApi/V1/HRCreditBus/PointOpertState";


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
    public static final String ContractDetailTwo  = "WebApi/V1/Refinance/ContractDetailTwo";
    public static final String RefinanceFinishFlow  = "WebApi/V1/Refinance/RefinanceFinishFlow";
    public static final String LookRefinanceContract  = "WebApi/V1//Refinance/LookRefinanceContract";

//    public static final String Form  = "WebApi/V1/FormControl/Form";
    public static final String Form  = "WebApi/V1/FormControl/Form_V2";

//    public static final String RecoverWorkflow  = "WebApi/V1/Workflow/RecoverWorkflow";
    public static final String HRRecoverProject  = "WebApi/V1/HRCreditBus/RecoverWorkflow";

    public static final String QueryCarAssess  = "WebApi/V1/Assess/QueryCarAssess";
    public static final String ApplyforRefinance  = "WebApi/V1/Refinance/ApplyforRefinance";
    public static final String FinishFlow  = "WebApi/V1/Workflow/FinishFlow";

    public static final String UploadPhoto  = "WebApi/V1/HRCreditBus/UploadPhoto";
    public static final String FlowProcess  = "WebApi/V1/HRCreditBus/FlowProcess";

    public static final String SearchName  = "WebApi/V1/Schedule/SearchName";
    public static final String SearchCarType  = "WebApi/V1/Schedule/SearchCarType";
    public static final String UpdateReturnSchedule  = "WebApi/V1/Schedule/UpdateReturnSchedule";

    //2020.11.23
    public static final String GetCarGps = "WebApi/V1/Base/GetCarGps";
}
