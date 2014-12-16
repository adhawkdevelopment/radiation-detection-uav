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
/**
 *
 * Redeclared to avoid circular dependancy between contrib and core
 *
 */
package org.rococoa.cocoa.qtkit;

import org.rococoa.NSClass;
import org.rococoa.NSObject;
import org.rococoa.Rococoa;

import com.sun.jna.Structure;
import org.rococoa.cocoa.qtkit.QTTime;
import org.rococoa.cocoa.qtkit.QTTimeRange;
import org.rococoa.cocoa.foundation.NSSize;

public abstract class NSValue implements NSObject {

    public static final _Class CLASS = Rococoa.createClass("NSValue", _Class.class);  //$NON-NLS-1$
    public interface _Class extends NSClass {
        NSValue valueWithSize(NSSize size);
        NSValue valueWithQTTime(QTTime time);
        NSValue valueWithQTTimeRange(QTTimeRange range);
        //NSValue valueWithSMPTETIme(SMPTETime time);
    }

    public abstract QTTime QTTimeValue();

    public static NSValue valueWithQTTime(QTTime time){
        return CLASS.valueWithQTTime(time);
    }

    public static NSValue valueWithQTTimeRange(QTTimeRange range){
        return CLASS.valueWithQTTimeRange(range);
    }
    /*public static NSValue valueWithSMPTETIme(SMPTETime time){
        return CLASS.valueWithSMPTETIme(time);
    }*/

    public static NSValue valueWithSize(NSSize size) {
        return CLASS.valueWithSize(size);
    }

    public abstract NSSize sizeValue();
    public abstract void getValue(Structure p);

}
