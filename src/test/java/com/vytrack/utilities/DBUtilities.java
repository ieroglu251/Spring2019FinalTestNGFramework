package com.vytrack.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtilities {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void establishConnection(DBType dbType) throws SQLException {
        switch (dbType) {
            case ORACLE:
                connection = DriverManager.getConnection(ConfigurationReader.getProperty("oracledb.url"),
                        ConfigurationReader.getProperty("oracledb.user"),
                        ConfigurationReader.getProperty("oracledb.password"));

                break;
            //if we mysql, or others can be add
            default:
                connection = null;

        }


    }
    public static List<Map<String, Object>> runSQLQuery(String sql) throws SQLException {
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(sql);
        //Throw resultset into a list of map
        //create a list lf map
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData rsMdata = resultSet.getMetaData();
        int colCount = rsMdata.getColumnCount();

        while(resultSet.next()){
            Map<String, Object> rowMap = new HashMap<>();
            for (int col = 1; col <= colCount; col++){
                rowMap.put(rsMdata.getCatalogName(col), resultSet.getObject(col));
            }

            list.add(rowMap);
        }

        return list;
    }
}
    enum DBType {
        ORACLE, MYSQL, MARIADB
    }

