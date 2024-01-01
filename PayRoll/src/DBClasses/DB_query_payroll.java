/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBClasses;

import Data.Employee;
import Data.OT;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author HASANKA
 */
public class DB_query_payroll {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DB_query_payroll() {
        java_connect c = java_connect.getInstance();
        conn = c.DBConnection();
    }

    public OT getOvertime(int empno, String intime, String outtime) {
        String sql = "SELECT sum(ot_normal_hours::double precision), sum(ot_double_hours::double precision)  FROM overtime where id in\n"
                + "(select id from employee_attendance where (intime::DATE, outtime::DATE) \n"
                + "OVERLAPS('" + intime + "'::DATE, '" + outtime + "'::DATE)and emp_no =" + empno + ")";

        OT ot = new OT();
        try {

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (!rs.wasNull()) {
                    BigDecimal not = new BigDecimal(rs.getString(1));
                    ot.setNormalOt(not);
                    BigDecimal dot = new BigDecimal(rs.getString(2));
                    ot.setDoubleOt(dot);
                } else {
                    BigDecimal not = new BigDecimal("0.0");
                    ot.setNormalOt(not);
                    BigDecimal dot = new BigDecimal("0.0");
                    ot.setDoubleOt(dot);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occured! hdgh" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception ex) {
            }
        }

        return ot;
    }
    
    public boolean insertIntoSalaryRecord(int id, String month, String salary) {
        String sql = "SELECT save_salary_record("+id+",'"+month+"'" + salary + "')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }

}
