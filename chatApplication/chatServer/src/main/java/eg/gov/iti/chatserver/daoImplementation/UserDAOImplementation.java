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
                user.setStatus_id(Integer.valueOf(result.getString("StatusId")));
                users.add(user); 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
//            try {
//               connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        return users;
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
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
//            }
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
       // fuckin bug
       String sql = "select count (*) from User where gender ='male'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet  result = statement.executeQuery();
            result.next();
            maleCount = result.getInt(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public User getUser(String phone) {
        return null;
       //TODO
    }
    /*get friends for specific user*/
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
                user.setStatus_id(Integer.valueOf(result.getString("StatusId")));
                Friends.add(user);          
            }   
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Friends;
    }

}
