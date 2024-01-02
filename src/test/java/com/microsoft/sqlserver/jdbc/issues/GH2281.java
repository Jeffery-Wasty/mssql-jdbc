package com.microsoft.sqlserver.jdbc.issues;

/*
 * Copyright (c) DbVis Software AB. All Rights Reserved.
 */
import java.sql.*;
import java.util.Properties;

public class GH2281 {
    /**
     * Run the query
     */
    private static void test() {
        Connection conn = null;
        try {
            conn = setupConnection();
            final DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("Connect successful. Driver:" + metaData.getDriverVersion() + "   Database: " + metaData.getDatabaseProductVersion());

            System.out.println("Calling supportsTransactions ..");

            DatabaseMetaData data = conn.getMetaData();
            conn.createStatement().execute("SET SHOWPLAN_ALL OFF");
            //Statement statement = conn.createStatement();
            //statement.setMaxRows(20);
            //statement.executeQuery("SELECT 1");
            //conn.createStatement().execute("SET SHOWPLAN_TEXT OFF");
            final boolean supportsTransactions = data.supportsTransactions();
            // The following warning is printed
            // WARNING: ConnectionID:1 ClientConnectionId: 5f7a089b-f1d9-4574-a959-6d5375ffcbb7: SQLServerConnection.supportsTransactions: Discarding unexpected TDS_COLMETADATA (0x81)
            System.out.println("  Result:" + supportsTransactions);
        } catch (Exception e) {
            System.out.println("Connected failed:" + e);
        } finally {

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Exception closing connection");
                }
            }
        }
    }

    private static Connection setupConnection() {
        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "sa");
        connectionProps.put("password", "TestPassword123");
        connectionProps.put("trustServerCertificate", "true");
        connectionProps.put("database", "TestDb");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433", connectionProps);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void run() {
        System.out.println("Running on Java: " + System.getProperty("java.runtime.version"));
        //SqlServerBug bug = new SqlServerBug();
        test();
    }
}
