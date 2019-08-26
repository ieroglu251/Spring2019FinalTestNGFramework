package com.vytrack.JDBC;

import com.vytrack.utilities.DBType;
import com.vytrack.utilities.DBUtilities;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class EmployeesDbTest {
    @Test
    public void countTest() throws SQLException {
        // connect to oracle database
        //run folliwing sql query
        //select * from employees where job_id = 'IT_PROG'
        DBUtilities.establishConnection(DBType.ORACLE);
        int rowsCount = DBUtilities.getRowsCount("select * from employees where job_id ='it_prog'");
        assertTrue(rowsCount>0);
        DBUtilities.closeConnections();;
    }
    @Test
    public void nameTestByID() throws SQLException {
        //connect to oracle database
        //employees
        DBUtilities.establishConnection(DBType.ORACLE);
        List<Map<String, Object>> empData = DBUtilities.runSQLQuery("select first_Name from employees where employee_id = 105 ");

        assertEquals(empData.get(0).get("first_name"), "David");
        assertEquals(empData.get(0).get("Last_Name"), "austin");

        DBUtilities.closeConnections();

    }
}
