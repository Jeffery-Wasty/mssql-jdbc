package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCSVFileRecord;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopyOptions;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class GH2380 {

    public static void main(String args[]) throws Exception {
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;";
        SQLServerConnection conn = (SQLServerConnection)DriverManager.getConnection(connectionString);
        try {
            Path csvFile = Files.createTempFile("bulk_csv", ".csv");
            Files.writeString(csvFile, "1, 2022/7/28 12:21:00.0000", StandardCharsets.UTF_8);
            //Files.writeString(csvFile, "1, 2022/7/28 12:21:00", StandardCharsets.UTF_8);

            SQLServerBulkCSVFileRecord bulkRecord = new SQLServerBulkCSVFileRecord(csvFile.toAbsolutePath().toString(),
                    StandardCharsets.UTF_8.name(), ",", false);
            bulkRecord.addColumnMetadata(1, "ID", 4, 1, 1);
            bulkRecord.addColumnMetadata(2, "TEXT", 93 /* timestamp */, 50, 3);

            try (SQLServerBulkCopy bulkCopy = new SQLServerBulkCopy(conn)) {
                SQLServerBulkCopyOptions opt = new SQLServerBulkCopyOptions();
                opt.setKeepIdentity(true);
                bulkCopy.setBulkCopyOptions(opt); // Insert identity keys as is
                bulkCopy.setDestinationTableName("TEST_IDENTITY_TABLE");
                bulkCopy.writeToServer(bulkRecord);
            }


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        Statement statement = conn.createStatement();
        //statement.execute("SET IDENTITY_INSERT dbo.TEST_IDENTITY_TABLE OFF;");
        statement.execute("SET IDENTITY_INSERT dbo.TEST_IDENTITY_TABLE2 ON;");
    }
}
