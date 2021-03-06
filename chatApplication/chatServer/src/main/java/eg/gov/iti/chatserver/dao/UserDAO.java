/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.dao;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.model.UserStatusDTO;
import java.util.List;

/**
 *
 * @author ghazallah
 */
public interface UserDAO {
  
    public void registerNewUser (User user);
    public void updateUser (User user);
    public User getUser(String phone);
    public User signIn(User user);
    //public void addUsers (List<User> user);
    public List<User> getUserFriends(String phone) ;

    public void setStatus (UserStatusDTO user);
    //Add friend functions
    
    
    public String getName (String phoneNumber);
    public void sendInvitation(List<String> contacts,String senderPhone);  
    public List<User> viewInvitation(String reciverPhone); 
    public void AcceptInvitation(String reciverPhone,String senderPhone);
    public void RejectInvitation(String reciverPhone,String senderPhone);
  

}
