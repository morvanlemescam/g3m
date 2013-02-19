package org.glob3.mobile.generated; 
//
//  TrailsRenderer.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 10/23/12.
//
//

//
//  TrailsRenderer.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 10/23/12.
//
//



//class Mesh;
//class Planet;

public class Trail
{
  private boolean _visible;
  private final int _maxSteps;
  private boolean _positionsDirty;

  private Color _color ;
  private final float _ribbonWidth;

  private java.util.ArrayList<Geodetic3D> _positions = new java.util.ArrayList<Geodetic3D>();

  private Mesh createMesh(Planet planet)
  {
    final int positionsSize = _positions.size();
  
    if (positionsSize < 2)
    {
      return null;
    }
  
  
    java.util.ArrayList<Double> anglesInDegrees = new java.util.ArrayList<Double>();
    for (int i = 1; i < positionsSize; i++)
    {
      final Geodetic3D current = _positions.get(i);
      final Geodetic3D previous = _positions.get(i - 1);
  
      final Angle angle = Geodetic2D.bearing(previous.latitude(), previous.longitude(), current.latitude(), current.longitude());
  
      final double angleInDegrees = angle.degrees();
      if (i == 1)
      {
        anglesInDegrees.add(angleInDegrees);
        anglesInDegrees.add(angleInDegrees);
      }
      else
      {
        anglesInDegrees.add(angleInDegrees);
        final double avr = (angleInDegrees + anglesInDegrees.get(i - 1)) / 2.0;
        anglesInDegrees.set(i - 1, avr);
      }
    }
  
  
    float centerX = 0F;
    float centerY = 0F;
    float centerZ = 0F;
    final Vector3D offsetP = new Vector3D(_ribbonWidth/2, 0, 0);
    final Vector3D offsetN = new Vector3D(-_ribbonWidth/2, 0, 0);
  
    IFloatBuffer vertices = IFactory.instance().createFloatBuffer(positionsSize * 3 * 2);
  
    final Vector3D rotationAxis = Vector3D.downZ();
    for (int i = 0; i < positionsSize; i++)
    {
      final Geodetic3D position = _positions.get(i);
  
      final MutableMatrix44D rotationMatrix = MutableMatrix44D.createRotationMatrix(Angle.fromDegrees(anglesInDegrees.get(i)), rotationAxis);
      final MutableMatrix44D geoMatrix = planet.createGeodeticTransformMatrix(position);
      final MutableMatrix44D matrix = geoMatrix.multiply(rotationMatrix);
  
      final int i6 = i * 6;
      final Vector3D offsetNTransformed = offsetN.transformedBy(matrix, 1);
      if (i == 0)
      {
        centerX = (float) offsetNTransformed._x;
        centerY = (float) offsetNTransformed._y;
        centerZ = (float) offsetNTransformed._z;
      }
      vertices.rawPut(i6, (float) offsetNTransformed._x - centerX);
      vertices.rawPut(i6 + 1, (float) offsetNTransformed._y - centerY);
      vertices.rawPut(i6 + 2, (float) offsetNTransformed._z - centerZ);
  
  
      final Vector3D offsetPTransformed = offsetP.transformedBy(matrix, 1);
      vertices.rawPut(i6 + 3, (float) offsetPTransformed._x - centerX);
      vertices.rawPut(i6 + 4, (float) offsetPTransformed._y - centerY);
      vertices.rawPut(i6 + 5, (float) offsetPTransformed._z - centerZ);
    }
  
    final Vector3D center = new Vector3D(centerX, centerY, centerZ);
    Mesh surfaceMesh = new DirectMesh(GLPrimitive.triangleStrip(), true, center, vertices, 1, 1, new Color(_color));
  
    // Debug unions
    //  Mesh* edgesMesh = new DirectMesh(GLPrimitive::lines(),
    //                                   false,
    //                                   center,
    //                                   vertices,
    //                                   2,
    //                                   1,
    //                                   Color::newFromRGBA(1, 1, 1, 0.7f));
    //
    //  CompositeMesh* cm = new CompositeMesh();
    //
    //  cm->addMesh(surfaceMesh);
    //  cm->addMesh(edgesMesh);
    //
    //  return cm;
  
    return surfaceMesh;
  
  
    //  FloatBufferBuilderFromGeodetic vertices(CenterStrategy::firstVertex(),
    //                                          planet,
    //                                          Geodetic3D::fromDegrees(0, 0, 0));
    //
    //  const int positionsSize = _positions.size();
    //  for (int i = 0; i < positionsSize; i++) {
    ///#ifdef C_CODE
    //    vertices.add( *(_positions[i]) );
    ///#endif
    ///#ifdef JAVA_CODE
    //	  vertices.add( _positions.get(i) );
    ///#endif
    //  }
    //
    //  return new DirectMesh(GLPrimitive::lineStrip(),
    //                        true,
    //                        vertices.getCenter(),
    //                        vertices.create(),
    //                        _lineWidth,
    //                        1,
    //                        new Color(_color));
  }

  private Mesh _mesh;
  private Mesh getMesh(Planet planet)
  {
    if (_positionsDirty || (_mesh == null))
    {
      if (_mesh != null)
         _mesh.dispose();
      _mesh = createMesh(planet);
      _positionsDirty = false;
    }
    return _mesh;
  }

  public Trail(int maxSteps, Color color, float ribbonWidth)
  {
     _maxSteps = maxSteps;
     _visible = true;
     _positionsDirty = true;
     _mesh = null;
     _color = new Color(color);
     _ribbonWidth = ribbonWidth;
  }

  public void dispose()
  {
    if (_mesh != null)
       _mesh.dispose();
  }

  public final void render(G3MRenderContext rc, GLState parentState)
  {
    if (_visible)
    {
      Mesh mesh = getMesh(rc.getPlanet());
      if (mesh != null)
      {
        mesh.render(rc, parentState);
      }
    }
  }

  public final void setVisible(boolean visible)
  {
    _visible = visible;
  }

  public final boolean isVisible()
  {
    return _visible;
  }

  public final void addPosition(Geodetic3D position)
  {
    _positionsDirty = true;

    if (_maxSteps > 0)
    {
      while (_positions.size() >= _maxSteps)
      {
        final int index = 0;
        if (_positions.get(index) != null)
           _positions.get(index).dispose();

        _positions.remove( index );
      }
    }

    _positions.add(new Geodetic3D(position));
  }

}