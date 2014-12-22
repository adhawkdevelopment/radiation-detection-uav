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
import org.rococoa.NSClass;
import org.rococoa.Rococoa;
import org.rococoa.RunOnMainThread;

/**
 *
 * @author ewan
 */
public @RunOnMainThread abstract class QTCaptureDeviceInput extends QTCaptureInput
{
    // Loading the QTMovie class has to happen on the main thread
    private static final _Class CLASS =
        Foundation.callOnMainThread(new Callable<_Class>() {
            public _Class call() throws Exception {
                return Rococoa.wrap(Foundation.getClass("QTCaptureDeviceInput"), _Class.class); //$NON-NLS-1$
            }});

    // Creating instances has to happen on the main thread
    private @RunOnMainThread interface _Class extends NSClass {
        QTCaptureDeviceInput deviceInputWithDevice(QTCaptureDevice device);
    }

    public static QTCaptureDeviceInput deviceInput(QTCaptureDevice device)
    {
        return CLASS.deviceInputWithDevice(device);
    }

    public abstract QTCaptureDevice device();
}
