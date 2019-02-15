/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatcommon.rmiconnection;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Abdelrhman
 */
public interface ClientInterface extends Remote {
    
     public void receive(String receivedMessage) throws RemoteException;
}
