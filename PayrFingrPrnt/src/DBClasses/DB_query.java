/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBClasses;

import Data.Employee;
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
public class DB_query {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DB_query() {
        java_connect c = java_connect.getInstance();
        conn = c.DBConnection();
    }

   
    
    public boolean insertIntoEmployeeAtt(int empn, String intime, String date, String status) {
        String sql = "SELECT save_employee_attendance('" + empn + "', '" + intime + "', '" + date + "', '" + status + "')";
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
    
   
    
    public boolean updateDEmployeeAtt(int empn, String outtime, String workhours) {
        String sql = "SELECT update_employee_attendance("+empn+",'" + outtime + "', '" + workhours + "')";
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
    
    public boolean insertIntoEmployeeOt(int empn, String normot, String doubot, String date, int emp_at_ot) {
        String sql = "SELECT save_employee_ot(" + empn + ", '" + normot + "', '" + doubot + "', '" + date + "', "+emp_at_ot+")";
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
    
    public int getEmployeeAtId(int empno){
        String sql = "SELECT id from employee_attendance where emp_no="+empno+"and status = 'ON'";
        int id = 0;
        try {
            
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {  
                id = Integer.parseInt(rs.getString(1));
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occured! hdgh"+ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception ex) {
            }
        }

        return id;
    }
    
    public Employee getEmployee(int empno){
        String sql = "SELECT * from employee where emp_no="+empno;
        Employee e = new Employee();
        try {
            
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {  
                
                e.setFirstname(rs.getString(1));
                e.setLastnme(rs.getString(2));
                e.setNic(rs.getString(3));
                e.setTel(rs.getString(4));
                e.setBasicsalary(Double.parseDouble(rs.getString(5)));
                e.setDesignation(rs.getString(6));
                e.setDob(rs.getString(7));
                e.setDoc(rs.getString(8));
                e.setGender(rs.getString(9));
                e.setOffday(rs.getString(10));
                e.setEmpno(rs.getString(11));
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occured!"+ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception ex) {
            }
        }

        return e;
    }

}
