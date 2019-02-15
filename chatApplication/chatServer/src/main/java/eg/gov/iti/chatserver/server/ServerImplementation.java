/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.server;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatserver.dao.UserDAO;
import eg.gov.iti.chatserver.daoImplementation.UserDAOImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;

/**
 *
 * @author ghazallah
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{
    private UserDAO userDAO;
    public ServerImplementation() throws RemoteException {
        userDAO = new UserDAOImplementation();
    }

    
    @Override
    public void registerNewUser(User user) throws RemoteException {
      
        userDAO.registerNewUser(user);
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        userDAO.updateUser(user);
    }


    
    @Override
    public User getUser(String phoneNumber) throws RemoteException {
        return userDAO.getUser(phoneNumber);
    }

    @Override
    public List<User> getUserFriends(User user) throws RemoteException {
       List<User> friends=new ArrayList<User>(); 
        friends=userDAO.getUserFriends(user.getPhoneNumber());
        return friends;
    }

    @Override
    public void sendMessage(String phone, String messageContent) throws RemoteException {
                
    }

    @Override
    public List<User> loginIn(User user) throws RemoteException {
        List<User> friends=new ArrayList<User>(); 
        friends=getUserFriends(user);
        return friends;
    }
    
}
