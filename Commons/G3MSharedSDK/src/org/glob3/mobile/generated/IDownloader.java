package org.glob3.mobile.generated; 
//
//  IDownloader.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 27/07/12.
//  Copyright (c) 2012 IGO Software SL. All rights reserved.
//

//
//  IDownloader.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 27/07/12.
//  Copyright (c) 2012 IGO Software SL. All rights reserved.
//


//class URL;
//class G3MContext;
//class IBufferDownloadListener;
//class IImageDownloadListener;
//class TimeInterval;
//class FrameTasksExecutor;


public abstract class IDownloader extends Disposable
{
  public void dispose()
  {
    JAVA_POST_DISPOSE
  }

  public abstract void initialize(G3MContext context, FrameTasksExecutor frameTasksExecutor);

  public abstract void onResume(G3MContext context);

  public abstract void onPause(G3MContext context);

  public abstract void onDestroy(G3MContext context);

  public abstract void start();

  public abstract void stop();

  public abstract long requestBuffer(URL url, long priority, TimeInterval timeToCache, boolean readExpired, IBufferDownloadListener listener, boolean deleteListener);

  public abstract long requestImage(URL url, long priority, TimeInterval timeToCache, boolean readExpired, IImageDownloadListener listener, boolean deleteListener);

  public abstract void cancelRequest(long requestId);

  public abstract String statistics();

}