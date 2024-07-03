package com.microsoft.sqlserver.jdbc.issues;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GH2463 {

    private final static String JDBC_URL = "jdbc:sqlserver://localhost:1433;database=TestDb;trustServerCertificate=true";
    private final static String JDBC_USER = "sa";
    private final static String JDBC_PASSWORD = "TestPassword123";

    @ParameterizedTest
    @ValueSource(strings = {"foobar", "foobar()"})
    void testFunctionCall(String value) throws SQLException
    {
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD))
        {
            String call = String.format("{? = call %s}", value);
            try (CallableStatement stmt = con.prepareCall(call))
            {
                stmt.registerOutParameter(1, Types.NVARCHAR);
                stmt.execute();

                assertEquals("foobar", stmt.getObject(1));
            }
        }
    }
}

