package com.microsoft.sqlserver.jdbc;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConfigRetryRule {
    // Created for each retry rule
    // Each object will have information for that error code on its retry interval, duration, count, and
    // specific queries to apply retry logic to. This is the same for connection and statement retry?

    // If we are reading statement retry, all the information needs to be included in the config file or connection string
    // If we are doing connection retry, we can omit information, as long as it's included in other connection properties.
    // Even then, we can assume defaults.

    private String retryError;

    private String operand;

    private int initialRetryTime; // In ms?

    private int retryChange; // In seconds

    private int retryCount;

    private ArrayList<String> retryQueries = new ArrayList<>();

    private ArrayList<Integer> waitTimes = new ArrayList<>();

    private boolean isConnection = false;

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

        // Calculate wait times
        calcWaitTime();
    }

    // If we have a rule with multiple errors as the error code, its send back in and separated.
    public ConfigRetryRule(String rule, ConfigRetryRule r) {
        copyFromCopy(r);
        this.retryError = rule;
    }

    private void copyFromCopy(ConfigRetryRule r) {
        this.retryError = r.getError();
        this.operand = r.getOperand();
        this.initialRetryTime = r.getInitialRetryTime();
        this.retryChange = r.getRetryChange();
        this.retryCount = r.getRetryCount();
        this.retryQueries = r.getRetryQueries();
        this.waitTimes = r.getWaitTimes();
        this.isConnection = r.getConnectionStatus();
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

    private boolean verifyError(String error) {
        // Verify if the errors are correct
        return true;
    }

    private String[] parseErrorList(String error) {
        return error.split(",");
    }

    private int addElements(String[] s) {
        // At this point we have an array of property=value
        // We can't assume the order is correct, so we go through each and do a match to above property names.

        // First we need to look at the error list since this is the only property that changes the number of rules,
        // i.e. a rule needs to be created for each error per line in the file, not just per line.



        for (String st : s) {
            int index = st.indexOf('=');
            String first = st.substring(0,index);
            String second = st.substring(index + 1);
            try {
                switch (first) {
                    case "error":
                        // If there are multiple errors, take only the first, and send the rest to be created in their own rules.
                        retryError = second;
                        break;
                    case "retryCount":
                        retryCount = Integer.parseInt(second);
                        break;
                    case "initialRetryTime":
                        initialRetryTime = Integer.parseInt(second);
                        break;
                    case "operand":
                        operand = second;
                        break;
                    case "retryChange":
                        retryChange = Integer.parseInt(second);
                        break;
                    case "retryQuery":
                        //retryQueries = second;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid value");
            }
        }
        return 0;
    }

    public void calcWaitTime() {
        for (int i = 0; i < retryCount; ++i) {
            int waitTime = initialRetryTime;
            if (operand.equals("+")) {
                for (int j = 0; j < i; ++j) {
                    waitTime += retryChange;
                }
            } else if (operand.equals("*")) {
                for (int k = 0; k < i; ++k) {
                    waitTime *= retryChange;
                }

            }
            waitTimes.add(waitTime);
        }
    }

    public String getError() {
        return retryError;
    }

    public String getOperand() {
        return operand;
    }

    public int getInitialRetryTime() {
        return initialRetryTime;
    }

    public int getRetryChange() {
        return retryChange;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public boolean getConnectionStatus() {
        return isConnection;
    }

    public ArrayList<String> getRetryQueries() {
        return retryQueries;
    }

    public ArrayList<Integer> getWaitTimes() {
        return waitTimes;
    }
}