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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.crd.data.wrapper.PreparedStatementWrapper;

/**
 * To provide support for datatime2
 * @author yshao
 *
 */
public class CrdJtdsPreparedStatement extends PreparedStatementWrapper {
	
	private final Connection conn;
	
	CrdJtdsPreparedStatement(Connection conn, PreparedStatement pStatement) {
		super(pStatement);
		this.conn = conn;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return new CrdJtdsResultSet(super.executeQuery(sql), this);
	}
	
	@Override
	public ResultSet getResultSet() throws SQLException {
		return new CrdJtdsResultSet(super.getResultSet(), this);
	}
	
	@Override
	public ResultSet executeQuery() throws SQLException {
		return new CrdJtdsResultSet(super.executeQuery(), this);
	}

	/**
	 * When the input object x is an Instant or a LocalDateTime, convert to Timestamp and use setString to preserve to the millisecond precision.
	 * Otherwise, use setObject as is.
	 */
	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		if (x instanceof Instant) {
			super.setString(parameterIndex, TimestampFormat.format(Timestamp.from((Instant)x)));
		} else if (x instanceof LocalDateTime ) {
			super.setString(parameterIndex, TimestampFormat.format(Timestamp.valueOf((LocalDateTime)x)));
        } else if (x instanceof LocalDate) {
            super.setObject(parameterIndex, Date.valueOf((LocalDate)x));
		} else {
			super.setObject(parameterIndex, x);
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return conn;
	}

}
