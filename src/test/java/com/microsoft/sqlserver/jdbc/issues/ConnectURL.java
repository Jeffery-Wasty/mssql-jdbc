package com.microsoft.sqlserver.jdbc.issues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectURL {
    public static void main(String[] args) {

        String connectionUrl = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=true;trustServerCertificate=true;useBulkCopyForBatchInsert=true";

        // Create a variable for the connection string.
        //String connectionUrl = "jdbc:sqlserver://sqlserver-ad.public.8e80c02b3c79.database.windows.net:3342;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.8e80c02b3c79.database.windows.net;loginTimeout=30;Authentication=ActiveDirectoryIntegrated;";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT TOP 10 * FROM Person.Contact";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}