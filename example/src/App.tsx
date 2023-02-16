import * as React from 'react';
import { useCallback } from 'react';

import { StyleSheet, View, Text } from 'react-native';
import {
  AllowedCardNetworks,
  AllowedMethods,
  SdkMode,
  getGooglePayToken,
  TapCurrencyCode,
  GoogleToken,
  GooglePay,
  GooglePayButtonType,
} from '@tap-payments/google-pay-rn';

export default function App() {
  const [result, setResult] = React.useState<string | undefined>();

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
      setResult(JSON.stringify(res));
    } catch (e) {
      setResult(e as string);
    }
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.resultContainer}>
        <Text>Result: {result}</Text>
      </View>
      <GooglePay
        style={styles.button}
        onPress={init}
        type={GooglePayButtonType.bookWithGooglePay}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  resultContainer: {
    flex: 1,
    justifyContent: 'center',
  },
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  button: {
    alignSelf: 'stretch',
    height: 50,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
