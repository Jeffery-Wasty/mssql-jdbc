/*	Copyright (c) 2015
 *	by Charles River Development, Inc., Burlington, MA
 *
 *	This software is furnished under a license and may be used only in
 *	accordance with the terms of such license.  This software may not be
 *	provided or otherwise made available to any other party.  No title to
 *	nor ownership of the software is hereby transferred.
 *
 *	This software is the intellectual property of Charles River Development, Inc.,
 *	and is protected by the copyright laws of the United States of America.
 *	All rights reserved internationally.
 *
 */

package com.crd.data.wrapper.jtds;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author yshao
 *
 */
class TimestampFormat {

	static final String format(Timestamp tm) {
		return timestampFormat.get().format(tm);
	}
	
	private static final ThreadLocal<SimpleDateFormat> timestampFormat = new ThreadLocal<SimpleDateFormat>() {
        @java.lang.Override protected SimpleDateFormat initialValue() {
             return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
    };
}
