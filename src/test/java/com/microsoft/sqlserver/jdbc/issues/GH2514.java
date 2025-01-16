package com.microsoft.sqlserver.jdbc.issues;

import java.sql.*;

public class GH2514 {
    private final static String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;"
            + "password=TestPassword123;encrypt=true;trustServerCertificate=false;";
    static void run() throws SQLException {
        try (Connection con = DriverManager.getConnection(connectionString)) {

        }
    }
}
