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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

import com.crd.data.wrapper.ResultSetWrapper;

/**
 * To provide support for datatime2
 * @author yshao
 */
public class CrdJtdsResultSet extends ResultSetWrapper {
	
	private final Statement statement;
	private final CrdJtdsResultSetMetaData rsMetaData;
	
	CrdJtdsResultSet(ResultSet resultSet, Statement statement) throws SQLException {
		super(resultSet);
		this.statement = statement;
		rsMetaData = new CrdJtdsResultSetMetaData(resultSet.getMetaData());
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return rsMetaData;
	}
	
	@Override
	public Date getDate(int columnIndex) throws SQLException {
		if (!rsMetaData.isDatetime2Column(columnIndex)) {
			return super.getDate(columnIndex);
		} else {
			Timestamp tStamp = super.getTimestamp(columnIndex);
			return new Date(tStamp.getTime());
		}
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		if (!rsMetaData.isDatetime2Column(columnIndex)) {
			return super.getTime(columnIndex);
		} else {
			Timestamp tStamp = super.getTimestamp(columnIndex);
			return new Time(tStamp.getTime());
		}
	}
	
	@Override
	public Date getDate(String columnLabel) throws SQLException {
		return getDate(super.findColumn(columnLabel));
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		return getTime(super.findColumn(columnLabel));
	}
	
	@Override
	public Object getObject(int columnIndex) throws SQLException {
		if (!rsMetaData.isDatetime2Column(columnIndex)) {
			return super.getObject(columnIndex);
		}
		return super.getTimestamp(columnIndex);
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		return getObject(super.findColumn(columnLabel));
	}
	
	@Override
	public Statement getStatement()  throws SQLException {
		return statement;
	}
}
