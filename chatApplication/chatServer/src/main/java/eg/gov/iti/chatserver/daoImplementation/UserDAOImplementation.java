/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.daoImplementation;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatserver.dao.UserDAO;
import eg.gov.iti.chatserver.database.DatabseConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author ghazallah
 */
public class UserDAOImplementation implements UserDAO {  // last update Arafa

    
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
        }finally{
            try {
               connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return users;
    }
    //well tested 
     @Override
    public User signIn(User user) 
    {  
        String sql ="select * from User where password = ? and phone = ?";
        Connection connection=null ;
        try {
            connection= DatabseConnection.getConnecion();
            PreparedStatement statment = connection.prepareStatement(sql);
            statment.setString(1, user.getPassword());
            statment.setString(2, user.getPhoneNumber());             
            ResultSet result = statment.executeQuery();        
            while(result.next()){
            if (result!=null) {
                user.setName(result.getString("name"));
                user.setGender(result.getString("gender"));
                user.setBio(result.getString("bio"));
                Blob blob = result.getBlob("picture");
                byte [] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setBirthDate(result.getDate("birthdate"));
                user.setEmail(result.getString("email"));
                user.setStatus_id(result.getInt("StatusId"));
                user.setCountry(result.getString("Country"));
            }
            else if(result==null){
                user=null;
            }
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       /* finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }*/        
        return user;
    }
    
    @Override
    public void registerNewUser(User user) {
        Connection connection = null;
        String sql = "insert into User(phone,name,password,gender,bio,picture,birthdate,email,country,StatusId) values (?,?,?,?,?,?,?,?,?,?)";
        try {
           
            connection = DatabseConnection.getConnecion();
            PreparedStatement registerStatement = connection.prepareStatement(sql);
            registerStatement.setString(1, user.getPhoneNumber());
            registerStatement.setString(2, user.getName());
            registerStatement.setString(3, user.getPassword());
            registerStatement.setString(4, user.getGender());
            registerStatement.setString(5, user.getBio());
            Blob image = new SerialBlob(user.getPicture());
            registerStatement.setBlob(6, image);
            registerStatement.setDate(7, user.getBirthDate());
            registerStatement.setString(8, user.getEmail());
            registerStatement.setString(9, user.getCountry());
            registerStatement.setInt (10,1);
            registerStatement.executeUpdate();
        } catch (SQLException ex) {
                ex.printStackTrace();
        } finally {
//            try {
//               // connection.close();
//            } catch (SQLException ex) {
//                    ex.printStackTrace();
//            }
        }
    }
    
                        // Number Of Online Users
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

    
                    //Update User Information
    @Override
    public void updateUser(User user) {
        Connection connection = DatabseConnection.getConnecion();
        String sql="UPDATE User SET Bio="+user.getBio()+" , Name="+user.getName()
                +" , Password="+user.getPassword()+" , Gender="+user.getGender()
                +" , Email="+user.getEmail()+" , Picture="+user.getPicture()
                +" , BirthDate"+user.getBirthDate()
                +" , StatusId="+user.getStatus_id()
                +" , Country="+user.getCountry()
                +" WHERE PhoneNumber= "+user.getPhoneNumber();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //tested
    @Override
    public User getUser(String phone) {
        Connection connection = null;
        User user = null;
        try {
            connection = DatabseConnection.getConnecion();
            String sql = "SELECT phone,name,password,gender,bio,birthdate,email,StatusId,country,picture FROM User WHERE phone =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            ResultSet result = statement.executeQuery();
            user = new User();
            while(result.next()){
                user.setBio(result.getString("bio"));
                user.setBirthDate(result.getDate("birthdate"));
                user.setEmail(result.getString("email"));
                user.setGender(result.getString("gender"));
                user.setName(result.getString("name"));
                user.setPassword(result.getString("password"));
                user.setPhoneNumber(result.getString("phone"));
                Blob blob = result.getBlob("picture");
                byte [] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setStatus_id(result.getInt("StatusId"));
                user.setCountry(result.getString("country"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }
    /*get friends for specific user*/
    //well tested 
    public List<User> getUserFriends(String phone) {
        Connection connection = null;
        List<User> Friends = new ArrayList<User>();    
        try {
            String sql = "select * from friends join user on user.phone=friends.friendcontact where  friends.contact="+phone;
            connection = DatabseConnection.getConnecion();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();          
            while(result.next()){
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
                Friends.add(user);          
            }   
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Friends;
    }
}