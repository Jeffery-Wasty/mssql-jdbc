package com.microsoft.sqlserver.jdbc;

import java.util.Date;
import java.util.HashMap;

class ConfigRead {
    // This is the "driver level retry provider" from the design doc
    // Resp for reading config file
    // Logical flow:
    // 1) If this doesn't exist, create and read config
    // 2) If it does exist, check time this last read from config
    // 3) If past exp time, re-read config to update singleton
    // 4) Regardless return a reference to this.
    private static ConfigRead single_instance = null;
    private static long timeRead;

    // Since changes in config should affect all connections, this is the only place we can put the list of rules.
    HashMap<String,ConfigRetryRule> cxnRules = new HashMap<>();
    HashMap<String,ConfigRetryRule> stmtRules = new HashMap<>();
    private ConfigRead() {
        timeRead = new Date().getTime(); // Then set the time read, maybe switch order.
        readConfig(); // This is only ran first time, so we always read config
    }

    public static synchronized ConfigRead getInstance() {
        if (single_instance == null) {
            single_instance = new ConfigRead();
        } else {
            // We know there is an existing ConfigRead
            // First check the time, if it's out of range, re-read.
            Date currentDate = new Date();
            long currentTime = currentDate.getTime();

            if ((currentTime - timeRead) >= 60000) {
                timeRead = currentTime; // We only update the time when we read config, not when we check if out of range.
                readConfig();
            }
        }

        return single_instance;
    }


    private static void readConfig() {
        // Handle reading from the config file, and from the file create the rules and assign them to objects.
        // This needs to be handled here, b/c if handled in the connection object, that would mean that each connection
        // could have a different set of rules.

        // Read from file to
        // Parse each rule from one another
        // Create a retry rule object from each parsed rule.
    }
}
