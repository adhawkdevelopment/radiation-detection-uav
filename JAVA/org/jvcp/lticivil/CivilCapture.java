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
package org.jvcp.lticivil;

import java.awt.Dimension;
import java.util.List;

import org.jvcp.VideoCapture;
import org.jvcp.VideoDevice;

import com.lti.civil.CaptureDeviceInfo;
import com.lti.civil.CaptureException;
import com.lti.civil.CaptureStream;
import com.lti.civil.CaptureSystem;
import com.lti.civil.CaptureSystemFactory;
import com.lti.civil.DefaultCaptureSystemFactorySingleton;

public class CivilCapture extends VideoCapture {

	private CaptureSystem system;
	private CaptureStream captureStream;
	private final CaptureSystemFactory factory = DefaultCaptureSystemFactorySingleton.instance();
	private String videoDeviceID;

	@SuppressWarnings("unchecked")
	public CivilCapture() {
		List<CaptureDeviceInfo> list;
		try {
			system = factory.createCaptureSystem();
			system.init();
			list = system.getCaptureDeviceInfoList();
		} catch (CaptureException e) {
			e.printStackTrace();
			return;
		}
		if ((list==null) || (list.size() == 0)) {
			System.out.println("No video capture device");
			return;
		}
		videoDevices = new CivilDevice[list.size()];
		for (int i = 0; i < list.size(); ++i)
			videoDevices[i] = new CivilDevice( ((CaptureDeviceInfo) list.get(i)).getDescription(), ((CaptureDeviceInfo) list.get(i)).getDeviceID() );
		
		captureComponent = new ImageComponent();
		isInit = true;
	}
	
	public void setDevice(VideoDevice vd) {
		stop();
		videoDeviceID = ((CivilDevice) vd).getDeviceID();
		try {
			captureStream = system.openCaptureDeviceStream(videoDeviceID);
		} catch (CaptureException e) {
			e.printStackTrace();
		}
		captureStream.setObserver((ImageComponent)captureComponent);
	}

	public void start() {
		try {
			if (captureStream != null)
				captureStream.start();
				if ((getCaptureComponent().getSize().width == 0) || (getCaptureComponent().getSize().height == 0)) {
					Dimension d = new Dimension( captureStream.getVideoFormat().getWidth(), captureStream.getVideoFormat().getHeight());
					getCaptureComponent().setSize(d);
					getCaptureComponent().setPreferredSize(d);
				}					
		} catch (CaptureException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			if (captureStream != null)
				captureStream.stop();
		} catch (CaptureException e) {
			e.printStackTrace();
		}
	}
}
