package com.microsoft.sqlserver.jdbc.issues;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.TestUtils;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.sql.*;


public class GH2534 {
    static void run() throws SQLException {
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true";
        try (SQLServerConnection connection = (SQLServerConnection) DriverManager.getConnection(connectionString); Statement stmt = connection.createStatement()) {
            TestUtils.dropProcedureIfExists("test_bigdecimal", stmt);
            stmt.executeUpdate("""
                create procedure test_bigdecimal
                    @big_decimal_type      decimal(15, 3)       ,
                    @big_decimal_type_o    decimal(15, 3) output
                   as begin
                       set @big_decimal_type_o = @big_decimal_type;
                   end;
                """);
            var call = connection.prepareCall("""
                {call test_bigdecimal(100.241, ?)}""");
            int scale = new BigDecimal("100.241").scale();
            call.registerOutParameter(1, Types.DECIMAL, scale);
            call.execute();

            var actual = call.getBigDecimal(1);

            var expected = new BigDecimal("100.241");

            Assertions.assertEquals(expected, actual);
        }
    }
}
