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
import org.rococoa.Rococoa;
import org.rococoa.RunOnMainThread;

/**
 *
 * @author ewan
 */
public @RunOnMainThread interface QTCaptureMovieFileOutput extends QTCaptureFileOutput
{
    public static final _Class CLASS = new _Class();

    public static class _Class {
        public QTCaptureMovieFileOutput create() {
            return Foundation.callOnMainThread(new Callable<QTCaptureMovieFileOutput>() {
                public QTCaptureMovieFileOutput call() throws Exception {
                    return Rococoa.create("QTCaptureMovieFileOutput", QTCaptureMovieFileOutput.class); //$NON-NLS-1$
                }});
        }
    }

}
