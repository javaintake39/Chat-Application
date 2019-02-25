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

/**
 *
 * @author pc
 */
public interface ClientInterface extends Remote {

    public void receive(Message message) throws RemoteException;

    public void recieveAnnouncement(String message) throws RemoteException;

    public void sendStopAnnouncement() throws RemoteException;

    public void loginNotification(User user) throws RemoteException;

    public void logoutNotification(User user) throws RemoteException;

    public void receiveFile(String filePath, byte[] fileData, int len, String directory) throws RemoteException;

    public boolean acceptFile(String senderPhone, String recieverPhone) throws RemoteException;

    public void rejectFile(String receiverPhone) throws RemoteException;

    public String getFileLocation() throws RemoteException;

    public void announceServerDown() throws RemoteException;
    public void updateFriendStatus (User user) throws RemoteException;

}
