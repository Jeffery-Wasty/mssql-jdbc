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

package com.crd.data.wrapper;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Default wrapper around the ResultSetMetaData
 * @author yshao
 *
 */
public class ResultSetMetaDataWrapper implements ResultSetMetaData {

	private final ResultSetMetaData rsMetaData;
	
	public ResultSetMetaDataWrapper(ResultSetMetaData rsMetaData) {
		this.rsMetaData = rsMetaData;
	}
	
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (isWrapperFor(iface)) {
			return (T)rsMetaData;
		}
		throw new SQLException("This is not a wrapper of a ResultSetMetaData");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return rsMetaData.isWrapperFor(iface);
	}

	@Override
	public int getColumnCount() throws SQLException {
		return rsMetaData.getColumnCount();
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		return rsMetaData.isAutoIncrement(column);
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		return rsMetaData.isCaseSensitive(column);
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		return rsMetaData.isSearchable(column);
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		return rsMetaData.isCurrency(column);
	}

	@Override
	public int isNullable(int column) throws SQLException {
		return rsMetaData.isNullable(column);
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		return rsMetaData.isSigned(column);
	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		return rsMetaData.getColumnDisplaySize(column);
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		return rsMetaData.getColumnLabel(column);
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		return rsMetaData.getColumnName(column);
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		return rsMetaData.getSchemaName(column);
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		return rsMetaData.getPrecision(column);
	}

	@Override
	public int getScale(int column) throws SQLException {
		return rsMetaData.getScale(column);
	}

	@Override
	public String getTableName(int column) throws SQLException {
		return rsMetaData.getTableName(column);
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		return rsMetaData.getCatalogName(column);
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		return rsMetaData.getColumnType(column);
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		return rsMetaData.getColumnTypeName(column);
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		return rsMetaData.isReadOnly(column);
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		return rsMetaData.isWritable(column);
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		return rsMetaData.isDefinitelyWritable(column);
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		return rsMetaData.getColumnClassName(column);
	}

}
