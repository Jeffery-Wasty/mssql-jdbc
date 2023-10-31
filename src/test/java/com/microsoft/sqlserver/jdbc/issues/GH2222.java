package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.*;
import com.microsoft.sqlserver.testframework.AbstractSQLGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;

public class GH2222 {
    public static void run() throws Exception {
        String url = "jdbc:sqlserver://localhost:1433;trustServerCertificate=false;encrypt=false;db=TestDb";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String username = "sa";
        String password = "TestPassword123";
        SQLServerConnection connection = (SQLServerConnection) DriverManager.getConnection(url, username, password);
        try {
            dropIfExists(connection);
            // Create a table
            createTable(connection);
            createType(connection);
            // Create a stored procedure
            createStoredProcedure(connection);

            // Call the stored procedure
            callStoredProcedure(connection);
        } finally {
            // Close the connection when done
            connection.close();
        }
    }

    private static void dropIfExists(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        TestUtils.dropProcedureIfExists("UpsertData", st);
        TestUtils.dropTypeIfExists("SampleTableType", st);
        TestUtils.dropTableIfExists("SampleTable", st);
    }

    private static void createTable(SQLServerConnection connection) throws SQLException {

        String createTableSQL = "CREATE TABLE SampleTable (id INT PRIMARY KEY, value NUMERIC(10, 2))";

        Statement statement = connection.createStatement();

        statement.execute(createTableSQL);
        statement.close();
        System.out.println("Table created successfully.");
    }

    private static void createType(SQLServerConnection connection) throws SQLException {
            String sql = "CREATE TYPE SampleTableType AS TABLE ( id INT, value NUMERIC(38, 10));";
            Statement st = connection.createStatement();

            st.execute(sql);
            st.close();
    }

    private static void createStoredProcedure(SQLServerConnection connection) throws SQLException {
        String createProcedureSQL = "CREATE PROCEDURE " + "UpsertData" + " @InputData "
                    + "SampleTableType" + " READONLY " + " AS " + " BEGIN "
                    + " INSERT INTO " + "SampleTable" + " SELECT * FROM @InputData"
                    + " END";

        Statement statement = connection.createStatement();
        statement.execute(createProcedureSQL);
        statement.close();
        System.out.println("Stored procedure created successfully.");
    }

    private static void callStoredProcedure(SQLServerConnection connection) throws SQLServerException, SQLTimeoutException {
        String callProcedureSQL = "{call UpsertData (?)}";
        SQLServerCallableStatement preparedStatement = (SQLServerCallableStatement) connection.prepareCall(callProcedureSQL);

        SQLServerDataTable tb = new SQLServerDataTable();
        tb.addColumnMetadata("id", Types.INTEGER);
        tb.addColumnMetadata("value", Types.NUMERIC);
        BigDecimal bd = new BigDecimal(0.222);
        bd.setScale(4, BigDecimal.ROUND_FLOOR);
        tb.addRow(1, bd);

        preparedStatement.setStructured(1, "SampleTableType", tb);
        preparedStatement.execute();
        preparedStatement.close();
        System.out.println("Stored procedure executed successfully.");
    }
}