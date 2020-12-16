package zjl.com.oa2.ApplicationConfig;

import zjl.com.oa2.FeedBack.View.FeedBackActivity;
import zjl.com.oa2.Meeting.View.EnteringOrderActivity;
import zjl.com.oa2.RenewLoan.View.RenewLoanActivity;
import zjl.com.oa2.RenewLoan.View.RenewLoanWaitSign;
import zjl.com.oa2.RenewLoanCompletation.View.RenewLoanCompletationActivity;
import zjl.com.oa2.Sign.View.BeginSignActivity;
import zjl.com.oa2.Sign.View.SignActivity;
import zjl.com.oa2.UploadPhotos.View.RiskManagerActivity;
import zjl.com.oa2.UploadPhotos.View.UploadPhotosActivity;

/**
 * Created by Administrator on 2018/3/12.
 */

public class Activitys {

    public static Class getClass(String arg){
        Class destClass = null;
            switch (arg){
                case "1":
//                    destClass =  Visitor.class;//来访
                    destClass =  RenewLoanActivity.class;//来访
                    break;
                case "2":
                    destClass =  EnteringOrderActivity.class;//面谈（取消）
//                    destClass =  MettingActivity.class;//面谈
                    break;
                case "3":
//                    destClass =  InformationCheck.class;//信息核查
                    destClass =  RenewLoanActivity.class;//信息核查
                    break;
                case "4":
//                    destClass =  UploadPhotosActivity.class;//车辆照片
                    destClass =  RenewLoanActivity.class;//车辆照片
                    break;
                case "5":
//                    destClass =  Evaluation.class;//评估报告
                    destClass =  RenewLoanActivity.class;//评估报告
                    break;
                case "6":
//                    destClass =  EvaluationQuotaActivity.class;//初步定额
                    destClass =  RenewLoanActivity.class;//初步定额
                    break;
                case "7":
//                    destClass =  BusinessFeedBackActivity.class;//初步反馈
                    destClass =  RenewLoanActivity.class;//初步反馈
                    break;
                case "8":
//                    destClass =  OTSInvestActivity.class;//实地考察
                    destClass =  RenewLoanActivity.class;//实地考察
                    break;
                case "9":
                    destClass =  FeedBackActivity.class;//初步反馈结果
                    break;
                case "10":
//                    destClass =  EvaluationQuotaActivity.class;//最终定额
                    destClass =  RenewLoanActivity.class;//最终定额
                    break;
                case "11":
//                    destClass =  BusinessFeedBackActivity.class;//业务反馈
                    destClass =  RenewLoanActivity.class;//业务反馈
                    break;
                case "12":
                    destClass =  FeedBackActivity.class;//反馈结果
                    break;
                case "13":
//                    destClass =  InformSignActivity.class;//通知签约
                    destClass =  RenewLoanActivity.class;//通知签约
                    break;
                case "14":
                    destClass =  BeginSignActivity.class;//开始签约
                    break;
                case "15":
//                    destClass =  SignActivity.class;//签约
                    destClass =  RenewLoanActivity.class;//签约
                    break;
                case "16":
                    destClass =  UploadPhotosActivity.class;//收车
                    break;
                case "17":
//                    destClass =  UploadPhotosActivity.class;//GPS，钥匙
                    destClass =  RenewLoanActivity.class;//GPS，钥匙
                    break;
                case "18":
//                    destClass =  UploadPhotosActivity.class;//抵押登记
                    destClass =  RenewLoanActivity.class;//抵押登记
                    break;
                case "19":
                    destClass =  RiskManagerActivity.class;//风控审核
                    break;
                case "20":
//                    destClass =  LoanRequestActivity.class;//通知财务
                    destClass =  RenewLoanActivity.class;//通知财务
                    break;
                case "21":
//                    destClass =  TransferVoucherActivity.class;//首扣通知
                    destClass =  RenewLoanActivity.class;//首扣通知
                    break;
                case "22":
//                    destClass =  TransferFinishActivity.class;//放款结束
                    destClass =  RenewLoanActivity.class;//放款结束
                    break;
                case "23":
                    destClass =  RenewLoanActivity.class;//信息核查续贷
                    break;
                case "24":
                    destClass =  RenewLoanActivity.class;//评估报告续贷
                    break;
                case "25":
                    destClass =  RenewLoanActivity.class;//评估审核续贷
                    break;
                case "26":
                    destClass =  RenewLoanActivity.class;//展期费续贷
                    break;
                case "27":
                    destClass =  RenewLoanActivity.class;//合同详情续贷
                    break;
                case "28":
                    destClass =  RenewLoanWaitSign.class;//等待签约续贷
                    break;
                case "29":
                    destClass =  SignActivity.class;//签约续贷
                    break;
                case "30":
//                    destClass =  EnteringOrderActivity.class;//录入资料
                    destClass =  RenewLoanActivity.class;//录入资料
                    break;
                case "31":
                    destClass =  RenewLoanActivity.class;//续贷申请
                    break;
                case "32":
                    destClass =  RenewLoanActivity.class;//续贷上传合同
                    break;
                case "33":
                    destClass =  RenewLoanActivity.class;//华融车贷面谈
                    break;
                case "34":
                    destClass =  RenewLoanActivity.class;//华融信贷资料上传
                    break;
                case "35":
                    destClass =  RenewLoanActivity.class;//华融信贷审核
                    break;
                case "36":
                    destClass =  RenewLoanActivity.class;//华融信贷家访
                    break;
                case "37":
                    destClass =  RenewLoanActivity.class;//华融信贷定额
                    break;
                case "38":
                    destClass =  RenewLoanActivity.class;//华融信贷签约
                    break;
                case "39":
                    destClass =  RenewLoanActivity.class;//华融信贷放款
                    break;
                case "40":
                    destClass =  RenewLoanCompletationActivity.class;//续贷结束
                    break;
                case "41":
                    destClass =  RenewLoanActivity.class;//过户办理
                    break;
                default:
                    destClass = RenewLoanActivity.class;
            }
        return destClass;
    }
}