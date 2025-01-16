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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

/**
 * Default wrapper around the DatabaseMetaData
 * @author yshao
 *
 */
public class DatabaseMetaDataWrapper implements DatabaseMetaData {

	private final DatabaseMetaData dbMetaData;
	
	public DatabaseMetaDataWrapper(DatabaseMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}
	
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (isWrapperFor(iface)) {
			return (T)dbMetaData;
		}
		throw new SQLException("This is not a wrapper of a DatabaseMetaData");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dbMetaData.isWrapperFor(iface);
	}

	@Override
	public boolean allProceduresAreCallable() throws SQLException {
		return dbMetaData.allProceduresAreCallable();
	}

	@Override
	public boolean allTablesAreSelectable() throws SQLException {
		return dbMetaData.allTablesAreSelectable();
	}

	@Override
	public String getURL() throws SQLException {
		return dbMetaData.getURL();
	}

	@Override
	public String getUserName() throws SQLException {
		return dbMetaData.getUserName();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return dbMetaData.isReadOnly();
	}

	@Override
	public boolean nullsAreSortedHigh() throws SQLException {
		return dbMetaData.nullsAreSortedHigh();
	}

	@Override
	public boolean nullsAreSortedLow() throws SQLException {
		return dbMetaData.nullsAreSortedLow();
	}

	@Override
	public boolean nullsAreSortedAtStart() throws SQLException {
		return dbMetaData.nullsAreSortedAtStart();
	}

	@Override
	public boolean nullsAreSortedAtEnd() throws SQLException {
		return dbMetaData.nullsAreSortedAtEnd();
	}

	@Override
	public String getDatabaseProductName() throws SQLException {
		return dbMetaData.getDatabaseProductName();
	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		return dbMetaData.getDatabaseProductVersion();
	}

	@Override
	public String getDriverName() throws SQLException {
		return dbMetaData.getDriverName();
	}

	@Override
	public String getDriverVersion() throws SQLException {
		return dbMetaData.getDriverVersion();
	}

	@Override
	public int getDriverMajorVersion() {
		return dbMetaData.getDriverMajorVersion();
	}

	@Override
	public int getDriverMinorVersion() {
		return dbMetaData.getDriverMinorVersion();
	}

	@Override
	public boolean usesLocalFiles() throws SQLException {
		return dbMetaData.usesLocalFiles();
	}

	@Override
	public boolean usesLocalFilePerTable() throws SQLException {
		return dbMetaData.usesLocalFilePerTable();
	}

	@Override
	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		return dbMetaData.supportsMixedCaseIdentifiers();
	}

	@Override
	public boolean storesUpperCaseIdentifiers() throws SQLException {
		return dbMetaData.storesUpperCaseIdentifiers();
	}

	@Override
	public boolean storesLowerCaseIdentifiers() throws SQLException {
		return dbMetaData.storesLowerCaseIdentifiers();
	}

	@Override
	public boolean storesMixedCaseIdentifiers() throws SQLException {
		return dbMetaData.storesMixedCaseIdentifiers();
	}

	@Override
	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		return dbMetaData.supportsMixedCaseQuotedIdentifiers();
	}

	@Override
	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		return dbMetaData.storesUpperCaseQuotedIdentifiers();
	}

	@Override
	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		return dbMetaData.storesLowerCaseQuotedIdentifiers();
	}

	@Override
	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		return dbMetaData.storesMixedCaseQuotedIdentifiers();
	}

	@Override
	public String getIdentifierQuoteString() throws SQLException {
		return dbMetaData.getIdentifierQuoteString();
	}

	@Override
	public String getSQLKeywords() throws SQLException {
		return dbMetaData.getSQLKeywords();
	}

	@Override
	public String getNumericFunctions() throws SQLException {
		return dbMetaData.getNumericFunctions();
	}

	@Override
	public String getStringFunctions() throws SQLException {
		return dbMetaData.getStringFunctions();
	}

	@Override
	public String getSystemFunctions() throws SQLException {
		return dbMetaData.getSystemFunctions();
	}

	@Override
	public String getTimeDateFunctions() throws SQLException {
		return dbMetaData.getTimeDateFunctions();
	}

	@Override
	public String getSearchStringEscape() throws SQLException {
		return dbMetaData.getSearchStringEscape();
	}

	@Override
	public String getExtraNameCharacters() throws SQLException {
		return dbMetaData.getExtraNameCharacters();
	}

	@Override
	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		return dbMetaData.supportsAlterTableWithAddColumn();
	}

	@Override
	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		return dbMetaData.supportsAlterTableWithDropColumn();
	}

	@Override
	public boolean supportsColumnAliasing() throws SQLException {
		return dbMetaData.supportsColumnAliasing();
	}

	@Override
	public boolean nullPlusNonNullIsNull() throws SQLException {
		return dbMetaData.nullPlusNonNullIsNull();
	}

	@Override
	public boolean supportsConvert() throws SQLException {
		return dbMetaData.supportsConvert();
	}

	@Override
	public boolean supportsConvert(int fromType, int toType) throws SQLException {
		return dbMetaData.supportsConvert(fromType, toType);
	}

	@Override
	public boolean supportsTableCorrelationNames() throws SQLException {
		return dbMetaData.supportsTableCorrelationNames();
	}

	@Override
	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		return dbMetaData.supportsDifferentTableCorrelationNames();
	}

	@Override
	public boolean supportsExpressionsInOrderBy() throws SQLException {
		return dbMetaData.supportsExpressionsInOrderBy();
	}

	@Override
	public boolean supportsOrderByUnrelated() throws SQLException {
		return dbMetaData.supportsOrderByUnrelated();
	}

	@Override
	public boolean supportsGroupBy() throws SQLException {
		return dbMetaData.supportsGroupBy();
	}

	@Override
	public boolean supportsGroupByUnrelated() throws SQLException {
		return dbMetaData.supportsGroupByUnrelated();
	}

	@Override
	public boolean supportsGroupByBeyondSelect() throws SQLException {
		return dbMetaData.supportsGroupByBeyondSelect();
	}

	@Override
	public boolean supportsLikeEscapeClause() throws SQLException {
		return dbMetaData.supportsLikeEscapeClause();
	}

	@Override
	public boolean supportsMultipleResultSets() throws SQLException {
		return dbMetaData.supportsMultipleResultSets();
	}

	@Override
	public boolean supportsMultipleTransactions() throws SQLException {
		return dbMetaData.supportsMultipleTransactions();
	}

	@Override
	public boolean supportsNonNullableColumns() throws SQLException {
		return dbMetaData.supportsNonNullableColumns();
	}

	@Override
	public boolean supportsMinimumSQLGrammar() throws SQLException {
		return dbMetaData.supportsMinimumSQLGrammar();
	}

	@Override
	public boolean supportsCoreSQLGrammar() throws SQLException {
		return dbMetaData.supportsCoreSQLGrammar();
	}

	@Override
	public boolean supportsExtendedSQLGrammar() throws SQLException {
		return dbMetaData.supportsExtendedSQLGrammar();
	}

	@Override
	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		return dbMetaData.supportsANSI92EntryLevelSQL();
	}

	@Override
	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		return dbMetaData.supportsANSI92IntermediateSQL();
	}

	@Override
	public boolean supportsANSI92FullSQL() throws SQLException {
		return dbMetaData.supportsANSI92FullSQL();
	}

	@Override
	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		return dbMetaData.supportsIntegrityEnhancementFacility();
	}

	@Override
	public boolean supportsOuterJoins() throws SQLException {
		return dbMetaData.supportsOuterJoins();
	}

	@Override
	public boolean supportsFullOuterJoins() throws SQLException {
		return dbMetaData.supportsFullOuterJoins();
	}

	@Override
	public boolean supportsLimitedOuterJoins() throws SQLException {
		return dbMetaData.supportsLimitedOuterJoins();
	}

	@Override
	public String getSchemaTerm() throws SQLException {
		return dbMetaData.getSchemaTerm();
	}

	@Override
	public String getProcedureTerm() throws SQLException {
		return dbMetaData.getProcedureTerm();
	}

	@Override
	public String getCatalogTerm() throws SQLException {
		return dbMetaData.getCatalogTerm();
	}

	@Override
	public boolean isCatalogAtStart() throws SQLException {
		return dbMetaData.isCatalogAtStart();
	}

	@Override
	public String getCatalogSeparator() throws SQLException {
		return dbMetaData.getCatalogSeparator();
	}

	@Override
	public boolean supportsSchemasInDataManipulation() throws SQLException {
		return dbMetaData.supportsSchemasInDataManipulation();
	}

	@Override
	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		return dbMetaData.supportsSchemasInProcedureCalls();
	}

	@Override
	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		return dbMetaData.supportsSchemasInTableDefinitions();
	}

	@Override
	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		return dbMetaData.supportsSchemasInIndexDefinitions();
	}

	@Override
	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		return dbMetaData.supportsSchemasInPrivilegeDefinitions();
	}

	@Override
	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		return dbMetaData.supportsCatalogsInDataManipulation();
	}

	@Override
	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		return dbMetaData.supportsCatalogsInProcedureCalls();
	}

	@Override
	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		return dbMetaData.supportsCatalogsInTableDefinitions();
	}

	@Override
	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		return dbMetaData.supportsCatalogsInIndexDefinitions();
	}

	@Override
	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		return dbMetaData.supportsCatalogsInPrivilegeDefinitions();
	}

	@Override
	public boolean supportsPositionedDelete() throws SQLException {
		return dbMetaData.supportsPositionedDelete();
	}

	@Override
	public boolean supportsPositionedUpdate() throws SQLException {
		return dbMetaData.supportsPositionedUpdate();
	}

	@Override
	public boolean supportsSelectForUpdate() throws SQLException {
		return dbMetaData.supportsSelectForUpdate();
	}

	@Override
	public boolean supportsStoredProcedures() throws SQLException {
		return dbMetaData.supportsStoredProcedures();
	}

	@Override
	public boolean supportsSubqueriesInComparisons() throws SQLException {
		return dbMetaData.supportsSubqueriesInComparisons();
	}

	@Override
	public boolean supportsSubqueriesInExists() throws SQLException {
		return dbMetaData.supportsSubqueriesInExists();
	}

	@Override
	public boolean supportsSubqueriesInIns() throws SQLException {
		return dbMetaData.supportsSubqueriesInIns();
	}

	@Override
	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		return dbMetaData.supportsSubqueriesInQuantifieds();
	}

	@Override
	public boolean supportsCorrelatedSubqueries() throws SQLException {
		return dbMetaData.supportsCorrelatedSubqueries();
	}

	@Override
	public boolean supportsUnion() throws SQLException {
		return dbMetaData.supportsUnion();
	}

	@Override
	public boolean supportsUnionAll() throws SQLException {
		return dbMetaData.supportsUnionAll();
	}

	@Override
	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		return dbMetaData.supportsOpenCursorsAcrossCommit();
	}

	@Override
	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		return dbMetaData.supportsOpenCursorsAcrossRollback();
	}

	@Override
	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		return dbMetaData.supportsOpenStatementsAcrossCommit();
	}

	@Override
	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		return dbMetaData.supportsOpenStatementsAcrossRollback();
	}

	@Override
	public int getMaxBinaryLiteralLength() throws SQLException {
		return dbMetaData.getMaxBinaryLiteralLength();
	}

	@Override
	public int getMaxCharLiteralLength() throws SQLException {
		return dbMetaData.getMaxCharLiteralLength();
	}

	@Override
	public int getMaxColumnNameLength() throws SQLException {
		return dbMetaData.getMaxColumnNameLength();
	}

	@Override
	public int getMaxColumnsInGroupBy() throws SQLException {
		return dbMetaData.getMaxColumnsInGroupBy();
	}

	@Override
	public int getMaxColumnsInIndex() throws SQLException {
		return dbMetaData.getMaxColumnsInIndex();
	}

	@Override
	public int getMaxColumnsInOrderBy() throws SQLException {
		return dbMetaData.getMaxColumnsInOrderBy();
	}

	@Override
	public int getMaxColumnsInSelect() throws SQLException {
		return dbMetaData.getMaxColumnsInSelect();
	}

	@Override
	public int getMaxColumnsInTable() throws SQLException {
		return dbMetaData.getMaxColumnsInTable();
	}

	@Override
	public int getMaxConnections() throws SQLException {
		return dbMetaData.getMaxConnections();
	}

	@Override
	public int getMaxCursorNameLength() throws SQLException {
		return dbMetaData.getMaxCursorNameLength();
	}

	@Override
	public int getMaxIndexLength() throws SQLException {
		return dbMetaData.getMaxIndexLength();
	}

	@Override
	public int getMaxSchemaNameLength() throws SQLException {
		return dbMetaData.getMaxSchemaNameLength();
	}

	@Override
	public int getMaxProcedureNameLength() throws SQLException {
		return dbMetaData.getMaxProcedureNameLength();
	}

	@Override
	public int getMaxCatalogNameLength() throws SQLException {
		return dbMetaData.getMaxCatalogNameLength();
	}

	@Override
	public int getMaxRowSize() throws SQLException {
		return dbMetaData.getMaxRowSize();
	}

	@Override
	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		return dbMetaData.doesMaxRowSizeIncludeBlobs();
	}

	@Override
	public int getMaxStatementLength() throws SQLException {
		return dbMetaData.getMaxStatementLength();
	}

	@Override
	public int getMaxStatements() throws SQLException {
		return dbMetaData.getMaxStatements();
	}

	@Override
	public int getMaxTableNameLength() throws SQLException {
		return dbMetaData.getMaxTableNameLength();
	}

	@Override
	public int getMaxTablesInSelect() throws SQLException {
		return dbMetaData.getMaxTablesInSelect();
	}

	@Override
	public int getMaxUserNameLength() throws SQLException {
		return dbMetaData.getMaxUserNameLength();
	}

	@Override
	public int getDefaultTransactionIsolation() throws SQLException {
		return dbMetaData.getDefaultTransactionIsolation();
	}

	@Override
	public boolean supportsTransactions() throws SQLException {
		return dbMetaData.supportsTransactions();
	}

	@Override
	public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
		return dbMetaData.supportsTransactionIsolationLevel(level);
	}

	@Override
	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		return dbMetaData.supportsDataDefinitionAndDataManipulationTransactions();
	}

	@Override
	public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
		return dbMetaData.supportsDataManipulationTransactionsOnly();
	}

	@Override
	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		return dbMetaData.dataDefinitionCausesTransactionCommit();
	}

	@Override
	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		return dbMetaData.dataDefinitionIgnoredInTransactions();
	}

	@Override
	public ResultSet getProcedures(String catalog, String schemaPattern,
			String procedureNamePattern) throws SQLException {
		return dbMetaData.getProcedures(catalog, schemaPattern, procedureNamePattern);
	}

	@Override
	public ResultSet getProcedureColumns(String catalog, String schemaPattern,
			String procedureNamePattern, String columnNamePattern)
			throws SQLException {
		return dbMetaData.getProcedureColumns(catalog, schemaPattern,
						procedureNamePattern, columnNamePattern);
	}

	@Override
	public ResultSet getTables(String catalog, String schemaPattern,
			String tableNamePattern, String[] types) throws SQLException {
		return dbMetaData.getTables(catalog, schemaPattern, tableNamePattern,  types);
	}

	@Override
	public ResultSet getSchemas() throws SQLException {
		return dbMetaData.getSchemas();
	}

	@Override
	public ResultSet getCatalogs() throws SQLException {
		return dbMetaData.getCatalogs();
	}

	@Override
	public ResultSet getTableTypes() throws SQLException {
		return dbMetaData.getTableTypes();
	}

	@Override
	public ResultSet getColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		return dbMetaData.getColumns(catalog, schemaPattern, tableNamePattern,  columnNamePattern);
	}

	@Override
	public ResultSet getColumnPrivileges(String catalog, String schema,
			String table, String columnNamePattern) throws SQLException {
		return dbMetaData.getColumnPrivileges(catalog, schema, table, columnNamePattern);
	}

	@Override
	public ResultSet getTablePrivileges(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		return dbMetaData.getTablePrivileges(catalog, schemaPattern, tableNamePattern);
	}

	@Override
	public ResultSet getBestRowIdentifier(String catalog, String schema,
			String table, int scope, boolean nullable) throws SQLException {
		return dbMetaData.getBestRowIdentifier(catalog, schema, table, scope, nullable);
	}

	@Override
	public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
		return dbMetaData.getVersionColumns(catalog, schema, table);
	}

	@Override
	public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
		return dbMetaData.getPrimaryKeys(catalog, schema, table);
	}

	@Override
	public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
		return dbMetaData.getImportedKeys(catalog, schema, table);
	}

	@Override
	public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
		return dbMetaData.getExportedKeys(catalog, schema, table);
	}

	@Override
	public ResultSet getCrossReference(String parentCatalog,
			String parentSchema, String parentTable, String foreignCatalog,
			String foreignSchema, String foreignTable) throws SQLException {
		return dbMetaData.getCrossReference(parentCatalog,
				parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable);
	}

	@Override
	public ResultSet getTypeInfo() throws SQLException {
		return dbMetaData.getTypeInfo();
	}

	@Override
	public ResultSet getIndexInfo(String catalog, String schema, String table,
			boolean unique, boolean approximate) throws SQLException {
		return dbMetaData.getIndexInfo(catalog, schema, table, unique, approximate);
	}

	@Override
	public boolean supportsResultSetType(int type) throws SQLException {
		return dbMetaData.supportsResultSetType(type);
	}

	@Override
	public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
		return dbMetaData.supportsResultSetConcurrency(type, concurrency);
	}

	@Override
	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		return dbMetaData.ownUpdatesAreVisible(type);
	}

	@Override
	public boolean ownDeletesAreVisible(int type) throws SQLException {
		return dbMetaData.ownDeletesAreVisible(type);
	}

	@Override
	public boolean ownInsertsAreVisible(int type) throws SQLException {
		return dbMetaData.ownInsertsAreVisible(type);
	}

	@Override
	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		return dbMetaData.othersUpdatesAreVisible(type);
	}

	@Override
	public boolean othersDeletesAreVisible(int type) throws SQLException {
		return dbMetaData.othersDeletesAreVisible(type);
	}

	@Override
	public boolean othersInsertsAreVisible(int type) throws SQLException {
		return dbMetaData.othersInsertsAreVisible(type);
	}

	@Override
	public boolean updatesAreDetected(int type) throws SQLException {
		return dbMetaData.updatesAreDetected(type);
	}

	@Override
	public boolean deletesAreDetected(int type) throws SQLException {
		return dbMetaData.deletesAreDetected(type);
	}

	@Override
	public boolean insertsAreDetected(int type) throws SQLException {
		return dbMetaData.insertsAreDetected(type);
	}

	@Override
	public boolean supportsBatchUpdates() throws SQLException {
		return dbMetaData.supportsBatchUpdates();
	}

	@Override
	public ResultSet getUDTs(String catalog, String schemaPattern,
			String typeNamePattern, int[] types) throws SQLException {
		return dbMetaData.getUDTs(catalog, schemaPattern, typeNamePattern, types);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dbMetaData.getConnection();
	}

	@Override
	public boolean supportsSavepoints() throws SQLException {
		return dbMetaData.supportsSavepoints();
	}

	@Override
	public boolean supportsNamedParameters() throws SQLException {
		return dbMetaData.supportsNamedParameters();
	}

	@Override
	public boolean supportsMultipleOpenResults() throws SQLException {
		return dbMetaData.supportsMultipleOpenResults();
	}

	@Override
	public boolean supportsGetGeneratedKeys() throws SQLException {
		return dbMetaData.supportsGetGeneratedKeys();
	}

	@Override
	public ResultSet getSuperTypes(String catalog, String schemaPattern,
			String typeNamePattern) throws SQLException {
		return dbMetaData.getSuperTypes(catalog, schemaPattern, typeNamePattern);
	}

	@Override
	public ResultSet getSuperTables(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		return dbMetaData.getSuperTables(catalog, schemaPattern, tableNamePattern);
	}

	@Override
	public ResultSet getAttributes(String catalog, String schemaPattern,
			String typeNamePattern, String attributeNamePattern)
			throws SQLException {
		return dbMetaData.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern);
	}

	@Override
	public boolean supportsResultSetHoldability(int holdability) throws SQLException {
		return dbMetaData.supportsResultSetHoldability(holdability);
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return dbMetaData.getResultSetHoldability();
	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		return dbMetaData.getDatabaseMajorVersion();
	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		return dbMetaData.getDatabaseMinorVersion();
	}

	@Override
	public int getJDBCMajorVersion() throws SQLException {
		return dbMetaData.getJDBCMajorVersion();
	}

	@Override
	public int getJDBCMinorVersion() throws SQLException {
		return dbMetaData.getJDBCMinorVersion();
	}

	@Override
	public int getSQLStateType() throws SQLException {
		return dbMetaData.getSQLStateType();
	}

	@Override
	public boolean locatorsUpdateCopy() throws SQLException {
		return dbMetaData.locatorsUpdateCopy();
	}

	@Override
	public boolean supportsStatementPooling() throws SQLException {
		return dbMetaData.supportsStatementPooling();
	}

	@Override
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		return dbMetaData.getRowIdLifetime();
	}

	@Override
	public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
		return dbMetaData.getSchemas(catalog, schemaPattern);
	}

	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		return dbMetaData.supportsStoredFunctionsUsingCallSyntax();
	}

	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		return dbMetaData.autoCommitFailureClosesAllResultSets();
	}

	@Override
	public ResultSet getClientInfoProperties() throws SQLException {
		return dbMetaData.getClientInfoProperties();
	}

	@Override
	public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
		return dbMetaData.getFunctions(catalog, schemaPattern, functionNamePattern);
	}

	@Override
	public ResultSet getFunctionColumns(String catalog, String schemaPattern,
			String functionNamePattern, String columnNamePattern) throws SQLException {
		return dbMetaData.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern);
	}

	@Override
	public ResultSet getPseudoColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern) throws SQLException {
		return dbMetaData.getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
	}

	@Override
	public boolean generatedKeyAlwaysReturned() throws SQLException {
		return dbMetaData.generatedKeyAlwaysReturned();
	}

}
