package com.microsoft.sqlserver.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ConfigRead {
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
    private static HashMap<Integer,ConfigRetryRule> cxnRules = new HashMap<>();
    private static HashMap<Integer,ConfigRetryRule> stmtRules = new HashMap<>();
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

        LinkedList<String> temp = readFromFile("config.txt");

        createRules(temp);

        // Read from file to
        // Parse each rule from one another
        // Create a retry rule object from each parsed rule.
    }

    private static void createRules(LinkedList<String> list) {
        for (String temp : list) {
            // temp needs to be parsed here for the error list, to see if we need to make multiple rules from a single line.
            //String[] processed = parseMultipleErrors(temp);

            ConfigRetryRule rule = new ConfigRetryRule(temp);
            if (rule.getError().contains(",")) {
                // Now we need to make a new one for each.
                // Parse the error list, get the list of errors, and make a new rule for each based off the composite.

                String[] arr = rule.getError().split(",");

                for (String s : arr) {
                    ConfigRetryRule rulez = new ConfigRetryRule(s, rule);
                    cxnRules.put(Integer.parseInt(rulez.getError()), rulez);
                }
            } else {
                cxnRules.put(Integer.parseInt(rule.getError()),rule);
            }
        }
    }

    public static String getCurrentClassPath() {
        try {
            String className = new Object() {}.getClass().getEnclosingClass().getName();
            String location = Class.forName(className).getProtectionDomain().getCodeSource().getLocation().getPath();
            URI uri = new URI(location + "/");
            return uri.getPath();
        } catch (Exception e) {
            //fail("Failed to get CSV file path. " + e.getMessage());
        }
        return null;
    }

    private static LinkedList<String> readFromFile(String inputFile) {
        String filePath = getCurrentClassPath();
        LinkedList<String> list = new LinkedList<>();
        try {
            File f = new File(filePath + inputFile);
            try (BufferedReader buffer = new BufferedReader(new FileReader(f))) {
                String readLine = "";

                while ((readLine = buffer.readLine()) != null) {
                    list.add(readLine);
                }
            }
        } catch (IOException e) {
            //fail(e.getMessage());
        }
        return list;
    }

    public static ConfigRetryRule searchRuleSet(int ruleToSearch) {
        for (Map.Entry<Integer, ConfigRetryRule> entry : cxnRules.entrySet()) {
            if (entry.getKey() == ruleToSearch) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static HashMap<Integer,ConfigRetryRule> getConnectionRules() {
        return cxnRules;
    }
}
