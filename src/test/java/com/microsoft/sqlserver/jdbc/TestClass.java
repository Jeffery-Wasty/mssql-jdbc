package com.microsoft.sqlserver.jdbc;

import java.sql.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class TestClass {
    public static void main(String[] args) throws Exception {
        final ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        Logger logger = Logger.getLogger("com.microsoft.sqlserver.jdbc");
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
        logger.log(Level.FINE, "The Sql Server logger is correctly configured.");
        getTableTest();
    }

    public static void getTableTest() throws Exception {
        String sql = "SELECT country_id FROM country";
        //jdbc:sqlserver://localhost:1433;user=sa;password=TestPassword123;DatabaseName=TestDb;
        String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;encrypt=true;trustServerCertificate=true;selectMethod=cursor";
        try( Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement ps = conn.prepareStatement( sql, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE ) ) {
            ResultSetMetaData md = ps.getMetaData();
            String tableName = md.getTableName( 1 );  // this returns "" instead of "country"
            System.out.println(tableName);
        }
    }
}