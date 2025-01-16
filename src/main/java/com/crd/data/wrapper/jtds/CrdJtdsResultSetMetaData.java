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

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import com.crd.data.wrapper.ResultSetMetaDataWrapper;

/**
 * To provide right metadata for datatime2
 * 
 * @author yshao
 *
 */
public class CrdJtdsResultSetMetaData extends ResultSetMetaDataWrapper {

	CrdJtdsResultSetMetaData(ResultSetMetaData rsMetaData) throws SQLException {
		super(rsMetaData);
		datetime2Column = new Boolean[rsMetaData.getColumnCount()+1];
	}
	
	@Override
	public int getColumnType(int column) throws SQLException {
		int type = super.getColumnType(column);
		if (type != Types.VARCHAR) {
			return type;
		}
		return isDatetime2Column(column) ? Types.TIMESTAMP : type;
	}
	
	@Override
	public int getPrecision(int column) throws SQLException {
		if (getColumnType(column) == Types.TIMESTAMP) {
			return TIME_STAMP_PRECISION;
		} else {
			return super.getPrecision(column);
		}
	}

	@Override
	public int getScale(int column) throws SQLException {
		if (getColumnType(column) == Types.TIMESTAMP) {
			return TIME_STAMP_SCALE;
		} else {
			return super.getScale(column);
		}
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		if (getColumnType(column) == Types.TIMESTAMP) {
			return "java.sql.Timestamp";
		}
		return super.getColumnClassName(column);
	}
	
	@Override
	public String getColumnTypeName(int column) throws SQLException {
		if (getColumnType(column) == Types.TIMESTAMP) {
			return "datetime";
		}
		return super.getColumnTypeName(column);
	}
	
	boolean isDatetime2Column(int column) throws SQLException {
		if (datetime2Column[column] == null) {
			boolean isDatetime2 = super.getColumnType(column)==Types.VARCHAR &&
				DATETIME2_PRECISION == super.getPrecision(column) &&
				NVARCHAR.equalsIgnoreCase(super.getColumnTypeName(column));
			datetime2Column[column] = isDatetime2;
		}
		return datetime2Column[column];		
	}
	
	private final Boolean[] datetime2Column;
	
	private static final String NVARCHAR = "nvarchar";
	private static final int DATETIME2_PRECISION = 26;
	
	private static final int TIME_STAMP_PRECISION = 23;
	private static final int TIME_STAMP_SCALE = 3;
}
