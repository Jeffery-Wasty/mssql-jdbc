package com.microsoft.sqlserver.jdbc.issues;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.TestUtils;
import org.junit.Test;

public class GH2283 {

    public static void run() throws SQLException {
        testBigDecimalNull();
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
                pstmt.setObject(4, baseL);
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
                pstmt.setObject(4, null);
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
}
