import { NativeModules, Platform } from 'react-native';
import type { GooglePayConfig } from './models';
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

export function startGooglePay(config: GooglePayConfig): Promise<string> {
  return new Promise(async (resolve, reject) => {
    try {
      const result = await TapGooglePayRn.startGooglePay(config);
      resolve(result);
    } catch (e) {
      let error = e as { message: string };
      if (error.message) {
        reject(error.message);
      }
    }
  });
}
