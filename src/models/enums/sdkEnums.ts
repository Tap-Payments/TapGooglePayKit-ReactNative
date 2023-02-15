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

export enum GooglePayButtonType {
  buyWithGooglePay = 'BUY_WITH_GOOGLE_PAY',
  donateWithGooglePay = 'DONATE_WITH_GOOGLE_PAY',
  normalGooglePay = 'NORMAL_GOOGLE_PAY',
  payWithGooglePay = 'PAY_WITH_GOOGLE_PAY',
  subscribeWithGooglePay = 'SUBSCRIBE_WITH_GOOGLE_PAY',
  checkoutWithGooglePay = 'CHECKOUT_WITH_GOOGLE_PAY',
  orderWithGooglePay = 'ORDER_WITH_GOOGLE_PAY',
  bookWithGooglePay = 'BOOK_WITH_GOOGLE_PAY',
}
