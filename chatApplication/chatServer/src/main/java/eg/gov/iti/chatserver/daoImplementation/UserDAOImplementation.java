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
                System.out.println(result.getString("phone"));
                user.setName(result.getString("name"));
                user.setPassword(result.getString("password"));
                System.out.println(result.getString("password"));
                user.setGender(result.getString("gender"));
                user.setBio(result.getString("bio"));
                Blob blob = result.getBlob("picture");
                byte[] image = blob.getBytes(1l, (int) blob.length());
               // user.setPicture(image);
                user.setBirthDate(result.getDate("birthdate"));
                System.out.println(result.getDate("birthdate"));
                user.setEmail(result.getString("email"));
                users.add(user);
                System.out.println(user.getBirthDate());
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
            int counter = registerStatement.executeUpdate();
            System.out.println(counter);
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
        Connection connection = DatabseConnection.getConnecion();
        String sql = "select count(*) from User where gender ='male'";
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
        String sql = "select COUNT(*) from User where gender =?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String gender = "female";
            statement.setString(1, gender);
            ResultSet result = statement.executeQuery();
            result.next();
            femaleCount = result.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return femaleCount;
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

    @Override
    public boolean signIn(User user) {
        boolean isValidInput = false;
        Connection connection = DatabseConnection.getConnecion();

        String sql = "select phone from User where password = ?";

        try {
            PreparedStatement statment = connection.prepareStatement(sql);
            statment.setString(1, user.getPassword());
            ResultSet result = statment.executeQuery();

            if (result.next()) {
                isValidInput = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isValidInput;
    }
    
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAOImplementation();
        int count = userDAO.countMales();
        System.out.println("count = " + count);
    }

   
}
