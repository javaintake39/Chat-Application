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
public class UserDAOImplementation implements UserDAO {

    
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
    public User signIn(User user) {  
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
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }        
        return user;
    }
    
    @Override
    public void registerNewUser(User user) {
        Connection connection = null;
        String sql = "insert into User values (?,?,?,?,?,?,?,?)";
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
            registerStatement.setDate(7, (Date) user.getBirthDate());
            registerStatement.setString(8, user.getEmail());
        } catch (SQLException ex) {
                ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        }
    }
    @Override
    public int countOnlineUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countOfflineUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public int countMales() {
        int maleCount = 0;
       Connection connection= DatabseConnection.getConnecion();
       String sql = "select count(*) from User where gender ='male'";
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
    @Override
    public int countFemales() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(User user) {
        Connection connection = DatabseConnection.getConnecion();
      //  String sql = "update User set name "
      //TODO
    }
    //tested
    @Override
    public User getUser(String phone) {
        Connection connection = null;
        User user = null;
        try {
            connection = DatabseConnection.getConnecion();
            String sql = "SELECT * FROM user WHERE phone =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            ResultSet result = statement.executeQuery();
            user = new User();
            while(result.next()){
                user.setBio(result.getString("Bio"));
                user.setBirthDate(result.getDate("BirthDate"));
                user.setEmail(result.getString("Email"));
                user.setGender(result.getString("Gender"));
                user.setName(result.getString("Name"));
                user.setPassword(result.getString("Password"));
                user.setPhoneNumber(result.getString("phone"));
                Blob blob = result.getBlob("picture");
                byte [] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setStatus_id(result.getInt("StatusId"));
                user.setCountry(result.getString("Country"));
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
        List<User> Friends = new ArrayList<>();    
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
