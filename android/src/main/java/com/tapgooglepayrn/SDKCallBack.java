package com.tapgooglepayrn;

import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.util.Map;

public interface SDKCallBack {
  public void onSuccess(WritableMap result);
  public void onFailure(String error);
}
