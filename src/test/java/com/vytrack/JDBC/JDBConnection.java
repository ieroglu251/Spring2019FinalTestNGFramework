package com.vytrack.JDBC;

import org.testng.annotations.Test;

import java.sql.*;

public class JDBConnection {

    String oracledburl = "jdbc:oracle:thin:@3.82.100.34:1521:xe";
    String oracledbusername = "hr";
    String oracledbpassword = "hr";
    @Test
    public void oracleJDBC() throws SQLException {
        Connection connection = DriverManager.getConnection(oracledburl,oracledbusername,oracledbpassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from countries");

        System.out.println(  resultSet.getString("country_name"));

        resultSet.close();
        statement.close();
        connection.close();

    }
}
