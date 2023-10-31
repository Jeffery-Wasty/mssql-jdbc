package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.RandomUtil;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.TestResource;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GH2188 {
    public static void loginTry() {
        long timerStart = 0;
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;";

        //int connectRetryCount = 3;
        //int connectRetryInterval = 1;
        int longLoginTimeout = 50; // 120 seconds

        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setURL(connectionString);
            ds.setLoginTimeout(longLoginTimeout);
            //ds.setConnectRetryCount(connectRetryCount);
            //ds.setConnectRetryInterval(connectRetryInterval);
            ds.setDatabaseName(RandomUtil.getIdentifier("DataBase"));
            timerStart = System.currentTimeMillis();

            try (Connection con = ds.getConnection()) {
                assertTrue(con == null, TestResource.getResource("R_shouldNotConnect"));
            }
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(TestResource.getResource("R_cannotOpenDatabase")), e.getMessage());
            long totalTime = System.currentTimeMillis() - timerStart;
            //int expectedMinimumTimeInMillis = (connectRetryCount * connectRetryInterval) * 1000; // 3 seconds

            System.out.println("TOTAL TIME: " + totalTime);

            // Minimum time is 0 seconds per attempt and connectRetryInterval * connectRetryCount seconds of interval.
            // Maximum is unknown, but is needs to be less than longLoginTimeout or else this is an issue.
            //assertTrue(totalTime > expectedMinimumTimeInMillis, TestResource.getResource("R_executionNotLong"));
            //assertTrue(totalTime < 0.9 * (longLoginTimeout * 1000L), TestResource.getResource("R_executionTooLong"));

        }
    }
}
