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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import com.crd.data.wrapper.DatabaseMetaDataWrapper;
import com.crd.data.wrapper.ResultSetWrapper;

/**
 * To provide right metadata for datatime2
 * 
 * @author yshao
 *
 */
public class CrdJtdsDatabaseMetaData extends DatabaseMetaDataWrapper  {
	
	private final Connection conn;
	
	CrdJtdsDatabaseMetaData(Connection conn, DatabaseMetaData dbMetaData) {
		super(dbMetaData);
		this.conn = conn;
	}

	@Override
	public ResultSet getColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		
		ResultSet rs = super.getColumns(catalog, schemaPattern, tableNamePattern,  columnNamePattern);
		return new GetColumnsResultSet(rs);
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return conn;
	}
	
	private static class GetColumnsResultSet extends ResultSetWrapper {
		
		private final static int DATA_TYPE_INDEX = 5;
		private final static int TYPE_NAME_INDEX = 6;
		private final static int COLUMN_SIZE_INDEX = 7;
		private final static int SQL_DATA_TYPE_INDEX = 14;
		
		private final static String DATETIME = "datetime";
		private final static String DATETIME2 = "datetime2";
		
		private final static List<Integer> INDEXES = Arrays.asList(DATA_TYPE_INDEX, TYPE_NAME_INDEX, COLUMN_SIZE_INDEX, SQL_DATA_TYPE_INDEX);
		
		private GetColumnsResultSet(ResultSet resultSet) {
			super(resultSet);
		}
		
		@Override
		public int getInt(int columnIndex) throws SQLException {
			
			if (!INDEXES.contains(columnIndex)) {
				return super.getInt(columnIndex);
			}
			if (!isDatetime2Column()) {
				return super.getInt(columnIndex);
			}
			if (columnIndex==DATA_TYPE_INDEX) {
				return Types.TIMESTAMP; //same as datetime column
			}
			if (columnIndex==COLUMN_SIZE_INDEX) {
				return 23; //same as datetime column
			}
			if (columnIndex==SQL_DATA_TYPE_INDEX) {
				return 9;  //same as datetime column
			}
			throw new SQLException("columnIndex "+columnIndex+" refers to TYPE_NAME which is a String");
		}
		
		@Override
		public String getString(int columnIndex) throws SQLException {
			if (!INDEXES.contains(columnIndex)) {
				return super.getString(columnIndex);
			}
			if (!isDatetime2Column()) {
				return super.getString(columnIndex);
			}
			if (columnIndex!=TYPE_NAME_INDEX) {
				return ""+getInt(columnIndex);
			}
			return DATETIME; //same as datetime column
		}
		
		@Override
		public Object getObject(int columnIndex) throws SQLException {
			if (!INDEXES.contains(columnIndex)) {
				return super.getObject(columnIndex);
			}
			if (!isDatetime2Column()) {
				return super.getObject(columnIndex);
			}
			if (columnIndex!=TYPE_NAME_INDEX) {
				return getInt(columnIndex);
			}
			return DATETIME; //same as datetime column
		}
		
		private boolean isDatetime2Column() throws SQLException {
			return DATETIME2.equalsIgnoreCase(super.getString(TYPE_NAME_INDEX))
					&& Types.NVARCHAR == super.getInt(SQL_DATA_TYPE_INDEX);
		}
	}
}
