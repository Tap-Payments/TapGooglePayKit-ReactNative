package com.tapgooglepayrn;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

import java.util.HashMap;
import java.util.Map;

@ReactModule(name = TapGooglePayRnModule.NAME)
public class TapGooglePayRnModule extends ReactContextBaseJavaModule implements SDKCallBack {

//  private Callback jsCallback;
  private TapGoogleSDKDelegate delegate;
  private Application application;
  private final ReactApplicationContext reactContext;
  private Activity _activity;

  private Promise promise;
  public static final String NAME = "TapGooglePayRn";

  public TapGooglePayRnModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.application = (Application) reactContext.getApplicationContext();
    this._activity = reactContext.getCurrentActivity();
    setup(application, _activity, reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void startGooglePay(ReadableMap readableMap, Promise promise) {
    Activity currentActivity = getCurrentActivity();
    HashMap<String, Object> args = readableMap.toHashMap();
    this.promise = promise;
    delegate.start(currentActivity, this, args);
  }


  private void setup(

    final Application application,
    final Activity activity, final ReactApplicationContext reactContext) {
    this.application = application;
    this.delegate = constructDelegate(activity, reactContext);
  }

  /**
   * construct delegate
   */

  private final TapGoogleSDKDelegate constructDelegate(final Activity setupActivity, ReactApplicationContext reactContext) {
    System.out.println("setupActivity = " + setupActivity + "delegate>>>" + delegate);
    return new TapGoogleSDKDelegate(setupActivity,reactContext);
  }

  @Override
  public void onSuccess(WritableMap result) {
    this.promise.resolve(result);
  }

  @Override
  public void onFailure(String error) {
    System.out.println(error);
    this.promise.reject(error);
  }
}
