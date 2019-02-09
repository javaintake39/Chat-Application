/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.dao;

import eg.gov.iti.chatcommon.model.User;
import java.io.File;
import java.util.List;

/**
 *
 * @author ghazallah
 */
public interface UserDAO {
    
    boolean registerNewUser(User user);
    boolean updateUser(User user);
    boolean login(User user);
    List<User> getAllFriends(User user);
    void setStatus(Integer statusValue);
    List<User> getStatus(String phoneNumber);
    List<User> getBlockedList(String phoneNumber);
    void setGroupContact(Integer groupId);
    void trasnferFile(File file);
}
