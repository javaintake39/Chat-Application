/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghazallah
 */
public class DatabseConnection {
    private static Connection connection;
    
    static
    {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/chatapplication","admin","admin");
          //  System.out.println ("connected");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
      
    }
      public static Connection getConnecion (){
            return connection;
        }
        
    
        
    
}
