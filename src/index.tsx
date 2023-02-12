import { NativeModules, Platform } from 'react-native';
import { GooglePayConfig, GoogleToken, SDKCallMode, TapToken } from './models';

export * from './models';
const LINKING_ERROR =
  `The package 'tap-google-pay-rn' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const TapGooglePayRn = NativeModules.TapGooglePayRn
  ? NativeModules.TapGooglePayRn
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function getGooglePayToken(config: GooglePayConfig) {
  return new Promise<GoogleToken>(async (resolve, reject) => {
    try {
      if (Platform.OS === 'ios') {
        reject('Not available for iOS');
      }
      const result = await TapGooglePayRn.startGooglePay({
        ...config,
        type: SDKCallMode.getGooglePayToken,
      });
      const res = result as { googlePayToken: string };
      const googleToken = JSON.parse(res.googlePayToken) as GoogleToken;
      resolve(googleToken);
    } catch (e) {
      let error = e as { message: string };
      if (error.message) {
        reject(error.message);
      }
    }
  });
}

export function getTapToken(config: GooglePayConfig) {
  return new Promise<TapToken>(async (resolve, reject) => {
    try {
      if (Platform.OS === 'ios') {
        reject('Not available for iOS');
      }
      const result = await TapGooglePayRn.startGooglePay({
        ...config,
        type: SDKCallMode.getTapToken,
      });
      resolve(result as TapToken);
    } catch (e) {
      let error = e as { message: string };
      if (error.message) {
        reject(error.message);
      }
    }
  });
}
