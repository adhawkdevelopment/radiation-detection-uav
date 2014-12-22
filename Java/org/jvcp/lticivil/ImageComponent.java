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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.lti.civil.CaptureException;
import com.lti.civil.CaptureObserver;
import com.lti.civil.CaptureStream;
import com.lti.civil.awt.AWTImageConverter;

public class ImageComponent extends JComponent implements CaptureObserver, ComponentListener
{
	private static final Dimension prefDim = new Dimension(320,200);
	
	public ImageComponent()	{ 
		addComponentListener(this);
		setPreferredSize(prefDim);
	}

	public void setImage(BufferedImage image)
	{
		SwingUtilities.invokeLater(new ImageRunnable(image));		
	}

	private class ImageRunnable implements Runnable
	{	private final BufferedImage newImage;

	public ImageRunnable(BufferedImage newImage)
	{
		super();
		this.newImage = newImage;
	}

	public void run()
	{	
		setImageInSwingThread(newImage);
	}

	}

	private BufferedImage image2draw = null;

	private synchronized void setImageInSwingThread(BufferedImage image)
	{
		this.image2draw = image;
		repaint();
	}

	public synchronized void paint(Graphics g)
	{
		if (image2draw != null)
			g.drawImage(image2draw, 0, 0, this);
	}

	public void onError(CaptureStream sender, CaptureException e)
	{	
		e.printStackTrace();
	}

	BufferedImage scaledImage = null;
	int newW=0, newH=0, new0x=0, new0y=0;
	Graphics2D g = null;
	Boolean lock=true;

	public void onNewImage(CaptureStream sender, com.lti.civil.Image image)
	{	
		BufferedImage bufImage = AWTImageConverter.toBufferedImage(image);

		synchronized(lock) {
			if (g==null)
				initScaledImage(bufImage);
			g.drawImage(bufImage, new0x, new0y, new0x+newW, new0y+newH, 0, 0, bufImage.getWidth(), bufImage.getHeight(), this);              
			setImage(scaledImage);
		}
	}
	
	private void initScaledImage(BufferedImage imgIn) {
		newW = (int)getSize().getWidth();
		newH = (int)getSize().getHeight();
		new0x = 0;
		new0y = 0;
		scaledImage = new BufferedImage(newW, newH,  imgIn.getType() == 0? BufferedImage.TYPE_INT_ARGB : imgIn.getType());  
		if (g!=null)
			g.dispose();
		g = scaledImage.createGraphics();
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, newW, newH);
		//			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ); 
		if (  (getSize().getWidth() / getSize().getHeight()) > ((double)imgIn.getWidth(null) / (double)imgIn.getHeight(null)) )
		{ // Fill on height
			newW = imgIn.getWidth(null)*newH/imgIn.getHeight(null);
			new0x = (int)(getSize().getWidth()-newW)/2;
		}
		else
		{ // Fill on width
			newH = imgIn.getHeight(null)*newW/imgIn.getWidth(null);
			new0y = (int)(getSize().getHeight()-newH)/2;
		}
	}

	public void componentResized(ComponentEvent e) {
		synchronized(lock) {
			g = null;
		}
	}
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
}