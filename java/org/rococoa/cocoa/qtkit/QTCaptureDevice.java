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
import org.rococoa.NSClass;
import org.rococoa.NSObject;
import org.rococoa.NSObjectByReference;
import org.rococoa.Rococoa;
import org.rococoa.RunOnMainThread;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSString;

/**
 *
 * @author ewan
 */
public @RunOnMainThread abstract class  QTCaptureDevice implements NSObject{
    
// Loading the QTMovie class has to happen on the main thread
    private static final _Class CLASS = 
        Foundation.callOnMainThread(new Callable<_Class>() {
            public _Class call() throws Exception {
                return Rococoa.wrap(Foundation.getClass("QTCaptureDevice"), _Class.class); //$NON-NLS-1$
            }});
    
    // Creating instances has to happen on the main thread
    private @RunOnMainThread interface _Class extends NSClass {
        NSArray inputDevices();
        NSArray inputDevicesWithMediaType(String mediaType); // media types are defined in QTMedia.h
        QTCaptureDevice defaultInputDeviceWithMediaType(String mediaType); // media types are defined in QTMedia.h
        QTCaptureDevice deviceWithUniqueId(String deviceUniqueID);
    }

    public static NSArray inputDevices()
    {
        return CLASS.inputDevices();
    }
    public static NSArray inputDevicesWithMediaType(String mediaType)
    {
        return CLASS.inputDevicesWithMediaType(mediaType);
    }
    public static QTCaptureDevice defaultInputDeviceWithMediaType(String mediaType)
    {
        return CLASS.defaultInputDeviceWithMediaType(mediaType);
    }
    public static QTCaptureDevice deviceWithUniqueId(String deviceUniqueID)
    {
        return CLASS.deviceWithUniqueId(deviceUniqueID);
    }

    public abstract String uniqueID();
    public abstract String modelUniqueID();
    public abstract String localizedDisplayName();


    public abstract NSArray formatDescriptions();
    public abstract boolean hasMediaType(String mediaType);	// media types are defined in QTMedia.h
    public abstract boolean hasMediaType(NSString mediaType);	// media types are defined in QTMedia.h
    public abstract NSDictionary deviceAttributes();
    public abstract void setDeviceAttributes(NSDictionary deviceAttributes);
    public abstract boolean attributeIsReadOnly(String attributeKey);
    public abstract boolean attributeIsReadOnly(NSString attributeKey);
    public abstract ID attributeForKey(String attributeKey);
    public abstract ID attributeForKey(NSString attributeKey);
    public abstract void setAttribute_forKey(ID attribute, String attributeKey);
    public abstract void setAttribute_forKey(ID attribute, NSString attributeKey);

    // Applications can use KVO with the @"connected" and @"inUseByAnotherApplication" keys to be notified of changes.
    public abstract boolean isConnected();
    public abstract boolean isInUseByAnotherApplication();

    public abstract boolean isOpen();
    public abstract boolean open(NSObjectByReference errorReference);
    public abstract void close();

    
}
