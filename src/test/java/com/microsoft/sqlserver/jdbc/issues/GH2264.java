package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.TestUtils;
import com.microsoft.sqlserver.testframework.AbstractSQLGenerator;

import java.sql.*;
import java.util.*;

public class GH2264 {

    private static String tableName = "tableName";
    private static Set<Integer> set = new HashSet<>();

    public static void run() throws SQLException {

        String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=true;trustServerCertificate=true;selectMethod=cursor";


        try(Connection conn = DriverManager.getConnection(connectionString);
            Statement s = conn.createStatement() ) {
            dropTable(s);
            createTable(s);
            test();
        }
    }

    private static void test() throws SQLException {
        // Table to insert to
        // For a number of random data
        // Try to update appropriate column in table
        // If no rows were updated, then insert

        //String updateSQL = "";
        String connectionString = "jdbc:sqlserver://localhost:1433;database=TestDb;user=sa;password=TestPassword123;" +
                "encrypt=true;trustServerCertificate=true;selectMethod=cursor";


        try(Connection conn = DriverManager.getConnection(connectionString)) {
            for (int i = 0; i < 100000; i++) {
                Random rand = new Random();
                int j = rand.nextInt(100) + 1;
                // Generate a random number, the first j columns will be updated. This is to simulate random PS sizes.
                set.clear();
                addRandomColumns(set,j);
                // We now have a distinct set of columns
                PreparedStatement ps = conn.prepareStatement(createInsert(j));

                setInt(ps, j).execute();
            }
        }
    }

    private static void addRandomColumns(Set<Integer> set,int j) {
        // Add j random columns to the set
        for (int i = 0; i < j;) {
            int k = (int) Math.ceil(Math.random() * 100); // Random num
            if (set.add(k)) {
                // Try to add, if able to, increment
                ++i;
            }
            // If not try again with a new randomly generated number
            // Eventually we will get j random distinct numbers in a set.
        }
    }

    private static PreparedStatement setInt(PreparedStatement ps, int times) throws SQLException {
        for (int i = 1; i <= times; ++i) {
            ps.setInt(i, (int) (Math.random() * 100));
        }

        return ps;
    }

    private static void createTable(Statement stmt) throws SQLException {
        //String sql = "create table " + tableName + " (c1 int, c2 int, c3 int);";
        String sql = "create table " + tableName + " (";
        for (int i = 1; i <= 100; ++i) {
            sql += "c" + i + " int, ";
        }
        sql = sql.substring(0,sql.length() - 1);
        sql += ");";
        stmt.execute(sql);
    }

    private static String createInsert(int random) {
        int o = set.size();
        Iterator<Integer> iterator = set.iterator();
        // Only create insert for the first 'random' columns.
        String sql = "INSERT INTO [" + tableName + "] ([";
        while (iterator.hasNext()) {
            Integer setElement = iterator.next();
            sql += "c" + setElement + "], [";
        }

//        for (int i = 1; i <= random; ++i) {
//            sql += "c" + i + "], [";
//        }
        sql = sql.substring(0,sql.length() - 3);
        sql += ") VALUES (";

        Iterator<Integer> iterator2 = set.iterator();

        while (iterator2.hasNext()) {
            Integer setElement = iterator2.next();
            sql += "?,";
        }

//        for (int i = 1; i <= random; ++i) {
//            sql += "?,";
//        }
        sql = sql.substring(0,sql.length() - 1);
        sql += ")";
        return sql;
    }

    private static String createInsert2() {
        String sql = "INSERT INTO [" + tableName + "] ([c";
        sql += (int) Math.floor((Math.random() * 100) + 1);
        sql += "]) VALUES (?);";
        return sql;
    }

    private static void dropTable(Statement stmt) throws SQLException {
        if (null != tableName) {
            TestUtils.dropTableIfExists(tableName, stmt);
        }
    }
}
