package com.microsoft.sqlserver.jdbc.issues;

import java.sql.*;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GH2228 {
    /**
     * Test getTableName() from ResultSetMetaData.
     * See <a href="https://github.com/microsoft/mssql-jdbc/issues/2228">GitHub Issue #2228</a>.
     */
    public static void getTableTest() throws SQLException {
        String sql = "SELECT * FROM test_table";
        String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=true;trustServerCertificate=true;selectMethod=cursor";
        try(Connection conn = DriverManager.getConnection(connectionString);
            PreparedStatement ps = conn.prepareStatement( sql, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE ) ) {
            ResultSetMetaData md = ps.getMetaData();
            String tableName = md.getTableName( 1 );  // this returns "" instead of "country"
            System.out.println(tableName);
        }
    }
}
