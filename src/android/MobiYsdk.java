package cordova.plugin.ysdk;

import android.content.Intent;
import android.util.Log;

import com.tencent.ysdk.api.YSDKApi;
import com.tencent.ysdk.framework.common.BaseRet;
import com.tencent.ysdk.module.user.UserListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import com.tencent.ysdk.framework.common.ePlatform;
import com.tencent.ysdk.module.user.UserLoginRet;

public class MobiYsdk extends CordovaPlugin {
  private static final String TAG = "LoginActivity";

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    YSDKApi.init();
    //YSDKApi.setUserListener(new UserCallback());
    //YSDKApi.setUserListener(new UserListener());
    YSDKApi.login(ePlatform.Guest);
  }
}