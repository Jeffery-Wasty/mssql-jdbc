package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.RandomUtil;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import com.microsoft.sqlserver.jdbc.TestUtils;
import com.microsoft.sqlserver.testframework.AbstractSQLGenerator;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Test sending timestamps with datetime2 using Bulk Copy and BulkCopyForBatchInsert.
 * See <a href="https://github.com/microsoft/mssql-jdbc/issues/2271">GitHub Issue #2271</a>.
 */
public class GH2271 {

    private static final String tableNameBulkCopyAPI = "testTable";
    private static final String srcTable = "SourceTable";
    private static final String desTable = "DestinationTable";
    static String connectionUrl = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
            "encrypt=false;statementPoolingCacheSize=10;disableStatementPooling=false;enablePrepareOnFirstPreparedStatementCall=true;";

    /**
     * Runs insert with datetime2 and BulkCopyForBatchInsert (w/prepared statement).
     * @throws Exception
     */
    public static void BulkCopyForBatchInsert(boolean retainTable) throws Exception {

        List<Timestamp> loT = new ArrayList<>();
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateString = simpleDateFormat.format(currentDate);
        long rangeEnd = Timestamp.valueOf(currentDateString).getTime();
        Timestamp timestamp = new Timestamp(rangeEnd);
        loT.add(timestamp);

        //String connectionUrl = "jdbc:sqlserver://<serverName>:1433;encrypt=false;databaseName=<dbName>;user=<userName>;password=<pwd>;statementPoolingCacheSize=10;disableStatementPooling=false;enablePrepareOnFirstPreparedStatementCall=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // SQL batch insert WITHOUT BULK API
        try (Connection con = DriverManager.getConnection(connectionUrl  + ";useBulkCopyForBatchInsert=false");
             Statement stmt = con.createStatement();
             PreparedStatement pstmt = con.prepareStatement(
                     "insert into " + tableNameBulkCopyAPI+ " values (?, ?, ?)")) {

            String dropSql = "if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[" + tableNameBulkCopyAPI + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1) DROP TABLE [" + tableNameBulkCopyAPI + "]";
            stmt.execute(dropSql);

            String createSql = "create table " + tableNameBulkCopyAPI + " (c1 varchar(50), expected varchar(50), actual datetime2(3) )";
            stmt.execute(createSql);

            pstmt.setString(1, "bulkCopyForBatchInsert=false");
            pstmt.setString(2, "test"+currentDateString);
            // Time zone conversion to GMT is fine
            pstmt.setTimestamp(3, loT.get(0), gmtCal);
            pstmt.addBatch();
            pstmt.executeBatch();
        }

        // SQL batch insert WITH  BULK COPY API
        try (Connection con = DriverManager.getConnection(connectionUrl + ";useBulkCopyForBatchInsert=true");
             Statement stmt = con.createStatement();
             PreparedStatement pstmt = con.prepareStatement(
                     "insert into " + tableNameBulkCopyAPI+ " values (?, ?, ?)")) {

            pstmt.setString(1, "bulkCopyForBatchInsert=true");
            pstmt.setString(2, "test"+currentDateString);
            // Time zone conversion to GMT DOES NOT HAPPEN. It inserts using the given timestamp value WITHOUT converting to GMT.
            pstmt.setTimestamp(3, loT.get(0), gmtCal);
            pstmt.addBatch();
            pstmt.executeBatch();

            if (!retainTable) {
                dropTestTable(stmt);
            }
        }
    }

    /**
     * Runs insert with datetime2 and Bulk Copy API.
     * @throws SQLException
     */
    public static void BulkCopy(boolean retainTable) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl); Statement stmt = conn.createStatement()) {

            dropTables(stmt);
            createTables(stmt);
            populateSourceTable();

            try (Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                 ResultSet rs = stmt1.executeQuery(
                         "select * from " + AbstractSQLGenerator.escapeIdentifier(srcTable) + " ORDER BY c1 ASC")) {
                try (SQLServerBulkCopy bulkCopy = new SQLServerBulkCopy(conn)) {
                    bulkCopy.setDestinationTableName(AbstractSQLGenerator.escapeIdentifier(desTable));
                    bulkCopy.writeToServer(rs);
                    //verifyDestinationTableData(expectedBigDecimals.length);
                }

                rs.beforeFirst();
                try (SQLServerBulkCopy bulkCopy1 = new SQLServerBulkCopy(conn)) {
                    bulkCopy1.setDestinationTableName(AbstractSQLGenerator.escapeIdentifier(desTable));
                    bulkCopy1.writeToServer(rs);
                    //verifyDestinationTableData(expectedBigDecimals.length * 2);
                }

                rs.beforeFirst();
                try (SQLServerBulkCopy bulkCopy2 = new SQLServerBulkCopy(conn)) {
                    bulkCopy2.setDestinationTableName(AbstractSQLGenerator.escapeIdentifier(desTable));
                    bulkCopy2.writeToServer(rs);
                    //verifyDestinationTableData(expectedBigDecimals.length * 3);
                }
            } finally {
                if (!retainTable)
                    dropTables(stmt);
            }
        }
    }
    private static void populateSourceTable() throws SQLException {
        List<Timestamp> loT = new ArrayList<>();
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateString = simpleDateFormat.format(currentDate);
        long rangeEnd = Timestamp.valueOf(currentDateString).getTime();
        Timestamp timestamp = new Timestamp(rangeEnd);
        loT.add(timestamp);

        String sql = "insert into " + AbstractSQLGenerator.escapeIdentifier(srcTable) + " values (?,?,?)";
        Calendar calGMT = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        try (Connection conn = DriverManager.getConnection(connectionUrl); PreparedStatement p = conn.prepareStatement(sql);
             SQLServerPreparedStatement pstmt = (SQLServerPreparedStatement) p) {
            pstmt.setInt(1, 2);
            pstmt.setString(2, "test"+currentDateString);
            // Time zone conversion to GMT DOES NOT HAPPEN. It inserts using the given timestamp value WITHOUT converting to GMT.
            pstmt.setTimestamp(3, loT.get(0), gmtCal);
            pstmt.execute();
        }
    }
    private static void dropTables(Statement stmt) throws SQLException {
        if (null != srcTable) {
            TestUtils.dropTableIfExists(AbstractSQLGenerator.escapeIdentifier(srcTable), stmt);
        }
        if (null != desTable) {
            TestUtils.dropTableIfExists(AbstractSQLGenerator.escapeIdentifier(desTable), stmt);
        }
    }

    private static void dropTestTable(Statement stmt) throws SQLException {
        if (null != tableNameBulkCopyAPI) {
            TestUtils.dropTableIfExists(AbstractSQLGenerator.escapeIdentifier(tableNameBulkCopyAPI), stmt);
        }
    }
    private static void createTables(Statement stmt) throws SQLException {
        String sql = "create table " + AbstractSQLGenerator.escapeIdentifier(srcTable) + " (c1 int, c2 varchar(50), c3 datetime2(3));";
        stmt.execute(sql);
        sql = "create table " + AbstractSQLGenerator.escapeIdentifier(desTable) + " (c1 int, c2 varchar(50), c3 datetime2(3));";
        stmt.execute(sql);
    }
}
