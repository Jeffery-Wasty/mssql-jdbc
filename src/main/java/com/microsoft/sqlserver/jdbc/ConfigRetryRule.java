package com.microsoft.sqlserver.jdbc;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConfigRetryRule {
    // Created for each retry rule
    // Each object will have information for that error code on its retry interval, duration, count, and
    // specific queries to apply retry logic to. This is the same for connection and statement retry?

    // If we are reading statement retry, all the information needs to be inlcluded in the config file or connection string
    // If we are doing connection retry, we can omit information, as long as its included in other connection properties.
    // Even then, we can assume defaults.

    private int retryInterval; // In ms?

    private int retryDuration; // In seconds

    private int retryCount;

    private ArrayList<String> retryQueries = new ArrayList<>();

    private boolean isConnection = true;

    public ConfigRetryRule(String s) {
        // Each retry rule will come from the line of information being passed in.
        // We'll have to parse s to get the information we want.

        ArrayList<String> stArr = parse(s);

        // We need some way to distinguish between connection and statement rules, maybe each rule is prefaced with conn for connection
        // Add the elements from the parsed string to the object

        addElements(stArr);

        // Inside the above we'll also need to address any blanks
        // Need someway to identify the connection rules from the statement rules, and if connection rules are missing
        // either fill with the defined connection string options, OR use the defaults.
        // In the case of statement, if they are missing, use defaults
        // Once this is done, we have created the rule, we need an easy way to find the rule so, it can be executed.
    }

    private ArrayList<String> parse(String s) {
        return new ArrayList<>();
    }

    private void addElements(ArrayList<String> s) {

    }
}