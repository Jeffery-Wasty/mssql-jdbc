package com.microsoft.sqlserver.jdbc.issues;

import java.sql.*;

public class GH2228 {
    /**
     * Test getTableName() from ResultSetMetaData.
     * See <a href="https://github.com/microsoft/mssql-jdbc/issues/2228">GitHub Issue #2228</a>.
     */
    public static void getTableTest() throws SQLException {
        String sql = "SELECT * FROM country";
        String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=true;trustServerCertificate=true;selectMethod=cursor";

//        try (Connection conn = DriverManager.getConnection(connectionString);Statement stmt = conn.createStatement())
//        {
//            try (ResultSet resultSet = stmt.executeQuery(sql))
//            {
//                ResultSetMetaData metadata = resultSet.getMetaData();
//                String tableName = metadata.getTableName(1);
//                System.out.println(tableName);
//            }
//        }

        try(Connection conn = DriverManager.getConnection(connectionString);
            PreparedStatement ps = conn.prepareStatement(sql) ) {
            //ps.execute();
            ResultSetMetaData md = ps.getMetaData();
            String tableName = md.getTableName( 1 );  // this returns "" instead of "country"

            System.out.println(tableName);
        }
    }
}
