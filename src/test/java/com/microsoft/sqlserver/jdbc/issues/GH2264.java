package com.microsoft.sqlserver.jdbc.issues;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.TestUtils;
import com.microsoft.sqlserver.testframework.AbstractSQLGenerator;

public class GH2264 {

    static String connectionUrl = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
            "encrypt=false;statementPoolingCacheSize=1000;disableStatementPooling=false;enablePrepareOnFirstPreparedStatementCall=true;";
    public static void run() throws SQLException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setURL(connectionUrl);

        try (Connection c = ds.getConnection()) {
            dropIfExists(c);
            createTable(c);
            c.setAutoCommit(false); // or true, doesn't change the outcome
            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE tab SET c1=?, c2=?, c3=?, c4=?, c5=?, c6=?, c7=?, c8=?, c9=?, c10=?, c11=?, c12=?, c13=?, c14=?, c15=?, c16=?, c17=?, c18=?, c19=?, c20=? WHERE cKey=?")) {
                for (int i = 0; i < 10_000_000; i++) {
                    setArguments(i, ps);
                    ps.executeUpdate();
                    if (i % 100_000 == 0)
                        System.out.println(" " + i);
                }
            }
            c.commit();
        }
    }

    private static void setArguments(int i, PreparedStatement ps) throws SQLException {
        ps.setString(21, "key");
        for(int c = 1; c < 21; c++) {
            //for each iteration use a DECIMAL definition declaration encoding it in binary
            boolean bit = (i & (1 << (c-1))) != 0;
            BigDecimal num = bit ? new BigDecimal(1.1) : new BigDecimal(1);
            ps.setBigDecimal(c, num);
        }
    }

    private static void createTable(Connection c) throws SQLException {
        try (Statement s = c.createStatement()) {
            s.execute("CREATE TABLE tab (cKey VARCHAR(100), c1 DECIMAL, c2 DECIMAL, c3 DECIMAL,"
                    +"c4 DECIMAL, c5 DECIMAL, c6 DECIMAL, c7 DECIMAL, c8 DECIMAL, c9 DECIMAL,"
                    +"c10 DECIMAL, c11 DECIMAL, c12 DECIMAL, c13 DECIMAL, c14 DECIMAL, c15 DECIMAL,"
                    +"c16 DECIMAL, c17 DECIMAL, c18 DECIMAL, c19 DECIMAL, c20 DECIMAL)");
            s.execute("INSERT INTO tab(cKey) VALUES('key')");
        }
    }

    private static void dropIfExists(Connection c) throws SQLException {
        try (Statement s = c.createStatement()) {
            if (null != "tab") {
                TestUtils.dropTableIfExists(AbstractSQLGenerator.escapeIdentifier("tab"), s);
            }
        }
    }
}