package cordova.plugin.ysdk;

import android.content.Intent;
import android.util.Log;

import com.tencent.ysdk.api.YSDKApi;
import com.tencent.ysdk.framework.common.BaseRet;
import com.tencent.ysdk.module.antiaddiction.listener.AntiAddictListener;
import com.tencent.ysdk.module.antiaddiction.model.AntiAddictRet;
import com.tencent.ysdk.module.user.UserListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.ysdk.framework.common.ePlatform;
import com.tencent.ysdk.module.user.UserLoginRet;
import com.tencent.ysdk.module.user.UserRelationRet;
import com.tencent.ysdk.module.user.WakeupRet;

public class TencentYSDKPlugin extends CordovaPlugin {
  private static final String TAG = "LoginActivity";

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    try {
      YSDKApi.init(false);
//      YSDKApi.showDebugIcon(cordova.getActivity());
    } catch (Exception e) {
      Log.d("ysdk error", e.toString());
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    YSDKApi.onActivityResult(requestCode, resultCode, intent);
  }

  @Override
  public boolean execute(
          String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    JSONObject jsonObject = args.getJSONObject(0);
    // 展示开屏广告
    if ("doLogin".equals(action)) {
      Log.d("ysdk login", "login");
      YSDKApi.login(ePlatform.Guest);
      return true;
    }
    if ("initYSDK".equals(action)) {
      Log.d("ysdk init", "init");
      YSDKApi.setUserListener(new UserListener() {
        @Override
        public void OnLoginNotify(UserLoginRet userLoginRet) {
          Log.d("ysdk", "onlogin not ify" + userLoginRet.ret);
//          callbackContext.success();
          JSONObject param = new JSONObject();
          try {
            param.put("ret", userLoginRet.ret);
            param.put("flag", userLoginRet.flag);
          } catch (JSONException e) {
            e.printStackTrace();
          }
          SendCallback(callbackContext,"onLogin", param);
        }

        @Override
        public void OnWakeupNotify(WakeupRet wakeupRet) {
          SendCallback(callbackContext,"onWakeup",new JSONObject());
        }

        @Override
        public void OnRelationNotify(UserRelationRet userRelationRet) {
          SendCallback(callbackContext,"onRelation",new JSONObject());
        }
      });
      YSDKApi.setAntiAddictListener(new AntiAddictListener() {
        @Override
        public void onLoginLimitNotify(AntiAddictRet antiAddictRet) {
          SendCallback(callbackContext,"onLoginLimit",new JSONObject());
        }

        @Override
        public void onTimeLimitNotify(AntiAddictRet antiAddictRet) {
          SendCallback(callbackContext,"onTimeLimit",new JSONObject());
        }
      });
      return true;
    }
    return false;
  }

  private void SendCallback(CallbackContext callbackContext, String event, JSONObject param) {
    try {
      param.put("type", event);
    }catch (Exception e) {
    }
    PluginResult result = new PluginResult(PluginResult.Status.OK, param);
    result.setKeepCallback(true);
    callbackContext.sendPluginResult(result);
  }

}