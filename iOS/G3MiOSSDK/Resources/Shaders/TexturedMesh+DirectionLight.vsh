//
//  Default.vsh
//
//  Created by José Miguel Santana Núñez
//

attribute vec4 aPosition;
attribute vec2 aTextureCoord;
attribute vec4 aNormal;

//uniform mat4 uModelview;
uniform mat4 uProjection;
uniform mat4 uCameraModel;
uniform mat4 uModel;

uniform float uPointSize;

uniform vec4 uLightDirection; //MUST BE NORMALIZED

varying vec4 VertexColor;
varying vec2 TextureCoordOut;

varying float diffuseLightIntensity;

void main() {

  vec4 normal = normalize( aNormal);
  vec4 lightDir = normalize( uLightDirection);
  diffuseLightIntensity = max(dot(normal, lightDir), 0.0);

//  gl_Position = uModelview * aPosition;
    gl_Position = uProjection * uCameraModel * uModel * aPosition;
  
  TextureCoordOut = aTextureCoord;

  gl_PointSize = uPointSize;
}