package com.microsoft.sqlserver.jdbc.crl;

import com.microsoft.sqlserver.jdbc.ConfigRead;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.TestUtils;

import java.sql.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
    static String tableName = "test_decimal";
    public static void main(String[] args) throws SQLException {
        log(false, Level.FINEST);

        String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=true;trustServerCertificate=true;selectMethod=cursor;loginTimeout=5;" +
                "connectRetryCount=1;";
                //+"retryExec={2714,2716:1,2*2:CREATE;2715:1,3;+4060,4070};"

        try(Connection conn = DriverManager.getConnection(connectionString);
            Statement s = conn.createStatement()) {
            PreparedStatement ps = conn.prepareStatement("create table test_decimal (c1 int, c2 int, c3 int, c4 int, c5 int,);");
            try {
                createTable(s);
            } catch (SQLServerException e) {
                ps.execute();
            }
        }
    }

    private static void createTable(Statement stmt) throws SQLException {
        //String sql = "create table " + tableName + " (c1 int, c2 int, c3 int);";
        String sql = "create table " + tableName + " (";
        for (int i = 1; i <= 5; ++i) {
            sql += "c" + i + " int, ";
        }
        sql = sql.substring(0,sql.length() - 1);
        sql += ");";
        stmt.execute(sql);
    }

    private static void dropTable(Statement stmt) throws SQLException {
        if (null != tableName) {
            TestUtils.dropTableIfExists(tableName, stmt);
        }
    }

    private static void log(boolean on, Level lvl){
        if (on) {
            final ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(lvl);
            Logger logger = Logger.getLogger("com.microsoft.sqlserver.jdbc");
            logger.addHandler(handler);
            logger.setLevel(lvl);
            logger.log(lvl, "The Sql Server logger is correctly configured.");
        }
    }
}