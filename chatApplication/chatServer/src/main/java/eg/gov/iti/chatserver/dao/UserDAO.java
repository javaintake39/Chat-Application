/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.dao;

import eg.gov.iti.chatcommon.model.User;
import java.util.List;

/**
 *
 * @author ghazallah
 */
public interface UserDAO {
    public List<User> getAllUsers ();
    public void registerNewUser (User user);
    public boolean signIn (User user);
    public void updateUser (User user);
    public User getUser(String phone);
    //public void addUsers (List<User> user);
    
  
    public int countOnlineUsers ();
    public int countOfflineUsers();
    public int countMales ();
    
    public int countFemales ();
    
}
