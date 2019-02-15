/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.server.UnicastRemoteObject;
import eg.gov.iti.chatcommon.rmiconnection.*;
import java.rmi.RemoteException;
/**
 *
 * @author Abdelrhman
 */
public class ClientService extends UnicastRemoteObject implements ClientInterface {

    public ClientService() throws RemoteException{
    }
    

    @Override
    public void receive(String receivedMessage) throws RemoteException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
