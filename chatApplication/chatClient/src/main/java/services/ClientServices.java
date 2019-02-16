/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import control.Controller;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author pc
 */
public class ClientServices extends UnicastRemoteObject implements ClientInterface{
  
     private Controller controller;

    public ClientServices(Controller controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    @Override
    public void receive(String receivedMessage) throws RemoteException {
       controller.loginBase.textArea.appendText(receivedMessage + "\n");
       
    }
    
    
}
