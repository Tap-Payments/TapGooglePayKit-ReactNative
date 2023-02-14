# @tap-payments/google-pay-rn (Only Android)
A standalone react native kit for handling Google Pay™

## Installation

```sh
npm install @tap-payments/google-pay-rn
```

<a name="additional_config_googlepay"></a>
# Additional Android Configuration
To use GooglePay in your app , you will be required to do additional configuration as follows:

1. In build.gradle file add

```kotlin
implementation "com.google.android.gms:play-services-wallet:18.1.3"
```

2. Ensure your min sdk is 24
```kotlin
minSdk 24
targetSdk 32
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
export enum AllowedMethods {
  panOnly = 'PAN_ONLY',
  cryptogram3Ds = 'CRYPTOGRAM_3DS',
  all = 'ALL',
}
```

```ts
export enum SdkMode {
  production,
  sandbox,
}
```

```ts
export enum SDKCallMode {
  getGooglePayToken,
  getTapToken,
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

  try {
      const config = {
        secretKey: 'sk_test_xxxxxxxxxxxxxxxxxx',
        bundleID: 'com.xxx.xxxxxxx',
        countryCode: 'US',
        transactionCurrency: TapCurrencyCode.USD,
        allowedCardNetworks: [AllowedCardNetworks.VISA],
        allowedCardAuthMethods: AllowedMethods.all,
        environmentMode: SdkMode.sandbox,
        gatewayId: 'xxxxx',
        gatewayMerchantID: '122xxxxx',
        amount: 23,
      };
      // get google Pay token
      const result: GoogleToken = await getGooglePayToken(config);

      // get tap token
      const result: TapToken = await getTapToken(config);

      setResult(JSON.stringify(res));
    } catch (e) {
      setResult(e as string);
    }
// ...

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
