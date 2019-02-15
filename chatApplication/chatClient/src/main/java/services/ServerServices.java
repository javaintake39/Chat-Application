/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
    
    public void register(User clientService) {
        try {
            serverRefrence.loginIn(clientService);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

    }

}
