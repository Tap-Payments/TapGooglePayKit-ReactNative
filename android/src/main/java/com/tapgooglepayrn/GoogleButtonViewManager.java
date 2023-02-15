package com.tapgooglepayrn;

  import android.graphics.Color;
  import android.view.LayoutInflater;
  import android.view.View;

  import androidx.annotation.NonNull;

  import com.facebook.react.uimanager.SimpleViewManager;
  import com.facebook.react.uimanager.ThemedReactContext;
  import com.facebook.react.uimanager.annotations.ReactProp;

  import company.tap.google.pay.open.GooglePayButton;
  import company.tap.google.pay.open.enums.GooglePayButtonType;
  import company.tap.google.pay.open.enums.SDKMode;

public class GoogleButtonViewManager extends SimpleViewManager<View> {
  public static final String REACT_CLASS = "GoogleButtonView";

  @Override
  @NonNull
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  @NonNull
  public View createViewInstance(ThemedReactContext reactContext) {
    View view = LayoutInflater.from(reactContext).inflate(
      R.layout.layout, null);
    return  view;
  }

  @NonNull
  private GooglePayButtonType getGooglePayType(String typeValue) {
    GooglePayButtonType type = GooglePayButtonType.NORMAL_GOOGLE_PAY;
    switch (typeValue) {
      case "BUY_WITH_GOOGLE_PAY":
        type = GooglePayButtonType.BUY_WITH_GOOGLE_PAY;
        break;
      case "NORMAL_GOOGLE_PAY":
        type = GooglePayButtonType.NORMAL_GOOGLE_PAY;
        break;
      case "PAY_WITH_GOOGLE_PAY":
        type = GooglePayButtonType.PAY_WITH_GOOGLE_PAY;
        break;
      case "SUBSCRIBE_WITH_GOOGLE_PAY":
        type = GooglePayButtonType.SUBSCRIBE_WITH_GOOGLE_PAY;
        break;
      case "CHECKOUT_WITH_GOOGLE_PAY":
        type = GooglePayButtonType.CHECKOUT_WITH_GOOGLE_PAY;
        break;
      case "ORDER_WITH_GOOGLE_PAY":
        type = GooglePayButtonType.ORDER_WITH_GOOGLE_PAY;
        break;
      case "BOOK_WITH_GOOGLE_PAY":
        type = GooglePayButtonType.BOOK_WITH_GOOGLE_PAY;
        break;
    }
    System.out.println(typeValue);
    return type;
  }

  @ReactProp(name = "type")
  public void setType(View view, String type) {
    GooglePayButton googlePayView = view.findViewById(R.id.googlePayView);
    googlePayView.setGooglePayButtonType(getGooglePayType(type));
  }


}
