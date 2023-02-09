import * as React from 'react';
import { useCallback } from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import {
  AllowedCardNetworks,
  AllowedMethods,
  SdkMode,
  startGooglePay,
  TapCurrencyCode,
} from 'tap-google-pay-rn';

export default function App() {
  const [result, setResult] = React.useState<string | undefined>();

  React.useEffect(() => {}, []);

  const init = useCallback(async () => {
    try {
      let res = await startGooglePay({
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
      });

      setResult(JSON.stringify(res));
    } catch (e) {
      setResult(e as string);
    }
  }, []);

  return (
    <View style={styles.container}>
      <View style={{ flex: 1, justifyContent: 'center' }}>
        <Text>Result: {result}</Text>
      </View>
      <TouchableOpacity
        style={{
          alignSelf: 'stretch',
          height: 50,
          backgroundColor: 'white',
          justifyContent: 'center',
          alignItems: 'center',
        }}
        onPress={init}
      >
        <Text style={{ color: 'black' }}>{'init'}</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
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
});
