
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNTapGooglePayRnSpec.h"

@interface TapGooglePayRn : NSObject <NativeTapGooglePayRnSpec>
#else
#import <React/RCTBridgeModule.h>

@interface TapGooglePayRn : NSObject <RCTBridgeModule>
#endif

@end
