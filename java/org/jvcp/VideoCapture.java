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
package org.jvcp;

import java.awt.Component;

import javax.swing.JFrame;

import org.jvcp.lticivil.CivilCapture;
import org.jvcp.qtkit.QTKitCapture;

public abstract class VideoCapture
{
	protected Component captureComponent;
	protected Boolean isInit = false;
	protected VideoDevice[] videoDevices;
	private static int WAIT4INIT = 250;

	public VideoCapture() {	}

	public abstract void start();

	public abstract void stop();

	public abstract void setDevice(VideoDevice vd);

	protected void waitInit() {
		while (! isInit)
			try {
				Thread.sleep(WAIT4INIT);
			} catch (InterruptedException e) {
			}
	}

	public Component getCaptureComponent() {
		waitInit();
		synchronized(isInit) {
			return captureComponent;
		}
	}

	public VideoDevice[] getDeviceList() {
		waitInit();
		synchronized(isInit) {
			return videoDevices;
		}
	}

	public static VideoCapture init() {
		try {			
			Class<?> c = Class.forName("com.apple.eawt.CocoaComponent"); // test to detect OSX
			return new QTKitCapture();
		}
		catch (Exception e) {
			System.out.println("Not OSX, no QTKit");
		}
		try {
			Class<?> c = Class.forName("com.lti.civil.Image"); // test to detect lti-civil
			return new CivilCapture();
		}
		catch (Exception e) {
			System.out.println("No lti-civil");
		} 
		return null;
	}


	public static void main (String [] arg) {

		VideoCapture qtc = VideoCapture.init();
		if (qtc == null) {
			System.out.println("Video capture subsystem not initialized");
			return;
		}
		
		if ((qtc.getDeviceList() == null) || (qtc.getDeviceList().length == 0)){
			System.out.println("No video capture device");
			return;
		}

		for (int i=0; i<qtc.getDeviceList().length;i++)
			if (qtc.getDeviceList()[i] != null)
				System.out.println(i+" : Description: "+ qtc.getDeviceList()[i].getDescription());

		qtc.setDevice( qtc.getDeviceList()[0] );

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(qtc.getCaptureComponent());
		f.pack();
		f.setVisible(true);
		qtc.start();
	}
}
