export type GoogleToken = {
  signature: string;
  intermediateSigningKey: {
    signedKey: string;
    signatures: string[];
  };
  protocolVersion: string;
  signedMessage: string;
};
