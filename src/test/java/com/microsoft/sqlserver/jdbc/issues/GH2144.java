package com.microsoft.sqlserver.jdbc.issues;

import java.sql.*;
import java.util.*;
/**
 * Test sendStringParamsAsUnicode with test from user and from me.
 * See <a href="https://github.com/microsoft/mssql-jdbc/issues/2144">GitHub Issue #2144</a>.
 */
public class GH2144 {
    /**
     * Test sendStringParamsAsUnicode with test from user.
     */
    public static void sendStringParamsTest(String option, boolean sendStringParamsAsUnicode) throws ClassNotFoundException, SQLException {
        String runningOption;
        String loggingOption;

        int count = 0;
        int ps_average = 0;
        int st_average = 0;

        if (option.equalsIgnoreCase("e")) {
            runningOption = "SELECT ID FROM [dbo].[Effective_Test_table]";
            loggingOption = "SELECT * FROM [dbo].[Effective_Test_table]";
        } else if (option.equalsIgnoreCase("i")) {
            runningOption = "SELECT ID,AGE FROM [dbo].[Invalid_Test_table]";
            loggingOption = "SELECT * FROM [dbo].[Invalid_Test_table]";
        } else {
            runningOption = "SELECT ID,NAME,AGE FROM [dbo].[Three_PK]";
            loggingOption = "SELECT * FROM [dbo].[Three_PK]";
        }

        String connectionString;
        if (sendStringParamsAsUnicode) {
            connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;";
        } else  {
            connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;sendStringParametersAsUnicode=false";
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // 获取连接
        try(Connection connection = DriverManager.getConnection(connectionString)){
            // Sql
            String effectiveTestTablePkSql = "SELECT ID FROM [dbo].[Effective_Test_table]";
            String invalidTestTablePKSql = "SELECT ID,AGE FROM [dbo].[Invalid_Test_table]";

            // 构建查询
            preparedStatement = connection.prepareStatement(runningOption);
            resultSet = preparedStatement.executeQuery();

            List<Map<String,Object>> pkDataList = new ArrayList<>();
            int iPutIndex = 0;

            while (resultSet.next()){
                Map<String,Object> pkDataMaps = new HashMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    // 存放主键的K,V键值对
                    pkDataMaps.put(metaData.getColumnLabel(i),resultSet.getObject(i));
                }

                pkDataList.add(pkDataMaps);
                ++iPutIndex;

                // 每N行写入一次
                if (iPutIndex >= 699) {
                    List<Object> paramValueList = new ArrayList<>();

                    PreparedStatement preparedStatement2 = null;
                    Statement statement = null;
                    ResultSet resultSetForPreparedStatement = null;
                    ResultSet resultSetForStatement = null;

                    // 利用ID主键查询全量数据
                    try(Connection connection2 = DriverManager.getConnection(connectionString)){
                        // Sql
                        String effectiveTestTableFullSql = "SELECT * FROM [dbo].[Effective_Test_table]";
                        String invalidTestTableFullSql = "SELECT * FROM [dbo].[Invalid_Test_table]";

                        // 用于拼装preparedStatement SQL
                        StringJoiner or = new StringJoiner(" OR ");
                        // 用于拼装statement SQL
                        StringJoiner or2 = new StringJoiner(" OR ");
                        pkDataList.forEach(c -> {
                            StringJoiner and = new StringJoiner(" AND ", "(", ")");
                            StringJoiner and2 = new StringJoiner(" AND ", "(", ")");
                            for (Map.Entry<String, Object> k : c.entrySet()) {
                                and.add(k.getKey() + "=?");
                                and2.add(k.getKey() + "='" + k.getValue()+"'");
                                paramValueList.add(k.getValue());
                            }
                            or.add(and.toString());
                            or2.add(and2.toString());
                        });

                        /*
                          prepareStatement测试
                         */
                        String preparedStatementTestSql = loggingOption + " WHERE " + or;
                        //System.out.println("preparedStatement test Sql:"+preparedStatementTestSql);

                        preparedStatement2 = connection2.prepareStatement(preparedStatementTestSql);
                        for (int i = 1; i <= paramValueList.size(); i++) {
                            preparedStatement2.setObject(i,paramValueList.get(i-1));
                        }

                        // 计算执行时间
                        long timerNow = System.currentTimeMillis();
                        //System.out.println("开始执行preparedStatement test Sql查询");
                        resultSetForPreparedStatement = preparedStatement2.executeQuery();

                        //System.out.println("preparedStatement test Sql 查询" +countRow1+"条记录执行耗时:" +(System.currentTimeMillis() - timerNow) +"ms");
                        ps_average += (int) (System.currentTimeMillis() - timerNow);

                        /*
                          Statement测试
                         */
                        String statementTestSql = loggingOption + " WHERE " + or2;
                        //System.out.println("statement test Sql:"+statementTestSql);

                        timerNow = System.currentTimeMillis();
                        //System.out.println("开始执行statement test Sql查询");
                        statement = connection2.createStatement();
                        resultSetForStatement = statement.executeQuery(statementTestSql);

                        //System.out.println("statement test Sql 查询" +countRow2+"条记录执行耗时:" +(System.currentTimeMillis() - timerNow) +"ms");
                        st_average += (int) (System.currentTimeMillis() - timerNow);
                        count++;
                    }finally {
                        assert resultSetForStatement != null;
                        resultSetForStatement.close();
                        resultSetForPreparedStatement.close();
                        statement.close();
                        preparedStatement2.close();
                    }
                    pkDataList.clear();
                    iPutIndex = 0;
                }
            }
        } finally {
            System.out.println("preparedStatement test Sql 查询" +(ps_average / count) +"ms");
            System.out.println("statement test Sql 查询" +(st_average / count) +"ms");
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
        }
    }

    /**
     * Test sendStringParamsAsUnicode with edited test from user.
     */
    public static void sendStringParamsTestJeff(String option, boolean sendStringParamsAsUnicode) throws ClassNotFoundException, SQLException {
        String runningOption;
        String loggingOption;
        if (option.equalsIgnoreCase("1")) {
            runningOption = "SELECT ID FROM [dbo].[Jeff1]";
            loggingOption = "SELECT * FROM [dbo].[Jeff1]";
        } else if (option.equalsIgnoreCase("2")){
            runningOption = "SELECT ID,NAME FROM [dbo].[Jeff2]";
            loggingOption = "SELECT * FROM [dbo].[Jeff2]";
        } else {
            runningOption = "SELECT ID,NAME,AGE FROM [dbo].[Jeff3]";
            loggingOption = "SELECT * FROM [dbo].[Jeff3]";
        }

        String connectionString;
        if (sendStringParamsAsUnicode) {
            connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;";
        } else  {
            connectionString = "jdbc:sqlserver://localhost:1433;databaseName=TestDb;userName=sa;password=TestPassword123;encrypt=false;trustServerCertificate=true;sendStringParametersAsUnicode=false";
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // 获取连接
        try(Connection connection = DriverManager.getConnection(connectionString)){
            // Sql

            // 构建查询
            preparedStatement = connection.prepareStatement(runningOption);
            resultSet = preparedStatement.executeQuery();

            List<Map<String,Object>> pkDataList = new ArrayList<>();
            int iPutIndex = 0;

            while (resultSet.next()){
                Map<String,Object> pkDataMaps = new HashMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    // 存放主键的K,V键值对
                    pkDataMaps.put(metaData.getColumnLabel(i),resultSet.getObject(i));
                }

                pkDataList.add(pkDataMaps);
                ++iPutIndex;

                // 每N行写入一次
                if (iPutIndex >= 500) {
                    List<Object> paramValueList = new ArrayList<>();

                    PreparedStatement preparedStatement2 = null;
                    Statement statement = null;
                    ResultSet resultSetForPreparedStatement = null;
                    ResultSet resultSetForStatement = null;

                    // 利用ID主键查询全量数据
                    try(Connection connection2 = DriverManager.getConnection(connectionString)){

                        // 用于拼装preparedStatement SQL
                        StringJoiner or = new StringJoiner(" OR ");
                        // 用于拼装statement SQL
                        StringJoiner or2 = new StringJoiner(" OR ");
                        pkDataList.forEach(c -> {
                            StringJoiner and = new StringJoiner(" AND ", "(", ")");
                            StringJoiner and2 = new StringJoiner(" AND ", "(", ")");
                            for (Map.Entry<String, Object> k : c.entrySet()) {
                                and.add(k.getKey() + "=?");
                                and2.add(k.getKey() + "='" + k.getValue()+"'");
                                paramValueList.add(k.getValue());
                            }
                            or.add(and.toString());
                            or2.add(and2.toString());
                        });

                        /*
                          prepareStatement测试
                         */
                        String preparedStatementTestSql = loggingOption + " WHERE " + or;
                        System.out.println("preparedStatement test Sql:"+preparedStatementTestSql);

                        preparedStatement2 = connection2.prepareStatement(preparedStatementTestSql);
                        for (int i = 1; i <= paramValueList.size(); i++) {
                            preparedStatement2.setObject(i,paramValueList.get(i-1));
                        }

                        // 计算执行时间
                        long timerNow = System.currentTimeMillis();
                        System.out.println("开始执行preparedStatement test Sql查询");
                        resultSetForPreparedStatement = preparedStatement2.executeQuery();

                        int countRow1 = 0;
                        while (resultSetForPreparedStatement.next()){
                            countRow1++;
                        }
                        System.out.println("preparedStatement test Sql 查询" +countRow1+"条记录执行耗时:"
                                +(System.currentTimeMillis() - timerNow) +"ms");


                        /*
                          Statement测试
                         */
                        int countRow2 = 0;
                        String statementTestSql = loggingOption + " WHERE " + or2;
                        System.out.println("statement test Sql:"+statementTestSql);

                        timerNow = System.currentTimeMillis();
                        System.out.println("开始执行statement test Sql查询");
                        statement = connection2.createStatement();
                        resultSetForStatement = statement.executeQuery(statementTestSql);
                        while (resultSetForStatement.next()){
                            countRow2++;
                        }
                        System.out.println("statement test Sql 查询" +countRow2+"条记录执行耗时:"
                                +(System.currentTimeMillis() - timerNow) +"ms");
                    }finally {
                        assert resultSetForStatement != null;
                        resultSetForStatement.close();
                        resultSetForPreparedStatement.close();
                        statement.close();
                        preparedStatement2.close();
                    }

                    pkDataList.clear();
                    iPutIndex = 0;
                }
            }
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
        }
    }
}
