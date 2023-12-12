package com.microsoft.sqlserver.jdbc;

import java.util.ArrayList;

public class ConfigRetryRule {
    // Created for each retry rule
    // Each object will have information for that error code on its retry interval, duration, count, and
    // specific queries to apply retry logic to. This is the same for connection and statement retry?

    // If we are reading statement retry, all the information needs to be included in the config file or connection string
    // If we are doing connection retry, we can omit information, as long as it's included in other connection properties.
    // Even then, we can assume defaults.

    private String retryError;

    private int retryInterval; // In ms?

    private int retryDuration; // In seconds

    private int retryCount;

    private ArrayList<String> retryQueries = new ArrayList<>();

    private boolean isConnection = true;

    public ConfigRetryRule(String s) {
        // Each retry rule will come from the line of information being passed in.
        // We'll have to parse s to get the information we want.

        String[] stArr = parse(s);

        // We need some way to distinguish between connection and statement rules, maybe each rule is prefaced with conn for connection
        // Add the elements from the parsed string to the object

        addElements(stArr);

        // Inside the above we'll also need to address any blanks
        // Need someway to identify the connection rules from the statement rules, and if connection rules are missing
        // either fill with the defined connection string options, OR use the defaults.
        // In the case of statement, if they are missing, use defaults
        // Once this is done, we have created the rule, we need an easy way to find the rule so, it can be executed.
    }

    private String[] parse(String s) {
        String temp = s;

        // Replace { with nothing, and }, with nothing, and then } with nothing.

        if (temp.charAt(temp.length() - 1) == ',') {
            temp = temp.substring(0,temp.length() - 1);
        }

        temp = temp.replace('{',' ');
        temp = temp.replace('}', ' ');
        temp = temp.trim();

        // Split based on semicolon

        String[] st = temp.split(";");

        return st;
    }

    private int addElements(String[] s) {
        // At this point we have an array of property=value
        // We can't assume the order is correct, so we go through each and do a match to above property names.

        for (String st : s) {

        }
        return 0;
    }

    public String getError() {
        return retryError;
    }
}