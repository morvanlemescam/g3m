//
//  G3MBuilder_iOS.hpp
//  G3MiOSSDK
//
//  Created by Mari Luz Mateo on 22/11/12.
//
//

#ifndef G3MBuilder_iOS_hpp
#define G3MBuilder_iOS_hpp

#include "IG3MBuilder.hpp"
#import "G3MWidget_iOS.h"

class G3MBuilder_iOS : public IG3MBuilder {

private:
    G3MWidget_iOS* _nativeWidget;

public:
    G3MBuilder_iOS(G3MWidget_iOS* nativeWidget);
    void initializeWidget();

};

#endif
