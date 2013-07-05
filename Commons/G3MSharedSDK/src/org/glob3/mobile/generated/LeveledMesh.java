package org.glob3.mobile.generated; 
//
//  LeveledMesh.cpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 16/04/13.
//
//

//
//  LeveledMesh.hpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 16/04/13.
//
//



public abstract class LeveledMesh extends Mesh
{
  private Mesh _mesh;

  private int _currentLevel;

  public LeveledMesh(Mesh mesh, int level)
  {
     _mesh = mesh;
     _currentLevel = level;

  }

  public final void setMesh(Mesh mesh, int level)
  {
    if (_mesh != mesh && level >= _currentLevel)
    {
      if (_mesh != null)
         _mesh.dispose();
      _mesh = mesh;
      _currentLevel = level;
    }
  }

  public void dispose()
  {
    if (_mesh != null)
       _mesh.dispose();
  }

  public final int getVertexCount()
  {
    return _mesh.getVertexCount();
  }

  public final Vector3D getVertex(int i)
  {
    return _mesh.getVertex(i);
  }

  public final void render(G3MRenderContext rc, GLState parentState)
  {
    _mesh.render(rc, parentState);
  }

  public final Extent getExtent()
  {
    return _mesh.getExtent();
  }

  public final boolean isTransparent(G3MRenderContext rc)
  {
    return _mesh.isTransparent(rc);
  }

  public final int getLevel()
  {
    return _currentLevel;
  }

}