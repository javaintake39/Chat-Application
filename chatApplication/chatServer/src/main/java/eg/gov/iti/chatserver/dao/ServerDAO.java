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
public interface ServerDAO {

    public int countOnlineUsers();

    public int countOfflineUsers();

    public int countMales();

    public int countFemales();

    public List<User> getAllUsers();

    public int countUsersCountry(String couuntryName);
    
    //Add friend function
    
     public  List<String> getAllContactsNumber();
    public List<String> getSendedInvitations(String sender);
    
    

}
