/*
 * Copyright 2010, Lionel Jeanson
 * 
 * This file is part of JVCP, a Java library to get video capture preview.
 * 
 * JVCP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JVCP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JVCP.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jvcp.qtkit;

import java.awt.Component;

import org.jvcp.VideoCapture;
import org.jvcp.VideoDevice;
import org.rococoa.Rococoa;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSAutoreleasePool;
import org.rococoa.cocoa.qtkit.CaptureComponent;
import org.rococoa.cocoa.qtkit.QTCaptureDevice;
import org.rococoa.cocoa.qtkit.QTCaptureDeviceInput;
import org.rococoa.cocoa.qtkit.QTCaptureSession;
import org.rococoa.cocoa.qtkit.QTCaptureView;
import org.rococoa.cocoa.qtkit.QTKit;
import org.rococoa.cocoa.qtkit.QTMedia;

public class QTKitCapture extends VideoCapture
{
	static {
		// load library
		@SuppressWarnings("unused")
		QTKit instance = QTKit.instance;
	}
	private QTCaptureSession captureSession;
	private CaptureComponent captureComponent;
	private QTCaptureDeviceInput videoCaptureDeviceInput = null; 
	private NSAutoreleasePool pool;
	private QTKitDevice videoDevice;

	public QTKitCapture()
	{
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				synchronized(isInit) {
					pool = NSAutoreleasePool.new_();
					initQT();
					pool.release();
					isInit = true;
				}
			}
		});
	}

	private void initQT()
	{
		QTCaptureView captureView;

		// create capture view
		captureView = QTCaptureView.CLASS.create();

		// create capture session
		captureSession = QTCaptureSession.CLASS.create();
		captureView.setCaptureSession(captureSession);    

		// set input devices list
		NSArray devices = QTCaptureDevice.inputDevicesWithMediaType(QTMedia.QTMediaTypeVideo); 
		videoDevices = new QTKitDevice[ devices.count() ];
		QTCaptureDevice qtcd;
		for (int i=0; i < devices.count();i++) {
			qtcd = Rococoa.cast(devices.objectAtIndex(i), QTCaptureDevice.class) ;
			videoDevices[i] = new QTKitDevice( qtcd.description(), qtcd.uniqueID() );
		}

		// build capture component
		captureComponent = new CaptureComponent(captureView);
	}
	

	public Component getCaptureComponent() {
		waitInit();
		synchronized(isInit) {
			return captureComponent;
		}
	}

	public void start() {	
		waitInit();
		synchronized(isInit) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					captureSession.startRunning();
				}
			});
		}
	}

	public void stop() {
		waitInit();
		synchronized(isInit) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					captureSession.stopRunning();
				}
			});
		}
	}
	
	public void setDevice(VideoDevice vd) {
		if (vd == null) return;
		videoDevice = (QTKitDevice) vd;
		waitInit();
		synchronized(isInit) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					NSArray devices = QTCaptureDevice.inputDevicesWithMediaType(QTMedia.QTMediaTypeVideo);
					QTCaptureDevice qtcd = null;
					int i;
					for (i=0; i < devices.count();i++) {
						qtcd = Rococoa.cast(devices.objectAtIndex(i), QTCaptureDevice.class) ;
						if ( videoDevice.getDescription().compareTo(qtcd.description())==0 && videoDevice.getUID().compareTo(qtcd.uniqueID())==0 )
							break;
					}
					if (i == devices.count() || qtcd == null) {
						System.out.println("No such video capture device "+	videoDevice.getDescription());
						return;
					}
					boolean success = true;
					success = qtcd.open(null);
					if(!success) {
						System.out.println("videoDevice.open: FAIL");
						return ;
					}

					if (videoCaptureDeviceInput != null) {
						captureSession.removeInput(videoCaptureDeviceInput);
						videoCaptureDeviceInput.device().close();
					}
					videoCaptureDeviceInput = QTCaptureDeviceInput.deviceInput(qtcd);
					success = captureSession.addInput_error(videoCaptureDeviceInput, null);
					if(!success) {
						System.out.println("captureSession.addInput_error: FAIL");
						return ;
					}
				}
			});
		}
	}
}
