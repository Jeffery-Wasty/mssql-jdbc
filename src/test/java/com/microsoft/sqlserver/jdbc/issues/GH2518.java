package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;


public class GH2518 {
    private final static String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;"
            + "password=TestPassword123;encrypt=true;trustServerCertificate=false;";
    static void run() throws SQLException {
        final SQLServerDataSource ds = new SQLServerDataSource();
        ds.setURL(connectionString);
        ds.setEncrypt("false");
        ds.setUseBulkCopyForBatchInsert(true);
        final Connection con = ds.getConnection();

        final OffsetDateTime odt = OffsetDateTime.now().withSecond(0).withNano(0);
        final PreparedStatement ps = con.prepareStatement("insert into dt_bug(dt) values(?)");
        ps.setObject(1, odt);
        ps.addBatch();
        ps.executeBatch();
    }
}
