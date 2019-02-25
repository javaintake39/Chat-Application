/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.daoImplementation;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatserver.dao.ServerDAO;
import eg.gov.iti.chatserver.database.DatabseConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghazallah
 */
public class ServerDAOImplementation implements ServerDAO{
     @Override
    public int countOnlineUsers() {
        int numOfOnlineUsers=0;
        Connection connection=DatabseConnection.getConnecion();
        String query="Select * from User ";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            ResultSet result= statement.executeQuery();
            while(result.next())
            {
                if(result.getInt("StatusId")==1 ||result.getInt("StatusId")==2 || result.getInt("StatusId")==3)
                {
                    numOfOnlineUsers++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numOfOnlineUsers;
    }
                    // Number Of Offline Users
    @Override
    public int countOfflineUsers() {
        int numOfOflineUsers=0;
        Connection connection=DatabseConnection.getConnecion();
        String query="Select * from User ";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            ResultSet result= statement.executeQuery();
            while(result.next())
            {
                if(result.getInt("StatusId")==4)
                {
                    numOfOflineUsers++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numOfOflineUsers;
    }
    
                    // Numeber of Males Users
    @Override
    public int countMales() {
        int maleCount = 0;
       Connection connection= DatabseConnection.getConnecion();
       String sql = "SELECT count(*) FROM User WHERE Gender ='male'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet  result = statement.executeQuery();
            result.next();
            maleCount = result.getInt(1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
       return maleCount;
    }
    
                    // Numeber of Females Users
    @Override
    public int countFemales() {
        int femaleCount = 0;
       Connection connection= DatabseConnection.getConnecion();
       String sql = "SELECT count(*) FROM User WHERE Gender ='female'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet  result = statement.executeQuery();
            result.next();
            femaleCount = result.getInt(1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
       return femaleCount;
    }
    
                    //get all users 
 @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();       
        String sql = "select * from User";
        Connection connection = null;
        try {
            connection = DatabseConnection.getConnecion();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                User user  = new User ();
                user.setPhoneNumber(result.getString("phone"));
                user.setName(result.getString("name"));
                user.setPassword(result.getString("password"));
                user.setGender(result.getString("gender"));
                user.setBio(result.getString("bio"));
                Blob blob = result.getBlob("picture");
                byte [] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setBirthDate(result.getDate("birthdate"));
                user.setEmail(result.getString("email"));
                user.setStatus_id(result.getInt("StatusId"));
                user.setCountry(result.getString("Country"));
                users.add(user); 
            }           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
                    //Number Of Users related to their country  // Ashraf
    @Override
    public int countUsersCountry(String couuntryName) {
        int usersCount = 0;
       Connection connection= DatabseConnection.getConnecion();
       String sql = "SELECT count(*) FROM User WHERE Country =?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, couuntryName);
            ResultSet  result = statement.executeQuery();
            if(result.next())
                usersCount = result.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
       return usersCount;
        
        }
    
}
