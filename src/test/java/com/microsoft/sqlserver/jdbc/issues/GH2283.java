package com.microsoft.sqlserver.jdbc.issues;

import java.math.BigDecimal;
import java.sql.*;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.TestUtils;
import org.junit.Test;

import static java.sql.Types.DECIMAL;

public class GH2283 {

    public static void run() throws SQLException {
        //testBigDecimalNull();
        testBigDecimalNullMultiple();
        //testBigDecimalNullBatch();
    }

    /**
     * Test big decimal null case
     */
    @Test
    private static void testBigDecimalNull() throws SQLException {
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;";
        try (SQLServerConnection con = (SQLServerConnection) DriverManager.getConnection(connectionString); Statement stmt = con.createStatement()) {
            con.setCalcBigDecimalPrecision(true);
            TestUtils.dropTableIfExists("test_decimal", stmt);
            stmt.executeUpdate("CREATE TABLE " + "test_decimal" + " (id bigint NOT NULL, base_e decimal(31,4)," +
                    "base_d decimal(31,4),base_l numeric(31,4),base_p numeric(31,4), PRIMARY KEY (id))");

            try (PreparedStatement pstmt = con.prepareStatement("insert into " + "test_decimal" + " values(?,?,?,?,?)")) {
                long id = 4;
                pstmt.setObject(1, id);
                BigDecimal baseE = new BigDecimal("0.1");
                pstmt.setObject(2, baseE);
                BigDecimal baseD = new BigDecimal("1.22");
                pstmt.setObject(3, baseD);
                BigDecimal baseL = new BigDecimal("2.333");
                pstmt.setObject(4, null);
                BigDecimal baseP = new BigDecimal("3.4444");
                pstmt.setObject(5, baseP);

                pstmt.execute();
            }

            try (PreparedStatement pstmt = con.prepareStatement("insert into " + "test_decimal" + " values(?,?,?,?,?)")) {
                long id = 5;
                pstmt.setObject(1, id);
                BigDecimal baseE = new BigDecimal("0.1");
                pstmt.setObject(2, baseE);
                BigDecimal baseD = new BigDecimal("1.22");
                pstmt.setObject(3, baseD);
                BigDecimal baseL = new BigDecimal("2.333");
                pstmt.setObject(4, baseL);
                BigDecimal baseP = new BigDecimal("3.4444");
                pstmt.setObject(5, baseP);

                pstmt.execute();
            }

            try (PreparedStatement pstmt = con.prepareStatement("insert into " + "test_decimal" + " values(?,?,?,?,?)")) {
                long id = 6;
                pstmt.setObject(1, id);
                BigDecimal baseE = new BigDecimal("0.1");
                pstmt.setObject(2, baseE);
                BigDecimal baseD = new BigDecimal("1.22");
                pstmt.setObject(3, baseD);
                BigDecimal baseL = new BigDecimal("2.333");
                pstmt.setObject(4, baseL);
                BigDecimal baseP = new BigDecimal("3.4444");
                pstmt.setObject(5, baseP);

                pstmt.execute();
            }
        }
    }

    private static void testBigDecimalNullMultiple() throws SQLException {

        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;";
        try (SQLServerConnection con = (SQLServerConnection) DriverManager.getConnection(connectionString); Statement stmt = con.createStatement()) {
            con.setCalcBigDecimalPrecision(true);
            TestUtils.dropTableIfExists("test_decimal", stmt);
            stmt.executeUpdate("CREATE TABLE " + "test_decimal" + " (id bigint NOT NULL, base_e decimal(31,4)," +
                    "base_d decimal(31,4),base_l numeric(31,4),base_p numeric(31,4), PRIMARY KEY (id))");
            try (PreparedStatement pstmt = con.prepareStatement("insert into " + "test_decimal" + " values(?,?,?,?,?),(?,?,?,?,?),(?,?,?,?,?)")) {
                pstmt.setObject(1, 4);
                pstmt.setBigDecimal(2, new BigDecimal("0.1"));
                pstmt.setBigDecimal(3, new BigDecimal("1.22"));
                pstmt.setBigDecimal(4, new BigDecimal("2.333"));
                pstmt.setBigDecimal(5, new BigDecimal("3.4444"));
                pstmt.setObject(6, 5);
                pstmt.setBigDecimal(7, new BigDecimal("0.1"));
                pstmt.setBigDecimal(8, new BigDecimal("1.22"));
                //pstmt.setBigDecimal(9, new BigDecimal("2.333"));
                pstmt.setObject(9, null, java.sql.Types.DECIMAL, 31);
                //pstmt.setBigDecimal(9, null);
                pstmt.setBigDecimal(10, new BigDecimal("3.4444"));
                pstmt.setObject(11, 6);
                pstmt.setBigDecimal(12, new BigDecimal("0.1"));
                pstmt.setBigDecimal(13, new BigDecimal("1.22"));
                pstmt.setBigDecimal(14, new BigDecimal("2.333"));
                pstmt.setBigDecimal(15, new BigDecimal("3.4444"));
                pstmt.execute();
            }
        }
    }

    private static void testBigDecimalNullBatch() throws SQLException {
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=;userName=;password=;calcBigDecimalPrecision=true;";
        try (SQLServerConnection con = (SQLServerConnection) DriverManager.getConnection(connectionString); Statement stmt = con.createStatement()) {
            stmt.executeUpdate("CREATE TABLE " + "test_decimal" + " (id bigint NOT NULL, base_e decimal(31,4)," +
                    "base_d decimal(31,4),base_l numeric(31,4),base_p numeric(31,4), PRIMARY KEY (id))");
            try (PreparedStatement pstmt = con.prepareStatement("insert into " + "test_decimal" + " values(?,?,?,?,?)")) {
                pstmt.setObject(1, 4);
                pstmt.setBigDecimal(2, new BigDecimal("0.1"));
                pstmt.setBigDecimal(3, new BigDecimal("1.22"));
                pstmt.setBigDecimal(4, new BigDecimal("2.333"));
                pstmt.setBigDecimal(5, new BigDecimal("3.4444"));
                pstmt.addBatch();
                pstmt.setObject(1, 5);
                pstmt.setBigDecimal(2, new BigDecimal("0.1"));
                pstmt.setBigDecimal(3, new BigDecimal("1.22"));
                pstmt.setBigDecimal(4, null);
                pstmt.setBigDecimal(5, new BigDecimal("3.4444"));
                pstmt.addBatch();
                pstmt.setObject(1, 6);
                pstmt.setBigDecimal(2, new BigDecimal("0.1"));
                pstmt.setBigDecimal(3, new BigDecimal("1.22"));
                pstmt.setBigDecimal(4, new BigDecimal("2.333"));
                pstmt.setBigDecimal(5, new BigDecimal("3.4444"));
                pstmt.addBatch();
                pstmt.executeBatch();
            }
        }
    }
}
