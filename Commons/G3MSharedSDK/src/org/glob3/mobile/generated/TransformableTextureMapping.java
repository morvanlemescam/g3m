package org.glob3.mobile.generated; 
//
//  TransformableTextureMapping.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 1/9/14.
//
//

//
//  TransformableTextureMapping.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 1/9/14.
//
//



public abstract class TransformableTextureMapping extends TextureMapping
{
  protected float _translationU;
  protected float _translationV;
  protected float _scaleU;
  protected float _scaleV;
  protected float _rotationInRadians;
  protected float _rotationCenterU;
  protected float _rotationCenterV;

  protected TransformableTextureMapping(float translationU, float translationV, float scaleU, float scaleV, float rotationAngleInRadians, float rotationCenterU, float rotationCenterV)
  {
     _translationU = translationU;
     _translationV = translationV;
     _scaleU = scaleU;
     _scaleV = scaleV;
     _rotationInRadians = rotationAngleInRadians;
     _rotationCenterU = rotationCenterU;
     _rotationCenterV = rotationCenterV;
  }

  protected abstract void updateGLState();

  public final void setTranslation(float translationU, float translationV)
  {
    _translationU = translationU;
    _translationV = translationV;
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#warning updateState();
  }

  public final void setScale(float scaleU, float scaleV)
  {
    _scaleU = scaleU;
    _scaleV = scaleV;
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#warning updateState();
  }

  public final void setRotation(float rotationAngleInRadians, float rotationCenterU, float rotationCenterV)
  {
    _rotationInRadians = rotationAngleInRadians;
    _rotationCenterU = rotationCenterU;
    _rotationCenterV = rotationCenterV;
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#warning updateState();
  }

}