/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import eg.gov.iti.chatcommon.model.Message;
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
    public User getUser (String phoneNumber){
        User user=null;
        try {
            user=serverRefrence.getUser(phoneNumber);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
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
    public void sendMessage(Message message) {
        try {
            serverRefrence.sendMessage(message);
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
    
    //check if phone the chatter want to add exist on DB
    //last Update
    public boolean checkContactExistance(String phone){
        List<String> contacts=new ArrayList<String>();
        boolean flag=false;
        try {
            contacts=serverRefrence.getAllContactsNumber();
            for(String contact:contacts){
                System.out.println(contact);
                if(contact.equals(phone)){
                    flag=true;
                    
                }
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return flag;
    }
    public void SendInvitation (List<String> contacts,String senderPhone){
        try {
            serverRefrence.SendInvitation(contacts, senderPhone);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    public void registerNewUser (User user){
        try {
            serverRefrence.registerNewUser(user);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public List<User> viewInvitation(String reciverPhone){
    List<User> inviting=new ArrayList<User>();
        try {
            inviting=serverRefrence.viewInvitation(reciverPhone);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return inviting;
    }
    public void AcceptInvitation(String reciverPhone, String senderPhone){
        try {
            serverRefrence.AcceptInvitation(reciverPhone, senderPhone);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    public void RejectInvitation(String reciverPhone, String senderPhone){
        try {
            serverRefrence.RejectInvitation(reciverPhone, senderPhone);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    
    }

    
    

}
