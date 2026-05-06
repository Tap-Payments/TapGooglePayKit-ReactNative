import * as React from 'react';
import {useCallback} from 'react';

import {StyleSheet, View, Text, ScrollView} from 'react-native';
import {
  AllowedMethods,
  SdkMode,
  getGooglePayToken,
  TapCurrencyCode,
  GoogleToken,
  GooglePayButtonType,
  GooglePay,
  GooglePayButtonTheme,
  AllowedCardNetworks,
} from '@tap-payments/google-pay-rn';

export default function App() {
  const [result, setResult] = React.useState<string | undefined>();

  const init = useCallback(async () => {
    try {
      const config = {
        publicKey: 'pk_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
        bundleID: 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
        countryCode: 'AE',
        transactionCurrency: TapCurrencyCode.AED,
        allowedCardNetworks: [
          AllowedCardNetworks.AMEX,
          AllowedCardNetworks.VISA,
          AllowedCardNetworks.JCB,
          AllowedCardNetworks.MADA,
          AllowedCardNetworks.MASTERCARD,
        ],
        allowedCardAuthMethods: AllowedMethods.all,
        environmentMode: SdkMode.sandbox,
        gatewayId: 'xxxxxxxxxxxx',
        gatewayMerchantID: 'xxxxxxxxxxxxxx',
        amount: 23,
      };
      const res: GoogleToken = await getGooglePayToken(config);

      // let res: TapToken = await getTapToken(config);
      setResult(JSON.stringify(res));
    } catch (e) {
      setResult(e as string);
    }
  }, []);
  return (
    <ScrollView
      contentContainerStyle={styles.container}
      showsVerticalScrollIndicator={false}>
      <View style={styles.resultContainer}>
        <Text>Result: {result}</Text>
      </View>

      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.buyWithGooglePay}
      />
      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.donateWithGooglePay}
      />
      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.checkoutWithGooglePay}
      />
      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.orderWithGooglePay}
      />
      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.normalGooglePay}
      />
      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.payWithGooglePay}
      />
      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.bookWithGooglePay}
      />
      <GooglePay
        style={styles.button}
        onPress={init}
        cornerRadius={50}
        theme={GooglePayButtonTheme.DARK}
        type={GooglePayButtonType.subscribeWithGooglePay}
      />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  resultContainer: {
    paddingVertical: 12,
    alignItems: 'center',
  },
  container: {
    flexGrow: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
    gap: 12,
  },
  button: {
    alignSelf: 'stretch',
    minHeight: 60,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
