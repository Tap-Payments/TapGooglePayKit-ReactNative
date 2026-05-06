import React from 'react';
import {
  NativeModules,
  Platform,
  Pressable,
  requireNativeComponent,
  UIManager,
  View,
  ViewStyle,
} from 'react-native';
import {
  GooglePayButtonTheme,
  GooglePayButtonType,
  GooglePayConfig,
  GoogleToken,
  SDKCallMode,
  TapToken,
} from './models';
export * from './models';

const LINKING_ERROR =
  `The package '@tap-payments/google-pay-rn' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

type GooglePayButtonProps = {
  type: GooglePayButtonType;
  theme?: GooglePayButtonTheme;
  cornerRadius?: number;
  style: ViewStyle;
};

const ComponentName = 'GoogleButtonView';

const GooglePayButton =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<GooglePayButtonProps>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };

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
export function GooglePay({
  type,
  theme,
  cornerRadius,
  onPress,
  style,
}: {
  type: GooglePayButtonType;
  theme?: GooglePayButtonTheme;
  cornerRadius?: number;
  onPress: () => void;
  style: ViewStyle;
}) {
  console.log('🚀 ~ GooglePay ~ type:', type);

  if (Platform.OS === 'ios') {
    return <View />;
  }

  return (
    <Pressable style={style} onPress={onPress}>
      <GooglePayButton
        type={type}
        theme={theme}
        cornerRadius={cornerRadius}
        style={style}
      />
    </Pressable>
  );
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

