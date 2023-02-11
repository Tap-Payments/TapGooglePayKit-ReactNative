import * as React from 'react';
import { useCallback } from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import {
  AllowedCardNetworks,
  AllowedMethods,
  SdkMode,
  getGooglePayToken,
  TapCurrencyCode,
} from 'tap-google-pay-rn';

export default function App() {
  const [result, setResult] = React.useState<string | undefined>();

  const init = useCallback(async () => {
    try {
      const config = {
        secretKey: 'sk_test_cvSHaplrPNkJO7dhoUxDYjqA',
        bundleID: 'company.tap.goSellSDKExamplee',
        countryCode: 'US',
        transactionCurrency: TapCurrencyCode.USD,
        allowedCardNetworks: [AllowedCardNetworks.VISA],
        allowedCardAuthMethods: AllowedMethods.all,
        environmentMode: SdkMode.sandbox,
        gatewayId: 'tappayments',
        gatewayMerchantID: '1124340',
        amount: 23,
      };
      const res = await getGooglePayToken(config);

      // let res = await getGooglePayToken(config);
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
      <TouchableOpacity style={styles.button} onPress={init}>
        <Text style={styles.black}>{'init'}</Text>
      </TouchableOpacity>
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
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  black: { color: 'black' },
  button: {
    alignSelf: 'stretch',
    height: 50,
    backgroundColor: 'white',
    justifyContent: 'center',
    alignItems: 'center',
  },
});
