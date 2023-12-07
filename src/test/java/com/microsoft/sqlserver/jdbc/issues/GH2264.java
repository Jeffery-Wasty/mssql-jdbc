package com.microsoft.sqlserver.jdbc.issues;

public class GH2264 {

//    public static void run() {
//        tDBInput_3Process();
//    }
//    private static void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws Exception {
//        java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();
//        try {
//            if(resumeIt || globalResumeTicket) { // start the resume
//
//                java.sql.Connection msSqlConnection = null;
//
//                String updateQuery = "UPDATE [aff_interv_lig] SET [int_id] = ?, ... WHERE [int_lig_id] = ?";
//                java.sql.PreparedStatement stmtUpdate = msSqlConnection.prepareStatement(updateQuery);
//
//                String insertQuery = "INSERT INTO [aff_interv_lig] ([int_lig_id], ...) VALUES (?,...)";
//                java.sql.PreparedStatement stmtInsert = msSqlConnection.prepareStatement(insertQuery);
//
//                java.sql.Connection connOracle = null;
//                String driverOracle = "oracle.jdbc.OracleDriver";
//                java.lang.Class.forName(driverOracle);
//
//                String url_tDBInput_3 = "jdbc:oracle:thin:@(description=(address=(protocol=tcp)(host=" + context.Sioux_Server + ")(port=" + context.Sioux_Port + "))(connect_data=(service_name=" + context.Sioux_ServiceName + ")))";
//                connOracle = java.sql.DriverManager.getConnection(url_tDBInput_3, atnParamsPrope_tDBInput_3);
//
//
//                java.sql.Statement stmt_tDBInput_3 = connOracle.createStatement( java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
//                stmt_tDBInput_3.setFetchSize(10000);
//                String dbquery_tDBInput_3 = "SELECT ..........";
//
//                java.sql.ResultSet rsOracle = null;
//
//                try {
//                    rsOracle = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
//                    java.sql.ResultSetMetaData rsmd_tDBInput_3 = rsOracle.getMetaData();
//                    int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();
//
//                    String tmpContent_tDBInput_3 = null;
//
//                    while(rsOracle.next()) {
//
//                        if(colQtyInRs_tDBInput_3 < 1) {
//                            sioux.int_lig_id = null;
//                        } else {
//
//                            if(rsOracle.getObject(1) != null) {
//                                sioux.int_lig_id = rsOracle.getLong(1);
//                            } else {
//
//                                sioux.int_lig_id = null;
//                            }
//                        }
//
//                        { // start of Var scope
//                            uiOds = null;
//                            // # Output table : 'uiOds'
//                            uiOds_tmp.int_lig_id = sioux.int_lig_id;
//                            uiOds = uiOds_tmp;
//                        } // end of Var scope
//
//                        // Start of branch "uiOds"
//                        if(uiOds != null) {
//
//                            int updateFlag_tDBOutput_1 = 0;
//                            try {
//                                if(uiOds.int_id == null) {
//                                    stmtUpdate.setNull(1, java.sql.Types.INTEGER);
//                                } else {
//                                    stmtUpdate.setLong(1, uiOds.int_id);
//                                }
//
//                                updateFlag_tDBOutput_1 = stmtUpdate.executeUpdate();
//                                updatedCount_tDBOutput_1 = updatedCount_tDBOutput_1 + updateFlag_tDBOutput_1;
//                                if(updateFlag_tDBOutput_1 == 0) {
//
//                                    if(uiOds.int_lig_id == null) {
//                                        stmtInsert.setNull(1, java.sql.Types.INTEGER);
//                                    } else {
//                                        stmtInsert.setLong(1, uiOds.int_lig_id);
//                                    }
//
//                                    insertedCount_tDBOutput_1 = insertedCount_tDBOutput_1
//                                            + stmtInsert.executeUpdate();
//                                    nb_line_tDBOutput_1++;
//                                } else {
//                                    nb_line_tDBOutput_1++;
//
//                                }
//                            }
//
//                        } // End of branch "uiOds"
//
//
//                    }
//                } finally {
//                    if(rsOracle != null) {
//                        rsOracle.close();
//                    }
//                    if(stmt_tDBInput_3 != null) {
//                        stmt_tDBInput_3.close();
//                    }
//                    if(connOracle != null && !connOracle.isClosed()) {
//                        connOracle.close();
//                    }
//                }
//
//                if(stmtUpdate != null) {
//                    stmtUpdate.close();
//                    resourceMap.remove("pstmtUpdate_tDBOutput_1");
//                }
//                if(stmtInsert != null) {
//                    stmtInsert.close();
//                    resourceMap.remove("pstmtInsert_tDBOutput_1");
//                }
//                resourceMap.put("statementClosed_tDBOutput_1", true);
//            } // end the resume
//
//
//        } catch(java.lang.Exception e) {
//            // ....
//            throw e;
//        } catch(java.lang.Error error) {
//            // ...
//            throw error;
//        } finally {
//            // free memory for "tAggregateRow_1_AGGIN"
//            try {
//
//                if(resourceMap.get("statementClosed_tDBOutput_1") == null) {
//                    java.sql.PreparedStatement pstmtUpdateToClose_tDBOutput_1 = null;
//                    if((pstmtUpdateToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap.remove("pstmtUpdate_tDBOutput_1")) != null) {
//                        pstmtUpdateToClose_tDBOutput_1.close();
//                    }
//                    java.sql.PreparedStatement pstmtInsertToClose_tDBOutput_1 = null;
//                    if((pstmtInsertToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap.remove("pstmtInsert_tDBOutput_1")) != null) {
//                        pstmtInsertToClose_tDBOutput_1.close();
//                    }
//                }
//
//                if(resourceMap.get("statementClosed_tDBOutput_3") == null) {
//                    java.sql.PreparedStatement pstmtUpdateToClose_tDBOutput_3 = null;
//                    if((pstmtUpdateToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap.remove("pstmtUpdate_tDBOutput_3")) != null) {
//                        pstmtUpdateToClose_tDBOutput_3.close();
//                    }
//                    java.sql.PreparedStatement pstmtInsertToClose_tDBOutput_3 = null;
//                    if((pstmtInsertToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap.remove("pstmtInsert_tDBOutput_3")) != null) {
//                        pstmtInsertToClose_tDBOutput_3.close();
//                    }
//                }
//
//                /**
//                 * [tDBOutput_3 finally ] stop
//                 */
//
//            } catch(java.lang.Exception e) {
//                // ignore
//            } catch(java.lang.Error error) {
//                // ignore
//            }
//            resourceMap = null;
//        }
//
//        globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
//    }
}
