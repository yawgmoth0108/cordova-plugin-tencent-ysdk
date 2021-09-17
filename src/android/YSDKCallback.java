package cordova.plugin.ysdk;

import android.util.Log;

//import com.tencent.tmgp.yybtestsdk.api.YSDKDemoApi;
//import cordova.plugin.ysdk.YSDKDemoApi;
import com.tencent.ysdk.api.YSDKApi;
import com.tencent.ysdk.framework.common.eFlag;
import com.tencent.ysdk.module.antiaddiction.listener.AntiAddictListener;
import com.tencent.ysdk.module.antiaddiction.listener.AntiRegisterWindowCloseListener;
import com.tencent.ysdk.module.antiaddiction.model.AntiAddictRet;
import com.tencent.ysdk.module.bugly.BuglyListener;
import com.tencent.ysdk.module.pay.PayListener;
import com.tencent.ysdk.module.pay.PayRet;
import com.tencent.ysdk.module.user.PersonInfo;
import com.tencent.ysdk.module.user.UserListener;
import com.tencent.ysdk.module.user.UserLoginRet;
import com.tencent.ysdk.module.user.UserRelationRet;
import com.tencent.ysdk.module.user.WakeupRet;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO GAME 游戏需要根据自己的逻辑实现自己的YSDKCallback对象。
 * YSDK通过UserListener抽象类中的方法将授权或查询结果回调给游戏。
 * 游戏根据回调结果调整UI等。只有设置回调，游戏才能收到YSDK的响应。
 * 这里是Java层回调(设置了Java层回调会优先调用Java层回调, 如果要使用C++层回调则不能设置Java层回调)
 */
public class YSDKCallback implements UserListener, BuglyListener, PayListener, AntiAddictListener, AntiRegisterWindowCloseListener {
    public final String TAG = "YSDKCallback";
    public void OnLoginNotify(UserLoginRet ret) {
        Log.d("YSDK_LOGIN", "YSDKCallback#OnLoginNotify>>>" + ret.toString());
        Log.d(TAG, "called");
        Log.d(TAG, ret.getAccessToken());
        Log.d(TAG, ret.getPayToken());
        Log.d(TAG, "ret.flag" + ret.flag);
        Log.d(TAG, ret.toString());
        //YSDKDemoApi.sShowView.hideProgressBar();
        YSDKApi.reportGameRoleInfo("zoneId", "zoneName", "roleId",
                "roleName", 9, 9, 9, null);
        switch (ret.flag) {
            case eFlag.Succ:
                //YSDKDemoApi.userLoginSuc();
                if (ret.getLoginType() != UserLoginRet.LOGIN_TYPE_TIMER) {
                    // 定时登录，不需要设置防沉迷统计开始
                    YSDKApi.setAntiAddictGameStart();
                }
                break;
            // 游戏逻辑，对登录失败情况分别进行处理
            case eFlag.QQ_UserCancel:
                //YSDKDemoApi.sShowView.showToastTips("用户取消授权，请重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.QQ_LoginFail:
                //YSDKDemoApi.sShowView.showToastTips("QQ登录失败，请重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.QQ_NetworkErr:
                //YSDKDemoApi.sShowView.showToastTips("QQ登录异常，请重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.QQ_NotInstall:
                //YSDKDemoApi.sShowView.showToastTips("手机未安装手Q，请安装后重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.QQ_NotSupportApi:
                //YSDKDemoApi.sShowView.showToastTips("手机手Q版本太低，请升级后重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.WX_NotInstall:
                //YSDKDemoApi.sShowView.showToastTips("手机未安装微信，请安装后重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.WX_NotSupportApi:
                //YSDKDemoApi.sShowView.showToastTips("手机微信版本太低，请升级后重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.WX_UserCancel:
                //YSDKDemoApi.sShowView.showToastTips("用户取消授权，请重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.WX_UserDeny:
                //YSDKDemoApi.sShowView.showToastTips("用户拒绝了授权，请重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.WX_LoginFail:
                //YSDKDemoApi.sShowView.showToastTips("微信登录失败，请重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.Login_TokenInvalid:
                //YSDKDemoApi.sShowView.showToastTips("您尚未登录或者之前的登录已过期，请重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.Login_NotRegisterRealName:
                // 显示登录界面
                //YSDKDemoApi.sShowView.showToastTips("您的账号没有进行实名认证，请实名认证后重试");
                //YSDKDemoApi.userLogout();
                break;
//            case eFlag.Login_Free_Login_Auth_Failed:
//                YSDKDemoApi.sShowView.showToastTips("免登录校验失败，请重启重试");
//                YSDKDemoApi.userLogout();
//                break;
            case eFlag.Login_NeedRegisterRealName:
                // 显示登录页面
                //YSDKDemoApi.sShowView.showToastTips("您的账号没有进行实名认证，请完成实名认证后重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.Login_Free_Login_Auth_Failed:
                //YSDKDemoApi.sShowView.showToastTips("免登录校验失败，请重启重试");
                //YSDKDemoApi.userLogout();
                break;
            case eFlag.Login_User_Logout:
                // 用户手动退出登录（用于用户游客体验模式下，提醒用户退出登录完成实名认证）
                //YSDKDemoApi.sShowView.showToastTips("您已退出登录，请重新登录");
                //YSDKDemoApi.userLogout();
                break;
            default:
                // 显示登录界面
                //YSDKDemoApi.userLogout();
                break;
        }
    }

    public void OnWakeupNotify(WakeupRet ret) {
        Log.d(TAG, "called");
        Log.d(TAG, "flag:" + ret.flag);
        Log.d(TAG, "msg:" + ret.msg);
        Log.d(TAG, "platform:" + ret.platform);
        //AppUtils.updateLoginPlatform(ret.platform);
        // TODO GAME 游戏需要在这里增加处理异账号的逻辑
        if (eFlag.Wakeup_YSDKLogining == ret.flag) {
            // 用拉起的账号登录，登录结果在OnLoginNotify()中回调
        } else if (ret.flag == eFlag.Wakeup_NeedUserSelectAccount) {
            // 异账号时，游戏需要弹出提示框让用户选择需要登录的账号
            Log.d(TAG, "diff account");
            //YSDKDemoApi.choseUserToLogin();
        } else if (ret.flag == eFlag.Wakeup_NeedUserLogin) {
            // 没有有效的票据，登出游戏让用户重新登录
            Log.d(TAG, "need login");
            //YSDKDemoApi.userLogout();
        } else {
            Log.d(TAG, "logout");
            //YSDKDemoApi.userLogout();
        }
    }

    @Override
    public void OnRelationNotify(UserRelationRet relationRet) {
        final String lineBreak = "\n";
        StringBuilder builder = new StringBuilder();
        builder.append("flag:").append(relationRet.flag).append(lineBreak)
                .append("msg:").append(relationRet.msg).append(lineBreak)
                .append("platform:").append(relationRet.platform).append(lineBreak);

        if (relationRet.persons != null && relationRet.persons.size() > 0) {
            PersonInfo personInfo = (PersonInfo) relationRet.persons.firstElement();
            builder.append("UserInfoResponse json:").append(lineBreak)
                    .append("nick_name: ").append(personInfo.nickName).append(lineBreak)
                    .append("open_id: ").append(personInfo.openId).append(lineBreak)
                    .append("userId: ").append(personInfo.userId).append(lineBreak)
                    .append("gender: ").append(personInfo.gender).append(lineBreak)
                    .append("picture_small: ").append(personInfo.pictureSmall).append(lineBreak)
                    .append("picture_middle: ").append(personInfo.pictureMiddle).append(lineBreak)
                    .append("picture_large: ").append(personInfo.pictureLarge).append(lineBreak)
                    .append("provice: ").append(personInfo.province).append(lineBreak)
                    .append("city: ").append(personInfo.city).append(lineBreak)
                    .append("country: ").append(personInfo.country).append(lineBreak);
        } else {
            builder.append("relationRet.persons is bad");
        }
        Log.d(TAG, "OnRelationNotify" + builder.toString());

        // 发送结果到结果展示界面
        //YSDKDemoApi.sShowView.showResult(builder.toString(), YSDKDemoApi.sLastFunction);
    }

    @Override
    public String OnCrashExtMessageNotify() {
        // 此处游戏补充crash时上报的额外信息
        Log.d(TAG, "OnCrashExtMessageNotify called");
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return "new Upload extra crashing message for bugly on " + time.format(nowTime);
    }

    @Override
    public byte[] OnCrashExtDataNotify() {
        return null;
    }

    @Override
    public void OnPayNotify(PayRet ret) {
        Log.d(TAG, ret.toString());
        if (PayRet.RET_SUCC == ret.ret) {
            //支付流程成功
            switch (ret.payState) {
                //支付成功
                case PayRet.PAYSTATE_PAYSUCC:
//                    YSDKDemoApi.sShowView.showResult(
//                            "用户支付成功，支付金额" + ret.realSaveNum + ";"
//                                    + "使用渠道：" + ret.payChannel + ";"
//                                    + "发货状态：" + ret.provideState + ";"
//                                    + "业务类型：" + ret.extendInfo + ";建议查询余额：" + ret.toString(),
//                            YSDKDemoApi.sLastFunction);
                    break;
                //取消支付
                case PayRet.PAYSTATE_PAYCANCEL:
                    //YSDKDemoApi.sShowView.showResult("用户取消支付：" + ret.toString(), YSDKDemoApi.sLastFunction);
                    break;
                //支付结果未知
                case PayRet.PAYSTATE_PAYUNKOWN:
                    //YSDKDemoApi.sShowView.showResult("用户支付结果未知，建议查询余额：" + ret.toString(),
                    //        YSDKDemoApi.sLastFunction);
                    break;
                //支付失败
                case PayRet.PAYSTATE_PAYERROR:
                    //YSDKDemoApi.sShowView.showResult("支付异常" + ret.toString(), YSDKDemoApi.sLastFunction);
                    break;
                default:
                    // do nothing
                    break;
            }
        } else {
            switch (ret.flag) {
                case eFlag.Login_TokenInvalid:
//                    YSDKDemoApi.sShowView.showResult("登录态过期，请重新登录：" + ret.toString(),
//                            YSDKDemoApi.sLastFunction);
//                    YSDKDemoApi.userLogout();
                    break;
                case eFlag.Pay_User_Cancle:
                    //用户取消支付
//                    YSDKDemoApi.sShowView.showResult("用户取消支付：" + ret.toString(),
//                            YSDKDemoApi.sLastFunction);
                    break;
                case eFlag.Pay_Param_Error:
//                    YSDKDemoApi.sShowView.showResult("支付失败，参数错误" + ret.toString(),
//                            YSDKDemoApi.sLastFunction);
                    break;
                case eFlag.Error:
                default:
                    //YSDKDemoApi.sShowView.showResult("支付异常" + ret.toString(), YSDKDemoApi.sLastFunction);
                    break;
            }
        }
    }

    @Override
    public void onTimeLimitNotify(AntiAddictRet ret) {
        if (AntiAddictRet.RET_SUCC == ret.ret) {
            // 防沉迷指令
            switch (ret.ruleFamily) {
                case AntiAddictRet.RULE_WORK_TIP:
                case AntiAddictRet.RULE_WORK_NO_PLAY:
                case AntiAddictRet.RULE_HOLIDAY_TIP:
                case AntiAddictRet.RULE_HOLIDAY_NO_PLAY:
                case AntiAddictRet.RULE_NIGHT_NO_PLAY:
                case AntiAddictRet.RULE_GUEST:
                default:
                    //YSDKDemoApi.executeInstruction(ret);
                    break;
            }
        }
    }

    @Override
    public void onLoginLimitNotify(AntiAddictRet ret) {
        if (AntiAddictRet.RET_SUCC == ret.ret) {
            // 防沉迷指令
            switch (ret.ruleFamily) {
                case AntiAddictRet.RULE_WORK_TIP:
                case AntiAddictRet.RULE_WORK_NO_PLAY:
                case AntiAddictRet.RULE_HOLIDAY_TIP:
                case AntiAddictRet.RULE_HOLIDAY_NO_PLAY:
                case AntiAddictRet.RULE_NIGHT_NO_PLAY:
                case AntiAddictRet.RULE_GUEST:
                default:
                    //YSDKDemoApi.executeInstruction(ret);
                    break;
            }

        }
    }

    @Override
    public void onWindowClose() {
        //YSDKDemoApi.sShowView.showToastTips("请重新登录游戏");
    }
}

