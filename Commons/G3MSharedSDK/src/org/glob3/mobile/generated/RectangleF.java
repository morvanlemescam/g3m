package org.glob3.mobile.generated; 
//
//  RectangleF.hpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 19/04/13.
//
//



public class RectangleF
{

  public final float _x;
  public final float _y;
  public final float _width;
  public final float _height;

  public RectangleF(float x, float y, float width, float height)
  {
     _x = x;
     _y = y;
     _width = width;
     _height = height;
    if (_width < 0 || _height < 0)
    {
      ILogger.instance().logError("Invalid rectangle extent");
    }
  }

  public RectangleF(RectangleF that)
  {
     _x = that._x;
     _y = that._y;
     _width = that._width;
     _height = that._height;
  }

  public void dispose()
  {
    super.dispose();
  }

  public final boolean equalTo(RectangleF that)
  {
    return ((_x == that._x) && (_y == that._y) && (_width == that._width) && (_height == that._height));
  }

  public final boolean fullContains(RectangleF that)
  {
    final IMathUtils mu = IMathUtils.instance();

    if (!mu.isBetween(that._x, _x, _x + _width))
    {
      return false;
    }

    if (!mu.isBetween(that._x + that._width, _x, _x + _width))
    {
      return false;
    }

    if (!mu.isBetween(that._y, _y, _y + _height))
    {
      return false;
    }

    if (!mu.isBetween(that._y + that._height, _y, _y + _height))
    {
      return false;
    }

    return true;
  }

  public static boolean fullContains(float outerX, float outerY, float outerWidth, float outerHeight, float innerX, float innerY, float innerWidth, float innerHeight)
  {
    final IMathUtils mu = IMathUtils.instance();

    if (!mu.isBetween(innerX, outerX, outerX + outerWidth))
    {
      return false;
    }

    if (!mu.isBetween(innerX + innerWidth, outerX, outerX + outerWidth))
    {
      return false;
    }

    if (!mu.isBetween(innerY, outerY, outerY + outerHeight))
    {
      return false;
    }

    if (!mu.isBetween(innerY + innerHeight, outerY, outerY + outerHeight))
    {
      return false;
    }

    return true;
  }

  public final boolean contains(float x, float y)
  {
    return (x >= _x) && (y >= _y) && (x <= (_x + _width)) && (y <= (_y + _height));
  }


}