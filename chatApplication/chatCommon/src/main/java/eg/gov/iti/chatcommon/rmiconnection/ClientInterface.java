/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatcommon.rmiconnection;

import eg.gov.iti.chatcommon.model.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author pc
 */
public interface ClientInterface extends Remote{
    public void receive(String Message) throws RemoteException;
    public void recieveAnnouncement (String message) throws RemoteException;
    public void sendStopAnnouncement () throws RemoteException;
    public void loginNotification (User user) throws RemoteException;
    public void logoutNotification (User user) throws RemoteException;
    
    
}
