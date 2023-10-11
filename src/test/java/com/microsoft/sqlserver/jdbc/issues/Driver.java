package com.microsoft.sqlserver.jdbc.issues;

public class Driver {
    public static void main(String[] args) throws Exception {
        GH2228.getTableTest(); //https://github.com/microsoft/mssql-jdbc/issues/2228
    }
}