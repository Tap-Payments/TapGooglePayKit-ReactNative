package com.tapgooglepayrn;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleButtonViewManager extends SimpleViewManager<GooglePayButtonView> {
  public static final String REACT_CLASS = "GoogleButtonView";

  // Shared default payment methods JSON (AMEX, DISCOVER, MASTERCARD, VISA).
  // Exposed package-level so GooglePayButtonView can reference it.
  static final String DEFAULT_ALLOWED_PAYMENT_METHODS;

  static {
    String json = "[]";
    try {
      json = new JSONArray()
        .put(new JSONObject()
          .put("type", "CARD")
          .put("parameters", new JSONObject()
            .put("allowedAuthMethods", new JSONArray()
              .put("PAN_ONLY")
              .put("CRYPTOGRAM_3DS"))
            .put("allowedCardNetworks", new JSONArray()
              .put("AMEX")
              .put("DISCOVER")
              .put("MASTERCARD")
              .put("VISA"))))
        .toString();
    } catch (Exception ignored) {}
    DEFAULT_ALLOWED_PAYMENT_METHODS = json;
  }

  @Override
  @NonNull
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  @NonNull
  public GooglePayButtonView createViewInstance(@NonNull ThemedReactContext reactContext) {
    // PayButton uses AppCompat widgets internally — it must receive the Activity
    // context (which carries the AppCompat theme) rather than ThemedReactContext.
    Activity activity = reactContext.getCurrentActivity();
    Context context = activity != null ? activity : reactContext;
    return new GooglePayButtonView(context);
  }

  // Called by React Native AFTER all props for the current update batch have been
  // set. This is the correct place to initialize PayButton — guaranteed to have
  // all props available, works on both old and new (Fabric) architectures.
  @Override
  public void onAfterUpdateTransaction(@NonNull GooglePayButtonView view) {
    super.onAfterUpdateTransaction(view);
    view.addButton();
  }

  @ReactProp(name = "type")
  public void setType(GooglePayButtonView view, @Nullable String type) {
    view.buttonType = type;
  }

  @ReactProp(name = "theme")
  public void setTheme(GooglePayButtonView view, @Nullable String theme) {
    view.buttonTheme = theme;
  }

  @ReactProp(name = "cornerRadius", defaultInt = 4)
  public void setCornerRadius(GooglePayButtonView view, int cornerRadius) {
    view.cornerRadius = cornerRadius;
  }
}
