# @tap-payments/google-pay-rn (Only Android)

A standalone react native kit for handling Google Pay™

## Installation

```sh
npm install @tap-payments/google-pay-rn
```

# Additional Android Configuration

To use GooglePay in your app , you will be required to do additional configuration as follows:

1. In build.gradle file add

```kotlin
implementation "com.google.android.gms:play-services-wallet:18.1.3"
```

2. Ensure your min sdk is 21

```kotlin
  minSdkVersion = 21
  compileSdkVersion = 33
  targetSdkVersion = 32
```

3. In Manifest file , Inside the <application tag do the below:

```kotlin
  <meta-data
    android:name="com.google.android.gsm.wallet.api.enabled"
    android:value="true"
  />
```

### Configure SDK Example

```ts
enum AllowedMethods {
  panOnly = 'PAN_ONLY',
  cryptogram3Ds = 'CRYPTOGRAM_3DS',
  all = 'ALL',
}
```

```ts
enum SdkMode {
  production,
  sandbox,
}
```

```ts
enum AllowedCardNetworks {
  VISA = 'VISA',
  AMEX = 'AMEX',
  JCB = 'JCB',
  MADA = 'MADA',
}
```

```ts
GooglePayConfig = {
  secretKey: string; // Tap secret key
  bundleID: string;  // App bundleID
  countryCode: string;  // countryCode
  transactionCurrency: TapCurrencyCode; // Enum TapCurrencyCode
  allowedCardNetworks: AllowedCardNetworks[]; // Array of AllowedCardNetworks enum 
  allowedCardAuthMethods: AllowedMethods; // AllowedMethods
  environmentMode: SdkMode; // SdkMode enum
  gatewayId: string;
  gatewayMerchantID: string;
  amount: number;
};

 enum GooglePayButtonType {
  buyWithGooglePay = 'BUY_WITH_GOOGLE_PAY',
  donateWithGooglePay = 'DONATE_WITH_GOOGLE_PAY',
  normalGooglePay = 'NORMAL_GOOGLE_PAY',
  payWithGooglePay = 'PAY_WITH_GOOGLE_PAY',
  subscribeWithGooglePay = 'SUBSCRIBE_WITH_GOOGLE_PAY',
  checkoutWithGooglePay = 'CHECKOUT_WITH_GOOGLE_PAY',
  orderWithGooglePay = 'ORDER_WITH_GOOGLE_PAY',
  bookWithGooglePay = 'BOOK_WITH_GOOGLE_PAY',
}
```

## Usage

```ts
import {
  AllowedCardNetworks,
  AllowedMethods,
  SdkMode,
  getGooglePayToken,
  TapCurrencyCode,
  GoogleToken,
  getTapToken,
  TapToken,
} from '@tap-payments/google-pay-rn';

 const init = useCallback(async () => {
    try {
      const config = {
        secretKey: 'sk_test_xxxxxxxxxxxxxxxxxxx',
        bundleID: 'xxxx.xxxx.xxxxxxx',
        countryCode: 'US',
        transactionCurrency: TapCurrencyCode.USD,
        allowedCardNetworks: [AllowedCardNetworks.VISA],
        allowedCardAuthMethods: AllowedMethods.all,
        environmentMode: SdkMode.sandbox,
        gatewayId: 'xxxxxxx',
        gatewayMerchantID: 'xxxxxxx',
        amount: 23,
      };
      const res: GoogleToken = await getGooglePayToken(config);

      // let res: TapToken = await getTapToken(config);
      console.log("🚀", JSON.stringify(res))
    } catch (error) {
      console.log("🚀", JSON.stringify(error))
    }
  }, []);
  
```

```tsx
return(
      <GooglePay
        style={styles.button}
        onPress={init}
        type={GooglePayButtonType.bookWithGooglePay} // GooglePayButtonType
      />
    )
```

### SDK Return types

```ts
 type GoogleToken = {
  signature: string;
  intermediateSigningKey: {
    signedKey: string;
    signatures: string[];
  };
  protocolVersion: string;
  signedMessage: string;
};
```

```ts
 type TapToken = {
  name?: string;
  currency?: string;
  isUsed: boolean;
  isLiveMode: boolean;
  created: number;
  brand: string;
  funding: string;
  expirationYear: number;
  id: string;
  expirationMonth: number;
  card: string;
  firstSix: string;
  fingerprint: string;
  clientIp?: string;
  object: string;
  lastFour: string;
};

```
