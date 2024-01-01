/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBClasses;

import Data.Designation;
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

    public Vector loadDesignations(){
        String sql = "SELECT designation, basic_salary from designation";
        Designation d = new Designation();
        
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
    
    public boolean insertIntoDesignation(String des, int sal) {
        String sql = "SELECT save_designation('" + des + "', '" + sal + "')";
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
    
    public boolean insertIntoAllowance(String altype, int amount) {
        String sql = "SELECT save_allowance('" + altype + "', '" + amount + "')";
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
    
    public boolean deleteFromDesignation(int id) {
        String sql = "SELECT delete_designation('" + id + "')";
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
    
    public boolean updateDesignation(int id, int salary) {
        String sql = "SELECT update_designation("+id+",'" + salary + "')";
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
    
    public boolean deleteFromAlloawnce(int id) {
        String sql = "SELECT delete_allowance('" + id + "')";
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
    
    public boolean updateDEmployee(int empn, String fname, String lname, String gen, String dob, String nic, int tel, int desig, String doc, int sal, String offday) {
        String sql = "SELECT update_employee("+empn+",'" + fname + "', '" + lname + "', '" + gen + "', '" + dob + "', '" + nic + "', " + tel + ", '" + desig + "','" + doc + "', " + sal + ", '"+offday+"')";
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
    
    public boolean insertIntoUser(String uname, String pass, int role) {
        String sql = "SELECT save_user('" + uname + "', '" + pass + "', '"+role+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
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

    public boolean loging(String userName, String password, int UserType) {
        if (!userName.equals("")) {
            if (!password.equals("")) {
                String sql = "";
                if (UserType == 0) {
                    sql = "select id,password from users where role=0 and user_name=?";
                } else if (UserType == 1) {
                    sql = "select id,password from users where role=1 and user_name=?";
                }
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, userName);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        String id = rs.getString("id");
                        String HashPwd = rs.getString("password");
                        rs.close();
                        pst.close();
                        if (PasswordHash.validatePassword(password, HashPwd)) {

                            return true;

                        } else {
                            JOptionPane.showMessageDialog(null, "Username or password incorrect", "Invallid login", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or password incorrect", "Invallid login", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please insert valid password");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please insert valid username");
        }

        return false;
    }
    
    

}
