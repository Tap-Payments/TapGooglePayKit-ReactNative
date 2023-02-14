export enum AllowedCardNetworks {
  VISA = 'VISA',
  AMEX = 'AMEX',
  JCB = 'JCB',
  MADA = 'MADA',
}

export enum LocaleIdentifier {
  ar,
  en,
}

export enum AllowedMethods {
  panOnly = 'PAN_ONLY',
  cryptogram3Ds = 'CRYPTOGRAM_3DS',
  all = 'ALL',
}

export enum SdkMode {
  production,
  sandbox,
}

export enum SDKCallMode {
  getGooglePayToken,
  getTapToken,
}
