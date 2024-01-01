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
public class DB_query_payroll {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DB_query_payroll() {
        java_connect c = java_connect.getInstance();
        conn = c.DBConnection();
    }

    public Vector loadDesignations(){
        String sql = "SELECT designation from designation";
        Vector v = new Vector();
        try {
            
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {                
                v.add(rs.getString(1));
            }
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

        return v;
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
    


    public boolean insertIntoDEmployee(String fname, String lname, String gen, String dob, String nic, int tel, int desig, String doc, int sal, String off_day) {
        String sql = "SELECT save_employee('" + fname + "', '" + lname + "', '" + gen + "', '" + dob + "', '" + nic + "', " + tel + ", '" + desig + "','" + doc + "', " + sal + ", '"+off_day+"')";
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
