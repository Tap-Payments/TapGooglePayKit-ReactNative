import type {
  AllowedCardNetworks,
  AllowedMethods,
  SdkMode,
  TapCurrencyCode,
} from './enums';

export type GooglePayConfig = {
  secretKey: string;
  bundleID: string;
  countryCode: string;
  transactionCurrency: TapCurrencyCode;
  allowedCardNetworks: AllowedCardNetworks[];
  allowedCardAuthMethods: AllowedMethods;
  environmentMode: SdkMode;
  gatewayId: string;
  gatewayMerchantID: string;
  amount: number;
};
