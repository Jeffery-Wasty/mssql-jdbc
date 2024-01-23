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
    private final static int timeToRead = 30000; //In ms
    private final static String defaultName = "mssql-jdbc.properties";
    private static ConfigRead single_instance = null;
    private static long timeRead;
    private static String lastQuery = "";
    private static long timeLastModified;
    private static String customRetryRules = "";
    private static String customLocation = ""; //Only set if passed in to connection string.
    private static boolean replaceFlag; // Are we replacing the list of transient errors?
    private static HashMap<Integer,ConfigRetryRule> cxnRules = new HashMap<>();
    private static final HashMap<Integer,ConfigRetryRule> stmtRules = new HashMap<>();

    private ConfigRead() {
        timeRead = new Date().getTime();
        readConfig();
    }

    public static synchronized ConfigRead getInstance() {
        if (single_instance == null) {
            single_instance = new ConfigRead();
        } else {
            reread();
        }

        return single_instance;
    }

    public void setCustomLocation(String cL) {
        if (!customLocation.isEmpty()) {
            customLocation = cL;
            readConfig();
        }
    }

    public void setCustomRetryRules(String cRR) {
        customRetryRules = cRR;
    }

    private static void reread() {
        long currentTime = new Date().getTime();

        if ((currentTime - timeRead) >= timeToRead && !compareModified()) {
            timeRead = currentTime;
            readConfig();
        }
    }

    public void storeLastQuery(String sql) {
        lastQuery = sql;
    }

    public String getLastQuery() {
        return lastQuery;
    }

    private static void readConfig() {
        LinkedList<String> temp = null;

        try {
            if (!customLocation.isEmpty()) {
                temp = readFromFile(customLocation, ""); //TODO input validation
            } else {
                temp = readFromFile(null, defaultName);
            }
        } catch (IOException e) {
            temp = new LinkedList<>();
            if (!customRetryRules.isEmpty()) {
                for (String s : customRetryRules.split("]")) {
                    temp.add(s);
                }
            }
        }

        if (temp != null) {
            createRules(temp);
        }
    }

    private static void createRules(LinkedList<String> list) {
        for (String temp : list) {

            ConfigRetryRule rule = new ConfigRetryRule(temp);
            if (rule.getError().contains(",")) {

                String[] arr = rule.getError().split(",");

                for (String s : arr) {
                    ConfigRetryRule rulez = new ConfigRetryRule(s, rule);
                    if (rule.getConnectionStatus()) {
                        if (rule.getReplaceExisting()) {
                            cxnRules = new HashMap<>();
                            replaceFlag = true;
                        }
                        cxnRules.put(Integer.parseInt(rulez.getError()), rulez);
                    } else {
                        stmtRules.put(Integer.parseInt(rulez.getError()), rulez);
                    }
                }
            } else {
                if (rule.getConnectionStatus()) {
                    if (rule.getReplaceExisting()) {
                        cxnRules = new HashMap<>();
                        replaceFlag = true;
                    }
                    cxnRules.put(Integer.parseInt(rule.getError()), rule);
                } else {
                    stmtRules.put(Integer.parseInt(rule.getError()), rule);
                }
            }
        }
    }

    private static String getCurrentClassPath() {
        try {
            String className = new Object() {}.getClass().getEnclosingClass().getName();
            String location = Class.forName(className).getProtectionDomain().getCodeSource().getLocation().getPath();
            URI uri = new URI(location + "/");
            return uri.getPath();
        } catch (Exception e) {
            //TODO handle exception
        }
        return null;
    }

    private static boolean compareModified() {
        String inputToUse;
        if (!customLocation.isEmpty()) {
            inputToUse = customLocation;
        } else {
            inputToUse = getCurrentClassPath() + defaultName;
        }

        try {
            File f = new File(inputToUse);
            return f.lastModified() == timeLastModified;
        } catch (Exception e) {
            return false;
        }
    }

    private static LinkedList<String> readFromFile(String filePath, String inputFile) throws IOException {
        if (filePath == null) {
            filePath = getCurrentClassPath();
        }

        LinkedList<String> list = new LinkedList<>();
        try {
            File f = new File(filePath + inputFile);
            timeLastModified = f.lastModified();
            try (BufferedReader buffer = new BufferedReader(new FileReader(f))) {
                String readLine = "";

                while ((readLine = buffer.readLine()) != null) {
                    list.add(readLine);
                }
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return list;
    }

    public ConfigRetryRule searchRuleSet(int ruleToSearch, String ruleSet) {
        reread();
        if (ruleSet.equals("statement")) {
            for (Map.Entry<Integer, ConfigRetryRule> entry : stmtRules.entrySet()) {
                if (entry.getKey() == ruleToSearch) {
                    return entry.getValue();
                }
            }
        } else {
            for (Map.Entry<Integer, ConfigRetryRule> entry : cxnRules.entrySet()) {
                if (entry.getKey() == ruleToSearch) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    public boolean getReplaceFlag() {
        return replaceFlag;
    }
}
