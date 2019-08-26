package com.vytrack.JDBC;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBConnection {

    String oracledburl = "jdbc:oracle:thin:@3.82.100.34:1521:xe";
    String oracledbusername = "hr";
    String oracledbpassword = "hr";
    @Test(enabled = false)
    public void oracleJDBC() throws SQLException {
        Connection connection = DriverManager.getConnection(oracledburl,oracledbusername,oracledbpassword);
//         Statement statement = connection.createStatement();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from countries");

        //to find out how many rows
        resultSet.last();
        int rowsCount = resultSet.getRow();
        System.out.println("row numbers: "+ rowsCount );

        resultSet.beforeFirst();
        while(resultSet.next()) {
            System.out.println(resultSet.getString("country_id") + "-" + resultSet.getString("country_name") + "-" + resultSet.getString("region_id"));
        }



        resultSet.close();
        statement.close();
        connection.close();

    }
    @Test
    public void jdbcMetaData() throws SQLException {
        Connection connection = DriverManager.getConnection(oracledburl,oracledbusername,oracledbpassword);
//         Statement statement = connection.createStatement();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "select employee_id, last_name, Job_id, salary from employees";
        ResultSet resultSet = statement.executeQuery(sql);

        //Database metadata
        DatabaseMetaData dbMetadata = connection.getMetaData();
        System.out.println(  "user: " + dbMetadata.getUserName()  );
        System.out.println(   "Database type: " + dbMetadata.getDatabaseProductName() );

        //resultSet metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        System.out.println(  "Columns count: " + rsMetadata.getColumnCount()  );
        System.out.println( "Column name: " + rsMetadata.getColumnName(1));

        for(int i = 1; i <= rsMetadata.getColumnCount(); i++){
            System.out.println(  i+ " -> " + rsMetadata.getColumnName(i));
        }

        /*
        if we want to store list in array list
        can use List<List>>, or List<Object[]>, or List<Map<String, Object>>

         */
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


        resultSet.close();
        statement.close();
        connection.close();
    }
}
