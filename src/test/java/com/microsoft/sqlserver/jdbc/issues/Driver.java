package com.microsoft.sqlserver.jdbc.issues;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) throws Exception {
        log(false, Level.FINEST);

        GH2534.run();
    }

    private static void log(boolean on, Level lvl){
        if (on) {
            final ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(lvl);
            Logger logger = Logger.getLogger("com.microsoft.sqlserver.jdbc");
            logger.addHandler(handler);
            logger.setLevel(lvl);
            logger.log(lvl, "The Sql Server logger is correctly configured.");
        }
    }
}
