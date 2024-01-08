package com.microsoft.sqlserver.jdbc.crl;

import com.microsoft.sqlserver.jdbc.ConfigRead;
import com.microsoft.sqlserver.jdbc.TestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
    static String tableName = "test_decimal";
    public static void main(String[] args) throws SQLException {
        log(false, Level.FINEST);

        ConfigRead x = ConfigRead.getInstance();
        String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=true;trustServerCertificate=true;selectMethod=cursor";


        try(Connection conn = DriverManager.getConnection(connectionString);
            Statement s = conn.createStatement() ) {
            //s.execute("SELECT * from " + tableName);
            createTable(s);
        }
    }

    private static void createTable(Statement stmt) throws SQLException {
        //String sql = "create table " + tableName + " (c1 int, c2 int, c3 int);";
        String sql = "create table " + tableName + " (";
        for (int i = 1; i <= 100; ++i) {
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