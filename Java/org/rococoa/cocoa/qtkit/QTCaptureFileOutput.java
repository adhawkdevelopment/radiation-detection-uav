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

import org.rococoa.ID;
import org.rococoa.RunOnMainThread;
import org.rococoa.cocoa.foundation.NSURL;
import org.rococoa.cocoa.qtkit.QTTime;

/**
 *
 * @author ewan
 */
public @RunOnMainThread interface QTCaptureFileOutput extends QTCaptureOutput
{
    public static final int QTCaptureFileOutputBufferDestinationNewFile	= 1;
    public static final int QTCaptureFileOutputBufferDestinationOldFile	= 2;

    public abstract NSURL outputFileURL();
    public abstract void recordToOutputFileURL(NSURL url);	// calls recordToOutputFileURL:bufferDestination: with a buffer destination of QTCaptureFileOutputBufferDestinationNewFile
    public abstract void recordToOutputFileURL_bufferDestination(NSURL url, int bufferDestination);
    public abstract QTCompressionOptions compressionOptionsForConnection(QTCaptureConnection connection);
    public abstract void setCompressionOptions_forConnection(QTCompressionOptions compressionOptions, QTCaptureConnection connection);
    public abstract QTTime recordedDuration();
    public abstract int recordedFileSize();

    public abstract QTTime maximumRecordedDuration();
    public abstract void setMaximumRecordedDuration(QTTime maximumRecordedDuration);
    public abstract int maximumRecordedFileSize();
    public abstract void setMaximumRecordedFileSize(int maximumRecordedFileSize);

    public abstract ID delegate();
    public abstract void setDelegate(ID delegate);

}
