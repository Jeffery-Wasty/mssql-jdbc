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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.crd.data.wrapper.StatementWrapper;

/**
 * To provide support for datatime2
 * @author yshao
 *
 */
public class CrdJtdsStatement extends StatementWrapper {
	
	private final Connection conn;
	
	CrdJtdsStatement(Connection conn, Statement statement) {
		super(statement);
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
	public Connection getConnection() throws SQLException {
		return conn;
	}
}
