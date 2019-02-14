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
            while (result.next()) {
                User user = new User();
                user.setPhoneNumber(result.getString("phone"));
                user.setName(result.getString("name"));
                user.setPassword(result.getString("password"));
                user.setGender(result.getString("gender"));
                user.setBio(result.getString("bio"));
                Blob blob = result.getBlob("picture");
                byte[] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setBirthDate(result.getDate("birthdate"));
                user.setEmail(result.getString("email"));
                user.setStatus_id(Integer.valueOf(result.getString("StatusId")));
                users.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        int numberOfOnlineUsers = 0;
        try {
            Connection connection = DatabseConnection.getConnecion();
            String sql = "SELECT * FROM User";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                if (result.getInt("Status_id")==1 || result.getInt("Status_id")==2 ||result.getInt("Status_id")==3 )
                {
                    numberOfOnlineUsers++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfOnlineUsers;
    }

    @Override
    public int countOfflineUsers() {
        int numberOfOfflineUsers = 0;
        try {
            Connection connection = DatabseConnection.getConnecion();
            String sql = "SELECT * FROM User";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                if (result.getInt("Status_id")==4 )
                {
                    numberOfOfflineUsers++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfOfflineUsers;
    }

    @Override
    public int countMales() {
        int maleCount = 0;
        Connection connection = DatabseConnection.getConnecion();
        // fuckin bug
        String sql = "select count (*) from User where gender ='male'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            result.next();
            maleCount = result.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return maleCount;
    }

    @Override
    public int countFemales() {
        int femaleCount = 0;
        Connection connection = DatabseConnection.getConnecion();
        // fuckin bug
        String sql = "select count (*) from User where gender ='female'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            result.next();
            femaleCount = result.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return femaleCount;
    }

    @Override
    public void updateUser(User user) {
        Connection connection = DatabseConnection.getConnecion();
        String sql="UPDATE User SET bio="+user.getBio()+" AND SET Name="+user.getName()
                +" AND SET password="+user.getPassword()+" AND SET Gender="+user.getGender()
                +" AND SET Email="+user.getEmail()+" AND SET Picture="+user.getPicture()
                +" AND SET Status_id="+user.getStatus_id()
                +" WHERE PhoneNumber="+user.getPhoneNumber();
        
    }

    @Override
    public User getUser(String phone) {

        User user = null;
        try {
            Connection connection = null;
            String sql = "SELECT * FROM User WHERE phone =" + phone;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            user = new User();
            user.setBio(result.getString("Bio"));
            user.setBirthDate(result.getDate("BirthDate"));
            user.setEmail(result.getString("Email"));
            user.setGender(result.getString("Gender"));
            user.setName(result.getString("Name"));
            user.setPassword(result.getString("Password"));
            user.setPhoneNumber(result.getString("PhoneNumber"));
            user.setPicture(result.getBytes("Picture"));
            user.setStatus_id(result.getInt("Status_id"));

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public List<User> getUserFriends(String phone) {
        Connection connection = null;
        List<User> Friends = new ArrayList<>();
        try {
            String sql = "select * from friends join user on user.phone=friends.friendcontact where  friends.contact=" + phone;
            connection = DatabseConnection.getConnecion();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setPhoneNumber(result.getString("phone"));
                user.setName(result.getString("name"));
                user.setPassword(result.getString("password"));
                user.setGender(result.getString("gender"));
                user.setBio(result.getString("bio"));
                Blob blob = result.getBlob("picture");
                byte[] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setBirthDate(result.getDate("birthdate"));
                user.setEmail(result.getString("email"));
                user.setStatus_id(Integer.valueOf(result.getString("StatusId")));
                Friends.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Friends;
    }

}
