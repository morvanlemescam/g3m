package org.glob3.mobile.generated; 
//
//  GEOSymbol.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 3/25/13.
//
//

//
//  GEOSymbol.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 3/25/13.
//
//



//class G3MRenderContext;
//class GEOSymbolizer;
//class MeshRenderer;
//class ShapesRenderer;
//class MarksRenderer;
//class GEOTileRasterizer;


public abstract class GEOSymbol extends Disposable
{

  public void dispose()
  {
    JAVA_POST_DISPOSE
  }

  public abstract boolean symbolize(G3MRenderContext rc, GEOSymbolizer symbolizer, MeshRenderer meshRenderer, ShapesRenderer shapesRenderer, MarksRenderer marksRenderer, GEOTileRasterizer geoTileRasterizer);

}