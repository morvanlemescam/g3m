package org.glob3.mobile.generated; 
//
//  GEOGeometry.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 11/30/12.
//
//

//
//  GEOGeometry.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 11/30/12.
//
//



//class GEOSymbol;
//class GEOFeature;

public abstract class GEOGeometry extends GEOObject
{
  private GEOFeature _feature;

  protected abstract java.util.ArrayList<GEOSymbol> createSymbols(GEOSymbolizer symbolizer);

  public GEOGeometry()
  {
     _feature = null;

  }

  public void dispose()
  {
    JAVA_POST_DISPOSE
  }

  public final void setFeature(GEOFeature feature)
  {
    if (_feature != feature)
    {
      if (_feature != null)
         _feature.dispose();
      _feature = feature;
    }
  }

  public final GEOFeature getFeature()
  {
    return _feature;
  }

  public final void symbolize(G3MRenderContext rc, GEOSymbolizer symbolizer, MeshRenderer meshRenderer, ShapesRenderer shapesRenderer, MarksRenderer marksRenderer, GEOTileRasterizer geoTileRasterizer)
  {
    java.util.ArrayList<GEOSymbol> symbols = createSymbols(symbolizer);
    if (symbols != null)
    {
  
      final int symbolsSize = symbols.size();
      for (int i = 0; i < symbolsSize; i++)
      {
        final GEOSymbol symbol = symbols.get(i);
        if (symbol != null)
        {
          final boolean deleteSymbol = symbol.symbolize(rc, symbolizer, meshRenderer, shapesRenderer, marksRenderer, geoTileRasterizer);
          if (deleteSymbol)
          {
            if (symbol != null)
               symbol.dispose();
          }
        }
      }
  
      symbols = null;
    }
  }

}