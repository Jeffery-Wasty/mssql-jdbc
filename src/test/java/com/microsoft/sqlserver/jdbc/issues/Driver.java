package com.microsoft.sqlserver.jdbc.issues;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) throws Exception {
//        final ConsoleHandler handler = new ConsoleHandler();
//        handler.setLevel(Level.FINE);
//        Logger logger = Logger.getLogger("com.microsoft.sqlserver.jdbc");
//        logger.addHandler(handler);
//        logger.setLevel(Level.FINEST);
//        logger.log(Level.FINE, "The Sql Server logger is correctly configured.");

        GH2228.getTableTest(); //https://github.com/microsoft/mssql-jdbc/issues/2228
        //GH2188.loginTry();
        //GH2222.run();
    }
}