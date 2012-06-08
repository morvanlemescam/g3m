package org.glob3.mobile.generated; 
public class CameraRenderer extends Renderer
{

  private Camera _camera; //Camera used at current frame
  private final Planet _planet; //Planet

  private Camera _camera0 = new Camera(); //Initial Camera saved on Down event
  private MutableVector3D _initialPoint = new MutableVector3D(); //Initial point at dragging

  private Gesture _currentGesture; //Gesture the user is making at the moment

//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//  void onDown(const TouchEvent& event);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//  void onMove(const TouchEvent& event);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//  void onUp(const TouchEvent& event);


  public CameraRenderer()
  {
	  _camera0 = new Camera(0,0);
	  _initialPoint = new MutableVector3D(0,0,0);
	  _currentGesture = Gesture.None;
  }

  public final void initialize(InitializationContext ic)
  {
  }

  public final int render(RenderContext rc)
  {
	_camera = rc.getCamera(); //Saving camera reference
	_planet = rc.getPlanet();
  
	rc.getCamera().draw(rc); //We "draw" the camera with IGL
	return 0;
  }

//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//  boolean onTouchEvent(const TouchEvent* event);


}
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//void CameraRenderer::onDown(const TouchEvent& event)
//{
//  //Saving Camera0
//  Camera c(*_camera);
//  _camera0 = c;
//
//  //Initial Point for Dragging
//  Vector2D pixel = event.getTouch(0)->getPos();
//  Vector3D ray = _camera0.pixel2Vector(pixel);
//  _initialPoint = _planet->closestIntersection(_camera0.getPos(), ray).asMutableVector3D();
//  _currentGesture = Drag; //Dragging
//}

//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//void CameraRenderer::onMove(const TouchEvent& event)
//{
//  int n = event.getNumTouch();
//
//  //ONE FINGER
//  if (n == 1 && _currentGesture == Drag)
//  {
//
//	if (_initialPoint.length() > 0) //VALID INITIAL POINT
//	{
//	  Vector2D pixel = event.getTouch(0)->getPos();
//	  Vector3D ray = _camera0.pixel2Vector(pixel);
//	  Vector3D pos = _camera0.getPos();
//
//	  MutableVector3D finalPoint = _planet->closestIntersection(pos, ray).asMutableVector3D();
//	  if (finalPoint.length() <= 0.0) //INVALID FINAL POINT
//	  {
//		finalPoint = _planet->closestPointToSphere(pos, ray).asMutableVector3D();
//	  }
//	  _camera->copyFrom(_camera0);
//	  _camera->dragCamera(_initialPoint.asVector3D(), finalPoint.asVector3D());
//	}
//  }
//
//
//  //TWO FINGERS
//  if (n==2)
//  {
//	Vector2D pixel0 = event.getTouch(0)->getPos();
//	Vector2D pixel1 = event.getTouch(1)->getPos();
//	Vector2D pixelCenter = pixel0.add(pixel1).div(2.0);
//
//	Vector3D ray = _camera0.pixel2Vector(pixelCenter);
//	_initialPoint = _planet->closestIntersection(_camera0.getPos(), ray).asMutableVector3D();
//
//	//IF CENTER PIXEL INTERSECTS THE PLANET
//	if (_initialPoint.length() > 0)
//	{
//	  //IF THE CENTER OF THE VIEW INTERSECTS THE PLANET
//	  if (_planet->intersections(_camera->getPos(), _camera->getCenter()).size() > 0)
//	  {
//
//		//ZOOM
//		_currentGesture = Zoom; //Zoom gesture
//
//		Vector2D prevPixel0 = event.getTouch(0)->getPrevPos();
//		Vector2D prevPixel1 = event.getTouch(1)->getPrevPos();
//
//		double dist = pixel0.sub(pixel1).length();
//		double prevDist = prevPixel0.sub(prevPixel1).length();
//
//		Vector2D pixelDelta = pixel1.sub(pixel0);
//		Vector2D prevPixelDelta = prevPixel1.sub(prevPixel0);
//
//		Angle angle = pixelDelta.angle();
//		Angle prevAngle = prevPixelDelta.angle();
//
//		//We rotate and zoom the camera with the same gesture
//		_camera->zoom(prevDist /dist);
//		_camera->rotate(angle.sub(prevAngle));
//	  }
//	}
//  }
//
//}

//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//void CameraRenderer::onUp(const TouchEvent& event)
//{
//  _currentGesture = None;
//}

//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to events in Java:
//boolean CameraRenderer::onTouchEvent(const TouchEvent* event)
//{
//  switch (event->getType())
//  {
//	case Down:
//	  onDown(*event);
//	  break;
//	case Move:
//	  onMove(*event);
//	  break;
//	case Up:
//	  onUp(*event);
//	default:
//	  break;
//  }
//
//  return true;
//}