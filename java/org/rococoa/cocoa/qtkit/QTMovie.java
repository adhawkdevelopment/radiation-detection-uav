/*
 * Copyright 2007, 2008 Duncan McGregor
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

import java.io.File;
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
import org.rococoa.cocoa.foundation.NSImage;
import org.rococoa.cocoa.foundation.NSString;

import com.sun.jna.Pointer;
import org.rococoa.cocoa.foundation.NSNumber;
import org.rococoa.cocoa.foundation.NSURL;
import org.rococoa.cocoa.qtkit.QTTime;
import org.rococoa.cocoa.qtkit.QTTimeRange;
import org.rococoa.cocoa.qtkit.QTTrack;

/**
 * Wrapper for QTKit QTMovie.
 * 
 * For a discussion on threading and QT see 
 * <a href="http://developer.apple.com/technotes/tn/tn2125.html">TN2125</a>
 * 
 * @author duncan
 *
 */
public
@RunOnMainThread
abstract class QTMovie implements NSObject
{
    private NSObject delegateWrapper;
    private QTMovieDelegate delegate;
    // Loading the QTMovie class has to happen on the main thread
    private static final _Class CLASS = 
        Foundation.callOnMainThread(new Callable<_Class>() {
            public _Class call() throws Exception {
                return Rococoa.wrap(Foundation.getClass("QTMovie"), _Class.class); //$NON-NLS-1$
            }});
    
    // Creating instances has to happen on the main thread
    private @RunOnMainThread interface _Class extends NSClass {
        QTMovie movie();
        QTMovie movieWithFile_error(String fileName, NSObjectByReference errorReference);        
        QTMovie movieWithAttributes_error(NSDictionary attributes, NSObjectByReference errorReference);
        QTMovie movieWithQuickTimeMovie_disposeWhenDone_error(Pointer movie, boolean b, NSObjectByReference errorReference);
        QTMovie movieWithURL_error(NSURL url, NSObjectByReference errorReference);
        boolean canInitWithURL(NSURL url);
        boolean canInitWithFile(String fileName);
        void enterQTKitOnThread();
        void enterQTKitOnThreadDisablingThreadSafetyProtection();
        void exitQTKitOnThread();
    }
    //notifications
    public static final String QTMovieEditabilityDidChangeNotification = "QTMovieEditabilityDidChangeNotification";	// change in movie editability				-					-
    public static final String QTMovieEditedNotification = "QTMovieEditedNotification";	// edit was done to the movie				-					-
    public static final String QTMovieLoadStateDidChangeNotification = "QTMovieLoadStateDidChangeNotification";	// change in movie load state				-					-
    public static final String QTMovieLoopModeDidChangeNotification = "QTMovieLoopModeDidChangeNotification";	// change in movie looping mode				-					-
    public static final String QTMovieMessageStringPostedNotification = "QTMovieMessageStringPostedNotification";	// message string							message				NSString
    public static final String QTMovieRateDidChangeNotification = "QTMovieRateDidChangeNotification";	// change in movie rate						rate				NSNumber (float)
    public static final String QTMovieSelectionDidChangeNotification = "QTMovieSelectionDidChangeNotification";	// change in selection start/duration		-					-
    public static final String QTMovieSizeDidChangeNotification = "QTMovieSizeDidChangeNotification";	// change in movie size						-					-
    public static final String QTMovieStatusStringPostedNotification = "QTMovieStatusStringPostedNotification";	// status string							status				NSString
    public static final String QTMovieTimeDidChangeNotification = "QTMovieTimeDidChangeNotification";	// goto time occured						-					-
    public static final String QTMovieVolumeDidChangeNotification = "QTMovieVolumeDidChangeNotification";	// change in volume							-					-
    public static final String QTMovieDidEndNotification = "QTMovieDidEndNotification";	// movie ended								-					-
    public static final String QTMovieChapterDidChangeNotification = "QTMovieChapterDidChangeNotification";	// change in current chapter				-					-
    public static final String QTMovieChapterListDidChangeNotification = "QTMovieChapterListDidChangeNotification";	// change in chapter list					-					-
    public static final String QTMovieEnterFullScreenRequestNotification = "QTMovieEnterFullScreenRequestNotification";	// full screen playback requested			-					-
    public static final String QTMovieExitFullScreenRequestNotification = "QTMovieExitFullScreenRequestNotification";	// normal windowed playback requested		-					-
    public static final String QTMovieCloseWindowRequestNotification = "QTMovieCloseWindowRequestNotification";	// window close requested					-					-
    public static final String QTMovieApertureModeDidChangeNotification = "QTMovieApertureModeDidChangeNotification";// change in visual aperture mode
    // keys for dictionaries in -chapters array
    public static final String QTMovieChapterName = "QTMovieChapterName"; // NSString
    public static final String QTMovieChapterStartTime = "QTMovieChapterStartTime"; // NSValue (QTTime)
    // keys for attributes dictionary in -addChapters
    public static final String QTMovieChapterTargetTrackAttribute = "QTMovieChapterTargetTrackAttribute"; // QTTrack
    // movie attributes
    public static final String QTMovieApertureModeAttribute = "QTMovieApertureModeAttribute";// NSString
    public static final String QTMovieActiveSegmentAttribute = "QTMovieActiveSegmentAttribute";
    public static final String QTMovieAutoAlternatesAttribute = "QTMovieAutoAlternatesAttribute";// NSNumber (BOOL)
    public static final String QTMovieCopyrightAttribute = "QTMovieCopyrightAttribute";// NSString
    public static final String QTMovieCreationTimeAttribute = "QTMovieCreationTimeAttribute";// NSDate
    public static final String QTMovieCurrentSizeAttribute = "QTMovieCurrentSizeAttribute";// NSValue (NSSize)
    public static final String QTMovieCurrentTimeAttribute = "QTMovieCurrentTimeAttribute";// NSValue (QTTime)
    public static final String QTMovieDataSizeAttribute = "QTMovieDataSizeAttribute";// NSNumber (long long)
    public static final String QTMovieDelegateAttribute = "QTMovieDelegateAttribute";// NSObject
    public static final String QTMovieDisplayNameAttribute = "QTMovieDisplayNameAttribute";// NSString
    public static final String QTMovieDontInteractWithUserAttribute = "QTMovieDontInteractWithUserAttribute";// NSNumber (BOOL)
    public static final String QTMovieDurationAttribute = "QTMovieDurationAttribute";// NSValue (QTTime)
    public static final String QTMovieEditableAttribute = "QTMovieEditableAttribute";// NSNumber (BOOL)
    public static final String QTMovieFileNameAttribute = "QTMovieFileNameAttribute";// NSString
    public static final String QTMovieHasApertureModeDimensionsAttribute = "QTMovieHasApertureModeDimensionsAttribute";// NSNumber (BOOL)
    public static final String QTMovieHasAudioAttribute = "QTMovieHasAudioAttribute";// NSNumber (BOOL)
    public static final String QTMovieHasDurationAttribute = "QTMovieHasDurationAttribute";// NSNumber (BOOL)
    public static final String QTMovieHasVideoAttribute = "QTMovieHasVideoAttribute";// NSNumber (BOOL)
    public static final String QTMovieIsActiveAttribute = "QTMovieIsActiveAttribute";// NSNumber (BOOL)
    public static final String QTMovieIsInteractiveAttribute = "QTMovieIsInteractiveAttribute";// NSNumber (BOOL)
    public static final String QTMovieIsLinearAttribute = "QTMovieIsLinearAttribute";// NSNumber (BOOL)
    public static final String QTMovieIsSteppableAttribute = "QTMovieIsSteppableAttribute";// NSNumber (BOOL)
    public static final String QTMovieLoadStateAttribute = "QTMovieLoadStateAttribute";// NSNumber (long)
    public static final String QTMovieLoopsAttribute = "QTMovieLoopsAttribute";// NSNumber (BOOL)
    public static final String QTMovieLoopsBackAndForthAttribute = "QTMovieLoopsBackAndForthAttribute";// NSNumber (BOOL)
    public static final String QTMovieModificationTimeAttribute = "QTMovieModificationTimeAttribute";// NSDate
    public static final String QTMovieMutedAttribute = "QTMovieMutedAttribute";// NSNumber (BOOL)
    public static final String QTMovieNaturalSizeAttribute = "QTMovieNaturalSizeAttribute";// NSValue (NSSize)
    public static final String QTMoviePlaysAllFramesAttribute = "QTMoviePlaysAllFramesAttribute";// NSNumber (BOOL)
    public static final String QTMoviePlaysSelectionOnlyAttribute = "QTMoviePlaysSelectionOnlyAttribute";// NSNumber (BOOL)
    public static final String QTMoviePosterTimeAttribute = "QTMoviePosterTimeAttribute";// NSValue (QTTime)
    public static final String QTMoviePreferredMutedAttribute = "QTMoviePreferredMutedAttribute";// NSNumber (BOOL)
    public static final String QTMoviePreferredRateAttribute = "QTMoviePreferredRateAttribute";// NSNumber (float)
    public static final String QTMoviePreferredVolumeAttribute = "QTMoviePreferredVolumeAttribute";// NSNumber (float)
    public static final String QTMoviePreviewModeAttribute = "QTMoviePreviewModeAttribute";// NSNumber (BOOL)
    public static final String QTMoviePreviewRangeAttribute = "QTMoviePreviewRangeAttribute";// NSValue (QTTimeRange)
    public static final String QTMovieRateAttribute = "QTMovieRateAttribute";// NSNumber (float)
    public static final String QTMovieSelectionAttribute = "QTMovieSelectionAttribute";// NSValue (QTTimeRange)
    public static final String QTMovieTimeScaleAttribute = "QTMovieTimeScaleAttribute";// NSNumber (long)
    public static final String QTMovieURLAttribute = "QTMovieURLAttribute";// NSURL
    public static final String QTMovieVolumeAttribute = "QTMovieVolumeAttribute";// NSNumber (float)
    public static final String QTMovieRateChangesPreservePitchAttribute = "";	// NSNumber (BOOL)
    public static final String QTMovieOpenAsyncOKAttribute = "QTMovieOpenAsyncOKAttribute";  //$NON-NLS-1$
    public static final long QTMovieLoadStateError = -1L;
    public static final long QTMovieLoadStateLoading = 1000L;
    public static final long QTMovieLoadStateComplete = 100000L;
    
    public static final String QTMovieFlatten = "QTMovieFlatten";  //$NON-NLS-1$
    public static final String QTMovieExport = "QTMovieExport";  //$NON-NLS-1$
    public static final String QTMovieExportType = "QTMovieExportType";  //$NON-NLS-1$
    public static final String QTMovieExportSettings = "QTMovieExportSettings"; // NSData (QTAtomContainer)
    //if __LP64__
    public static final int QTMovieOperationBeginPhase = 0;
    public static final int QTMovieOperationUpdatePercentPhase = 1;
    public static final int QTMovieOperationEndPhase = 2;
    /*else
    QTMovieOperationBeginPhase			= movieProgressOpen,
    QTMovieOperationUpdatePercentPhase  = movieProgressUpdatePercent,
    QTMovieOperationEndPhase			= movieProgressClose
     */

    	// aperture modes
    public static final String QTMovieApertureModeClassic = "QTMovieApertureModeClassic";
    public static final String QTMovieApertureModeClean = "QTMovieApertureModeClean";
    public static final String QTMovieApertureModeProduction = "QTMovieApertureModeProduction";
    public static final String QTMovieApertureModeEncodedPixels = "QTMovieApertureModeEncodedPixels";


    public static QTMovie movie()
    {
        return CLASS.movie();
    }

    public static QTMovie movieWithFile_error(File file, NSObjectByReference errorReference)
    {
        return movieWithFile_error(file.getAbsolutePath(), errorReference);
    }

    public static QTMovie movieWithFile_error(String fileName, NSObjectByReference errorReference)
    {
        return CLASS.movieWithFile_error(fileName, errorReference);
    }

    public static QTMovie movieWithAttributes_error(NSDictionary attributes, NSObjectByReference errorReference)
    {
        return CLASS.movieWithAttributes_error(attributes, errorReference);
    }

    public static QTMovie movieWithQuickTimeMovie_disposeWhenDone_error(Pointer movie, boolean b, NSObjectByReference errorReference)
    {
        return CLASS.movieWithQuickTimeMovie_disposeWhenDone_error(movie, b, errorReference);
    }

    public static QTMovie movieWithURL_error(NSURL url, NSObjectByReference errorReference)
    {
        return CLASS.movieWithURL_error(url, errorReference);
    }

    public static boolean canInitWithFile(String fileName)
    {
        return CLASS.canInitWithFile(fileName);
    }

    public static boolean canInitWithURL(NSURL url)
    {
        return CLASS.canInitWithURL(url);
    }

    //chapter manipulation
    public abstract boolean hasChapters();

    public abstract int chapterCount();

    public abstract NSArray chapters();

    public abstract void addChapters_withAttributes_error(NSArray chapters, NSDictionary attributes, NSObjectByReference errorReference);

    public abstract boolean removeChapters();

    public abstract QTTime startTimeOfChapter(int chapterIndex);		// 0-based index

    public abstract int chapterIndexForTime(QTTime time);				// 0-based index

    public abstract QTTime duration();

    public abstract void gotoBeginning();

    public abstract void gotoEnd();

    public abstract void play();

    public abstract void stop();

    public abstract void stepBackward();

    public abstract void stepForward();

    public abstract void setCurrentTime(QTTime time);

    public abstract QTTime currentTime();

    public abstract void setRate(float speed);

    public abstract float rate();

    public abstract NSImage frameImageAtTime(QTTime time);

    public abstract NSObject attributeForKey(NSString key);
    public abstract NSObject attributeForKey(String key);
    public abstract NSObject attributeForKey(ID keyId);

    public abstract void setAttribute_forKey(NSObject attribute, NSString key);
    public abstract void setAttribute_forKey(NSObject attribute, String key);
    public abstract void setAttribute_forKey(NSObject attribute, ID key);

    public abstract void insertSegmentOfMovie_timeRange_atTime(QTMovie movie, QTTimeRange range, QTTime time);
    public abstract void insertSegmentOfMovie_fromRange_scaledToRange(QTMovie movie, QTTimeRange srcRange, QTTimeRange dstRange);
    public abstract void insertEmptySegmentAt(QTTimeRange range);

    public abstract NSArray tracksOfMediaType(String mediaTypeVideo);
    public abstract NSArray tracksOfMediaType(ID mediaTypeVideo);
    public abstract NSArray tracks();

    public abstract void setSelection(QTTimeRange timeRange);

    public abstract QTTime selectionStart();

    public abstract QTTime selectionDuration();

    public abstract QTTime selectionEnd();

    public abstract boolean writeToFile_withAttributes(String filename, NSDictionary attributes);
    public abstract boolean writeToFile_withAttributes_error(String filename, NSDictionary attributes, NSObjectByReference errorRef);

    public abstract Pointer quickTimeMovie();

    //QTEditing
    public abstract void replaceSelectionWithSelectionFromMovie(ID movie);

    public abstract void appendSelectionFromMovie(ID movie);

//public abstract void insertSegmentOfMovie_timeRange_atTime(QTMovie  movie, QTTimeRange range, QTTime time);
//public abstract void insertSegmentOfMovie_fromRange_scaledToRange(QTMovie  movie, QTTimeRange srcRange, QTTimeRange dstRange);
//public abstract void insertEmptySegmentAt(QTTimeRange range);
    public abstract void deleteSegment(QTTimeRange segment);
//public abstract void scaleSegment(QTTimeRange segment newDuration(QTTime newDuration);

    public abstract void addImage_forDuration_withAttributes(NSImage image, QTTime duration, NSDictionary attributes);

    public abstract QTTrack insertSegmentOfTrack_timeRange_atTime(QTTrack track, QTTimeRange range, QTTime time);

    public abstract QTTrack insertSegmentOfTrack_fromRange_scaledToRange(QTTrack track, QTTimeRange srcRange, QTTimeRange dstRange);

    public abstract void removeTrack(QTTrack track);

    //QTMovieThreading
    public static void enterQTKitOnThread()
    {
        CLASS.enterQTKitOnThread();
    }
    public static void enterQTKitOnThreadDisablingThreadSafetyProtection()
    {
        CLASS.enterQTKitOnThreadDisablingThreadSafetyProtection();
    }
    public static void exitQTKitOnThread()
    {
        CLASS.exitQTKitOnThread();
    }
    public abstract boolean attachToCurrentThread();
    public abstract boolean detachFromCurrentThread();

    public abstract void setIdling_state(boolean state);
    public abstract boolean isIdling();

    public abstract void generateApertureModeDimensions();
    public abstract void removeApertureModeDimensions();

    public synchronized void setDelegate(final QTMovieDelegate delegate)
    {
        this.delegate = delegate;
        if(delegate == null)
        {
            delegateWrapper = null;
            ID id = null;
            setDelegate(id);
        }
        else
        {
            delegateWrapper = Rococoa.proxy(delegate);
            setDelegate(this.delegateWrapper.id());
        }
    }

    public synchronized QTMovieDelegate getDelegate()
    {
        return delegate;
    }

    public abstract void setDelegate(ID delegate);

    public abstract QTMovieDelegate delegate();

    public interface QTMovieDelegate
    {
        public QTMovie externalMovie(NSDictionary dictionary);

        public boolean movie_linkToURL(NSURL url);

        public boolean movie_shouldContinueOperation_withPhase_atPercent_withAttributes(
                QTMovie movie, NSString op, int phase, NSNumber percent, NSDictionary attributes);

        public boolean movieShouldTask(QTMovie movie);
    }

    public class QTMovieDelegateAdapter implements QTMovieDelegate
    {
        public QTMovie externalMovie(NSDictionary dictionary)
        {
            return null;
        }

        public boolean movie_linkToURL(NSURL url)
        {
            return true;
        }

        public boolean movie_shouldContinueOperation_withPhase_atPercent_withAttributes(
                QTMovie movie, NSString op, int phase, NSNumber percent, NSDictionary attributes)
        {
            return true;
        }
        public boolean movieShouldTask(QTMovie movie)
        {
            return true;
        }
    }

}
