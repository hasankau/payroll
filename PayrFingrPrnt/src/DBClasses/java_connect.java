package DBClasses;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GF274
 */

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
public class java_connect {

    Connection conn=null;
    private static java_connect instance = new java_connect();
    
    private java_connect(){
       
        conn=ConnecrDb();
    }
    
    public static java_connect getInstance( ) {
      return instance;
   }
    
    public Connection DBConnection(){
        return conn;
    }
    public static Connection ConnecrDb(){
       
        try{     
                    File file = new File("src/host.inf");
                    Scanner sc = new Scanner(file);
                    String ab=sc.next();
                    File file2 = new File("src/data.inf");
                    Scanner sc2 = new Scanner(file2);
                    String ab2=sc2.next();
                    
                    Class.forName("org.postgresql.Driver");
                    Connection conn;
            conn = DriverManager.getConnection("jdbc:postgresql://"+ab+":5432/"+ab2+"",
            "postgres", "97_Data_4");
                    
                    return conn; 
        }
        
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Database connection error!"+e,"Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
