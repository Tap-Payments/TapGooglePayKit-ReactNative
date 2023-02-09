package com.tapgooglepayrn;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import company.tap.google.pay.internal.api.responses.Token;
import company.tap.google.pay.open.DataConfiguration;
import company.tap.google.pay.open.GooglePayButton;
import company.tap.google.pay.open.SDKDelegate;
import company.tap.google.pay.open.enums.AllowedMethods;
import company.tap.google.pay.open.enums.SDKMode;

public class TapGoogleSDKDelegate implements SDKDelegate {

  private Activity activity;
  private DataConfiguration dataConfiguration;
  private TapGooglePayRnModule callback;
  private GooglePayButton googlePayButton;

  public TapGoogleSDKDelegate(Activity _activity, ReactApplicationContext reactContext) {
    this.activity = _activity;
    this.dataConfiguration = DataConfiguration.INSTANCE;
    this.googlePayButton = new GooglePayButton(reactContext);
  }

  @Override
  public void onFailed(@NonNull String s) {
    this.callback.onFailure(s);
  }

  @Override
  public void onGooglePayToken(@NonNull String s) {
    WritableMap resultData = new WritableNativeMap();
    resultData.putString("googlePayToken", s);
    this.callback.onSuccess(resultData);
  }

  @Override
  public void onTapToken(@NonNull Token token) {
    WritableMap resultData = new WritableNativeMap();
    resultData.putString("id", token.getId());
    resultData.putString("object", token.getObject());

    if (token.getCard() != null) {
      resultData.putString("card", token.getCard().getId());
      resultData.putString("funding", token.getCard().getFunding());
      resultData.putString("fingerprint", token.getCard().getFingerprint());
      resultData.putString("brand", token.getCard().getBrand());
      resultData.putInt("expirationMonth", token.getCard().getExpirationMonth());
      resultData.putInt("expirationYear", token.getCard().getExpirationYear());
      resultData.putString("lastFour", token.getCard().getFirstSix());
      resultData.putString("firstSix", token.getCard().getLastFour());
    }

    if (token.getType() != null) {
      resultData.putString("type", token.getType().name());
    }

    resultData.putDouble("created", token.getCreated());
    resultData.putString("clientIp", token.getClient_ip());
    resultData.putBoolean("isLiveMode", token.getLivemode());
    resultData.putBoolean("isUsed", token.getUsed());
    resultData.putString("currency", token.getCurrency());
    resultData.putString("name", token.getName());
    this.callback.onSuccess(resultData);
  }

  public void start(Activity activity1, TapGooglePayRnModule callback, HashMap<String, Object> args) {
    this.callback = callback;
    try {
      String secretKey = (String) Objects.requireNonNull(args.get("secretKey"));
      String bundleID = (String) Objects.requireNonNull(args.get("bundleID"));
      String countryCode = (String) Objects.requireNonNull(args.get("countryCode"));
      String transactionCurrency = (String) Objects.requireNonNull(args.get("transactionCurrency"));
      List<String> allowedCardNetworks = (List<String>) Objects.requireNonNull(args.get("allowedCardNetworks"));
      String allowedCardAuthMethodsString = (String) Objects.requireNonNull(args.get("allowedCardAuthMethods"));
      double environmentModeDouble = (double) Objects.requireNonNull(args.get("environmentMode"));
      String gatewayId = (String) Objects.requireNonNull(args.get("gatewayId"));
      String gatewayMerchantID = (String) Objects.requireNonNull(args.get("gatewayMerchantID"));
      double amount = (double) Objects.requireNonNull(args.get("amount"));
      SDKMode environmentMode = getSdkMode((int) environmentModeDouble);

      AllowedMethods allowedCardAuthMethods = getAllowedMethods(allowedCardAuthMethodsString);
      DataConfiguration.INSTANCE.initSDK(activity1, secretKey, bundleID);
      DataConfiguration.INSTANCE.addSDKDelegate(this);
      DataConfiguration.INSTANCE.setCountryCode(countryCode);
      DataConfiguration.INSTANCE.setTransactionCurrency(transactionCurrency);
      DataConfiguration.INSTANCE.setAllowedCardNetworks(allowedCardNetworks);
      DataConfiguration.INSTANCE.setAllowedCardAuthMethods(allowedCardAuthMethods);
      DataConfiguration.INSTANCE.setEnvironmentMode(environmentMode); //**Required SDK MODE**/
      DataConfiguration.INSTANCE.setGatewayId(gatewayId); //**Required GATEWAY ID**/
      DataConfiguration.INSTANCE.setGatewayMerchantID(gatewayMerchantID); //**Required GATEWAY Merchant ID**/
      DataConfiguration.INSTANCE.setAmount(BigDecimal.valueOf(amount)); //**Required Amount**/
      DataConfiguration.INSTANCE.getTapToken(activity1, googlePayButton);
      
    } catch (Exception e) {
      this.callback.onFailure("Missing params");
    }

  }

  @NonNull
  private AllowedMethods getAllowedMethods(String allowedCardAuthMethodsString) {
    AllowedMethods allowedCardAuthMethods = AllowedMethods.ALL;


    switch (allowedCardAuthMethodsString) {
      case "ALL":
        allowedCardAuthMethods = AllowedMethods.ALL;
      case "CRYPTOGRAM_3DS":
        allowedCardAuthMethods = AllowedMethods.CRYPTOGRAM_3DS;
      case "PAN_ONLY":
        allowedCardAuthMethods = AllowedMethods.PAN_ONLY;
    }
    return allowedCardAuthMethods;
  }

  @NonNull
  private SDKMode getSdkMode(int environmentModeDouble) {
    SDKMode environmentMode = SDKMode.ENVIRONMENT_TEST;
    switch (environmentModeDouble) {
      case 0:
        environmentMode = SDKMode.ENVIRONMENT_PRODUCTION;
      case 1:
        environmentMode = SDKMode.ENVIRONMENT_TEST;
    }
    return environmentMode;
  }
}
