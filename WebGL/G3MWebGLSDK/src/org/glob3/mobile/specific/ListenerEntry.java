

package org.glob3.mobile.specific;

import org.glob3.mobile.generated.IBufferDownloadListener;
import org.glob3.mobile.generated.IImageDownloadListener;
import org.glob3.mobile.generated.ILogger;
import org.glob3.mobile.generated.LogLevel;
import org.glob3.mobile.generated.URL;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;


class ListenerEntry {

   final static String                   TAG = "ListenerEntry";

   private boolean                       _canceled;
   private final long                    _requestId;
   private final IBufferDownloadListener _bufferListener;
   private final IImageDownloadListener  _imageListener;


   public ListenerEntry(final IBufferDownloadListener bufferListener,
                        final IImageDownloadListener imageListener,
                        final long requestId) {
      _bufferListener = bufferListener;
      _imageListener = imageListener;
      _requestId = requestId;
      _canceled = false;
   }


   public long getRequestId() {
      return _requestId;
   }


   public IBufferDownloadListener getBufferListener() {
      return _bufferListener;
   }


   public IImageDownloadListener getImageListener() {
      return _imageListener;
   }


   public void cancel() {
      if (_canceled) {
         log(LogLevel.ErrorLevel, ": Listener for requestId=" + _requestId + " already canceled");
      }
      _canceled = true;
   }


   public boolean isCanceled() {
      return _canceled;
   }


   void onCancel(final URL url) {
      if (_bufferListener != null) {
         _bufferListener.onCancel(url);
      }
      if (_imageListener != null) {
         _imageListener.onCancel(url);
      }
   }


   void onError(final URL url) {
      if (_bufferListener != null) {
         _bufferListener.onError(url);
      }
      if (_imageListener != null) {
         _imageListener.onError(url);
      }
   }


   void onDownload(final URL url,
                   final JavaScriptObject data) {


      throw new RuntimeException("NOT IMPLEMENTED ONDOWNLOAD LISTENER ENTRY");


      //      if (_bufferListener != null) {
      //         final IByteBuffer byteBuffer = new ByteBuffer_WebGL(data);
      //
      //         _bufferListener.onDownload(url, byteBuffer);
      //      }
      //      if (_imageListener != null) {
      //         final IImage img = new Image_WebGL(data);
      //
      //         if ((img.getWidth() <= 0) || (img.getHeight() <= 0)) {
      //            log(LogLevel.ErrorLevel, ": Can't create image from data");
      //
      //            return;
      //         }
      //         _imageListener.onDownload(url, img);
      //      }

   }


   void onCanceledDownload(final URL url,
                           final JavaScriptObject data) {


      throw new RuntimeException("NOT IMPLEMENTED ONCANCELED DOWNLOAD LISTENER ENTRY");

      //      
      //      if (_bufferListener != null) {
      //         final IByteBuffer byteBuffer = new ByteBuffer_WebGL(data);
      //
      //         _bufferListener.onCanceledDownload(url, byteBuffer);
      //      }
      //      if (_imageListener != null) {
      //         final IImage img = new Image_WebGL(data);
      //
      //         if ((img.getWidth() <= 0) || (img.getHeight() <= 0)) {
      //            log(LogLevel.ErrorLevel, ": Can't create image from data");
      //
      //            return;
      //         }
      //         _imageListener.onCanceledDownload(url, img);
      //      }

   }


   public void log(final LogLevel level,
                   final String msg) {
      if (ILogger.instance() != null) {
         switch (level) {
            case InfoLevel:
               ILogger.instance().logInfo(TAG + msg);
               break;
            case WarningLevel:
               ILogger.instance().logWarning(TAG + msg);
               break;
            case ErrorLevel:
               ILogger.instance().logError(TAG + msg);
               break;
            default:
               break;
         }
      }
      else {
         GWT.log(TAG + msg);
      }

   }
}
