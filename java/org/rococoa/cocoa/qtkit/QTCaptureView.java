/*
 * Copyright 2009 55 Degrees Ltd.
 *
 * @author Ewan McDougall
 *
 * This file is part of Rococoa, a library to allow Java to talk to Cocoa.
 *
 * Rococoa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Rococoa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Rococoa.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.rococoa.cocoa.qtkit;

import java.util.concurrent.Callable;

import org.rococoa.Foundation;
import org.rococoa.ID;
import org.rococoa.NSObject;
import org.rococoa.Rococoa;
import org.rococoa.RunOnMainThread;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.qtkit.QTCaptureConnection;


public @RunOnMainThread interface QTCaptureView extends NSObject {

    public static final _Class CLASS = new _Class();

    public static class _Class {
        public QTCaptureView create() {
            return Foundation.callOnMainThread(new Callable<QTCaptureView>() {
                public QTCaptureView call() throws Exception {
                    return Rococoa.create("QTCaptureView", QTCaptureView.class); //$NON-NLS-1$
                }});
        }
    }
    public abstract QTCaptureSession captureSession();
    public abstract void setCaptureSession(QTCaptureSession captureSession);

    // If there are multiple video connections that can be previewed, these methods determine which the view will display.
    public abstract NSArray availableVideoPreviewConnections();
    public abstract QTCaptureConnection videoPreviewConnection();
    public abstract void setVideoPreviewConnection(QTCaptureConnection videoPreviewConnection);

    //public abstract NSColor fillColor();
    //public abstract void setFillColor(NSColor fillColor);
    public abstract boolean preservesAspectRatio();
    public abstract void setPreservesAspectRatio(boolean preservesAspectRatio);

    //public abstract NSRect previewBounds();	// Subclasses can override this method to provide custom preview bounds

    public abstract ID delegate();
    public abstract void setDelegate( ID delegate);

}
