package com.tapgooglepayrn;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;

import com.facebook.react.uimanager.PixelUtil;
import com.google.android.gms.wallet.button.ButtonConstants;
import com.google.android.gms.wallet.button.ButtonOptions;
import com.google.android.gms.wallet.button.PayButton;

public class GooglePayButtonView extends FrameLayout {

  public String buttonType;
  public String buttonTheme;
  public int    cornerRadius = 4;
  public String allowedPaymentMethods;

  private PayButton payButton;

  private final ViewTreeObserver.OnGlobalLayoutListener layoutListener = this::requestLayout;

  public GooglePayButtonView(@NonNull Context context) {
    super(context);
    init();
  }

  public GooglePayButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    setLayoutParams(new FrameLayout.LayoutParams(
      FrameLayout.LayoutParams.MATCH_PARENT,
      FrameLayout.LayoutParams.MATCH_PARENT
    ));
  }

  public void addButton() {
    if (buttonType == null) return;
    removeView(payButton);
    payButton = buildPayButton();
    if (payButton != null) {
      addView(payButton);
      getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }
  }

  private @Nullable PayButton buildPayButton() {
    // PayButton inflates AppCompat widgets (RoundedImageViewWithBorder, AppCompatTextView)
    // via Google's Dynamite module loader. That loader requires an explicit AppCompat
    // theme on the context — the raw Activity/ReactContext is not enough on all setups.
    Context payContext = new ContextThemeWrapper(
      getContext(),
      androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar
    );
    PayButton btn = new PayButton(payContext);
    int radiusPx = (int) PixelUtil.toPixelFromDIP(cornerRadius);
    try {
      String methods = allowedPaymentMethods != null
        ? allowedPaymentMethods
        : GoogleButtonViewManager.DEFAULT_ALLOWED_PAYMENT_METHODS;
      btn.initialize(
        ButtonOptions.newBuilder()
          .setButtonType(resolveType(buttonType))
          .setButtonTheme(resolveTheme(buttonTheme))
          .setCornerRadius(radiusPx)
          .setAllowedPaymentMethods(methods)
          .build()
      );
    } catch (Exception e) {
      Log.e("GooglePayButton", "initialize failed: " + e.getMessage());
      return null;
    }
    // Forward native PayButton clicks up to whatever JS-side wrapper holds the
    // onPress handler (Pressable). Without this, the JS overlay had to capture
    // taps itself, which prevented PayButton from receiving the touch/lifecycle
    // signals it needs to render the recently-used-card variant on first attach.
    btn.setOnClickListener(v -> {
      ViewParent parent = getParent();
      if (parent instanceof View) {
        ((View) parent).performClick();
      }
    });
    return btn;
  }

  private int resolveType(@Nullable String t) {
    if (t == null) return ButtonConstants.ButtonType.BUY;
    switch (t) {
      case "BUY_WITH_GOOGLE_PAY":       return ButtonConstants.ButtonType.BUY;
      case "PAY_WITH_GOOGLE_PAY":       return ButtonConstants.ButtonType.PAY;
      case "SUBSCRIBE_WITH_GOOGLE_PAY": return ButtonConstants.ButtonType.SUBSCRIBE;
      case "CHECKOUT_WITH_GOOGLE_PAY":  return ButtonConstants.ButtonType.CHECKOUT;
      case "ORDER_WITH_GOOGLE_PAY":     return ButtonConstants.ButtonType.ORDER;
      case "BOOK_WITH_GOOGLE_PAY":      return ButtonConstants.ButtonType.BOOK;
      case "DONATE_WITH_GOOGLE_PAY":    return ButtonConstants.ButtonType.DONATE;
      case "NORMAL_GOOGLE_PAY":         return ButtonConstants.ButtonType.PLAIN;
      default:                          return ButtonConstants.ButtonType.BUY;
    }
  }

  private int resolveTheme(@Nullable String t) {
    if ("DARK".equalsIgnoreCase(t)) return ButtonConstants.ButtonTheme.DARK;
    return ButtonConstants.ButtonTheme.LIGHT;
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
  }

  // React Native does not always re-measure/re-layout native views on its own.
  // This pattern (from google-pay/react-native-make-payment) forces a correct
  // layout pass whenever React Native triggers requestLayout.
  @Override
  public void requestLayout() {
    super.requestLayout();
    post(mLayoutRunnable);
  }

  private final Runnable mLayoutRunnable = () -> {
    measure(
      MeasureSpec.makeMeasureSpec(getWidth(),  MeasureSpec.EXACTLY),
      MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY)
    );
    layout(getLeft(), getTop(), getRight(), getBottom());
  };
}
