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

import java.awt.Component;
import java.awt.Dimension;

import com.apple.eawt.CocoaComponent;

/**
 * A component which lets you put a {@link QTCaputureView} into a {@link Component}.
 *
 * Straight copy with find and replace of MovieComponent
 * @author ewan
 *
 */
public class CaptureComponent extends CocoaComponent {

    private QTCaptureView captureView;

    public CaptureComponent(QTCaptureView captureView) {
        this.captureView =  captureView;
    }

    // TODO - this int suspect in 64-bit
    @Override
    @SuppressWarnings("deprecation")
    public int createNSView() {
        return captureView.id().intValue();
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(1024, 768);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(10, 7);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public QTCaptureView view() {
        return captureView;
    }

}
