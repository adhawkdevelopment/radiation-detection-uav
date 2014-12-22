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
import org.rococoa.cocoa.foundation.NSDictionary;
import org.rococoa.cocoa.foundation.NSString;

/**
 *
 * @author ewan
 */
public @RunOnMainThread abstract class QTCaptureConnection implements NSObject {

    public static final _Class CLASS = new _Class();

    public static class _Class {
        public QTCaptureConnection create() {
            return Foundation.callOnMainThread(new Callable<QTCaptureConnection>() {
                public QTCaptureConnection call() throws Exception {
                    return Rococoa.create("QTCaptureConnection", QTCaptureConnection.class); //$NON-NLS-1$
                }});
        }
    }

    public abstract ID owner();

    public abstract NSString mediaType();	// media types are defined in QTMedia.h
    public abstract QTFormatDescription formatDescription();

    public abstract boolean isEnabled();
    public abstract void setEnabled(boolean enabled);

    public abstract NSDictionary connectionAttributes();
    public abstract void setConnectionAttributes(NSDictionary connectionAttributes);
    public abstract boolean attributeIsReadOnly(NSString attributeKey);
    public abstract boolean attributeIsReadOnly(String attributeKey);
    public abstract ID attributeForKey(NSString attributeKey);
    public abstract ID attributeForKey(String attributeKey);
    public abstract void setAttribute_forKey(ID attribute, NSString key);
    public abstract void setAttribute_forKey(ID attribute, String key);

}
