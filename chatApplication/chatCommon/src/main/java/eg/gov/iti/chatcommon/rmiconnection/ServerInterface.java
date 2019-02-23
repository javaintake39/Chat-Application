/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatcommon.rmiconnection;

import eg.gov.iti.chatcommon.model.Message;
import eg.gov.iti.chatcommon.model.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ghazallah
 */
public interface ServerInterface extends Remote{
    public void registerNewUser (User user) throws RemoteException;
    public void updateUser (User user) throws RemoteException;
    public User getUser (String phoneNumber) throws RemoteException;
    public List<User> getUserFriends(User user)  throws RemoteException;
    void sendMessage(Message message) throws RemoteException; 
    public User login(User user,ClientInterface client) throws RemoteException;    
    public void logout (User user)throws RemoteException;
    public void sendAnnouncement (String announcement) throws RemoteException;
    public void setStatus (User user) throws RemoteException;
    public boolean isMyFriend (String myPhone, String friendContact) throws RemoteException;

    
    //Add fiiend Functions
    public  List<String> getAllContactsNumber() throws RemoteException;
    public void SendInvitation(List<String> contacts,String senderPhone)throws RemoteException;
    public List<User> viewInvitation(String reciverPhone) throws RemoteException; 
    public void AcceptInvitation(String reciverPhone,String senderPhone) throws RemoteException; 

    public String getName (String phoneNumber) throws RemoteException;
    public void sendFile (String fileName,byte [] fileData,int len,String receiverPhone,String senderPhone)throws RemoteException;

}