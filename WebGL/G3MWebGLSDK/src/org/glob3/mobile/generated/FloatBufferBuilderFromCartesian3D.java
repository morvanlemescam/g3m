package org.glob3.mobile.generated; 
public class FloatBufferBuilderFromCartesian3D extends FloatBufferBuilder
{

  private final CenterStrategy _centerStrategy;
  private float _cx;
  private float _cy;
  private float _cz;

  private void setCenter(Vector3D center)
  {
	_cx = (float)center.x();
	_cy = (float)center.y();
	_cz = (float)center.z();
  }


  public FloatBufferBuilderFromCartesian3D(CenterStrategy cs, Vector3D center)
  {
	  _centerStrategy = cs;
	setCenter(center);
  }

  public final void add(Vector3D vector)
  {
	add((float) vector.x(), (float) vector.y(), (float) vector.z());
  }

  public final void add(double x, double y, double z)
  {
	add((float) x, (float) y, (float) z);
  }

  public final void add(float x, float y, float z)
  {
	if (_centerStrategy == CenterStrategy.FirstVertex && _values.size() == 0)
	{
	  setCenter(new Vector3D(x,y,z));
	}

	if (_centerStrategy != CenterStrategy.NoCenter)
	{
	  x -= _cx;
	  y -= _cy;
	  z -= _cz;
	}

	_values.add(x);
	_values.add(y);
	_values.add(z);
  }

  public final Vector3D getCenter()
  {
	return new Vector3D((double)_cx,(double)_cy,(double)_cz);
  }
}