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
import org.rococoa.NSObject;
import org.rococoa.Rococoa;
import org.rococoa.RunOnMainThread;
import org.rococoa.cocoa.foundation.NSArray;
import org.rococoa.cocoa.foundation.NSString;

/**
 *
 * @author ewan
 */

/*
	Compression options identifiers:

	A QTCompressionOptions object can be created with any of the following identifiers, each representing a set of options that determine how media will be compressed.

	These compression options are appropriate for high quality intermediate video that requires further processing.
		@"QTCompressionOptionsLosslessAppleIntermediateVideo";
		@"QTCompressionOptionsLosslessAnimationVideo";
        @"QTCompressionOptionsJPEGVideo";

	These compression options are appropriate for medium and low quality video that will be used for delivery to destinations such as the internet.
		@"QTCompressionOptions120SizeH264Video";
		@"QTCompressionOptions240SizeH264Video";
		@"QTCompressionOptionsSD480SizeH264Video";
		@"QTCompressionOptions120SizeMPEG4Video";
		@"QTCompressionOptions240SizeMPEG4Video";
		@"QTCompressionOptionsSD480SizeMPEG4Video";

	This compression option is appropriate for lossless audio that requires further processing, or is intended for high fidelity destinations.
		@"QTCompressionOptionsLosslessALACAudio";

	These compression options are appropriate for audio delivered with lossy compression.
		@"QTCompressionOptionsHighQualityAACAudio";		// For music and other high quality audio
		@"QTCompressionOptionsVoiceQualityAACAudio";	// For voice recordings
*/


public @RunOnMainThread abstract class QTCompressionOptions implements NSObject {



    // Loading the QTMovie class has to happen on the main thread
    private static final _Class CLASS =
        Foundation.callOnMainThread(new Callable<_Class>() {
            public _Class call() throws Exception {
                return Rococoa.wrap(Foundation.getClass("QTCompressionOptions"), _Class.class); //$NON-NLS-1$
            }});

    // Creating instances has to happen on the main thread
    private @RunOnMainThread interface _Class extends NSClass {
        NSArray compressionOptionsIdentifiersForMediaType(NSString mediaType);
        NSArray compressionOptionsIdentifiersForMediaType(String mediaType);
        QTCompressionOptions compressionOptionsWithIdentifier(NSString identifier);
        QTCompressionOptions compressionOptionsWithIdentifier(String identifier);
    }

    public static NSArray compressionOptionsIdentifiersForMediaType(NSString mediaType)
    {
        return CLASS.compressionOptionsIdentifiersForMediaType(mediaType);
    }
    public static NSArray compressionOptionsIdentifiersForMediaType(String mediaType)
    {
        return CLASS.compressionOptionsIdentifiersForMediaType(mediaType);
    }
    public static QTCompressionOptions compressionOptionsWithIdentifier(String identifier)
    {
        return CLASS.compressionOptionsWithIdentifier(identifier);
    }
    public static QTCompressionOptions compressionOptionsWithIdentifier(NSString identifier)
    {
        return CLASS.compressionOptionsWithIdentifier(identifier);
    }

    public abstract NSString mediaType();
    public abstract NSString localizedDisplayName();
    public abstract NSString localizedCompressionOptionsSummary();
    public abstract boolean isEqualToCompressionOptions(QTCompressionOptions compressionOptions);

}
