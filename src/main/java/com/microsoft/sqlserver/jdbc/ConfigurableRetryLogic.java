package com.microsoft.sqlserver.jdbc;

import java.util.HashMap;

public class ConfigurableRetryLogic {
    // This is the "retry logic provider" from the design doc
    // Takes in retry objects, and executes them based on their structure
    // This is created on a per-connection basis



    public ConfigurableRetryLogic() {
        // Created for each connection
        // Must have a list of connection-based retry rules, as well as statement-based retry rules.
        // which must be created early on.
        // On connection failures, in addition to the existing set of errors in login() and the transient rules.
        // The connection rules are referenced here, with the rules being followed if conditions are met.
        // For the list of statement rules, they are referenced on error returned from statment execution (of course)
        // Quick lookup is best, so hash maps, with the key being the error.
    }
}
