package com.example.osiris.Class;

/**
 * Wire
 * Copyright (C) 2016 Wire Swiss GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Please see
 * https://github.com/wireapp/wire-android/blob/master/wire-ui/src/main/java/com/waz/zclient/ui/views/OnDoubleClickListener.java
 */

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Doesn't care about which view is clicked, so should only be used for a single View.
 *
 * Modified by Filippo Beraldo on 02/02/17:
 * - Added the ability to pass the View v when the click event occurs.
 */
public abstract class OnDoubleClickListener implements View.OnClickListener {
    private final int doubleClickTimeout;
    private Handler handler;

    private long firstClickTime;

    public OnDoubleClickListener() {
        doubleClickTimeout = ViewConfiguration.getDoubleTapTimeout();
        firstClickTime = 0L;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onClick(final View v) {
        long now = System.currentTimeMillis();

        if (now - firstClickTime < doubleClickTimeout) {
            handler.removeCallbacksAndMessages(null);
            firstClickTime = 0L;
            onDoubleClick(v);
        } else {
            firstClickTime = now;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onSingleClick(v);
                    firstClickTime = 0L;
                }
            }, doubleClickTimeout);
        }
    }

    public abstract void onDoubleClick(View v);

    public abstract void onSingleClick(View v);

    public void reset() {
        handler.removeCallbacksAndMessages(null);
    }
}