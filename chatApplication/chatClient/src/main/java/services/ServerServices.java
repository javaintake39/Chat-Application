/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import eg.gov.iti.chatcommon.model.User;

import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.io.Serializable;
import java.rmi.AccessException;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pc
 */
public class ServerServices {

    private ServerInterface serverRefrence;
    public ServerServices() {
        try {

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9800);
            serverRefrence = (ServerInterface) registry.lookup("chatService");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }

     }
    public List<User> getUserFriends(User user){
        List<User> userFriends=new ArrayList<User>();
        try {
            userFriends=serverRefrence.getUserFriends(user);       
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return userFriends;
    }
    public void sendMessage(String phone, String messageContent) {
        try {
            serverRefrence.sendMessage(phone, messageContent);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }   
    } 
    
    public User loginIn (User user,ClientServices client){
        User userData=null;
        try {
           userData =serverRefrence.login(user,client);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return userData;

    }
    

}
