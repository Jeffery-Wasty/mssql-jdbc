package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import com.microsoft.sqlserver.jdbc.TestUtils;
import com.microsoft.sqlserver.testframework.AbstractSQLGenerator;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class GH2309 {

    private static String tableName = "tableName";
    private static final String srcTable = "SourceTable";
    private static final String desTable = "DestinationTable";

    static String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
            "encrypt=true;trustServerCertificate=true;useBulkCopyForBatchInsert=true";
    private static Set<Integer> set = new HashSet<>();

    public static void run() throws SQLException {


        try(Connection conn = DriverManager.getConnection(connectionString);
            Statement s = conn.createStatement() ) {
            dropTable(s);
            createTable(s);
            //test(conn);
            BulkCopy(true);
        }
    }

    private static void test(Connection connection) throws SQLException {
        String sql = "insert into " + tableName + " values (?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, 922337203685477.5607);
            pstmt.setObject(2, 214748.3647);
            pstmt.execute();
        }

        String sql2 = "select * from " + tableName;
        try (PreparedStatement pstmt = connection.prepareStatement(sql2)) {
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                System.out.println(rs.getObject(1));
                System.out.println(rs.getObject(2));
            }
        }


    }

    public static void BulkCopy(boolean retainTable) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString); Statement stmt = conn.createStatement()) {

            dropTable(stmt);
            createTable(stmt);
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
                    dropTable(stmt);
            }
        }
    }

    private static void populateSourceTable() throws SQLException {
        String sql = "insert into " + srcTable + " values (?,?)";

        try (Connection conn = DriverManager.getConnection(connectionString); PreparedStatement p = conn.prepareStatement(sql);
             SQLServerPreparedStatement pstmt = (SQLServerPreparedStatement) p) {
            pstmt.setObject(1, 922337203685487.5808);
            pstmt.setObject(2, 214758.3648);
            pstmt.execute();
        }
    }

    private static void createTable(Statement stmt) throws SQLException {
        String sql = "create table " + tableName + " (c1 money, c2 smallmoney);";
        stmt.execute(sql);
        sql = "create table " + srcTable + " (c1 money, c2 smallmoney);";
        stmt.execute(sql);
        sql = "create table " + desTable + " (c1 money, c2 smallmoney);";
        stmt.execute(sql);
    }

    private static void dropTable(Statement stmt) throws SQLException {
        if (null != tableName) {
            TestUtils.dropTableIfExists(tableName, stmt);
        }
        if (null != srcTable) {
            TestUtils.dropTableIfExists(AbstractSQLGenerator.escapeIdentifier(srcTable), stmt);
        }
        if (null != desTable) {
            TestUtils.dropTableIfExists(AbstractSQLGenerator.escapeIdentifier(desTable), stmt);
        }
    }
}
