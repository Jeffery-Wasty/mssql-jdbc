package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;


public class GH1554 {
    public static void main(String[] args) throws Throwable {
        SQLServerDataSource ds = new SQLServerDataSource();

        String connectionUrl = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=false;useBulkCopyForBatchInsert=true";
        ds.setURL(connectionUrl);

        Date time = new Date(2024,10,12);
        Connection c = ds.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            c.setAutoCommit(false);
            String sql = "INSERT INTO testTable (c1, c2, c3) VALUES (?,?,?)";
            ps = c.prepareStatement(sql);
            try {
                ps.setInt(1, 1);
                ps.setString(2, "123");
                ps.setDate(3, time);
                ps.addBatch();
                ps.executeBatch();
                c.commit();
            } finally {
                ps.close();
            }
        } catch (Throwable t) {
            throw t;
        }
    }
}
