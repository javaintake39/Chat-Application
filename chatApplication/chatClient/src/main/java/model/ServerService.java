/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelrhman
 */
public class ServerService {

    ServerInterface serverRefrence;
    User user;

    public ServerService() {
        try {

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9800);
            serverRefrence = (ServerInterface) registry.lookup("chatService");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
    }
       public void register(ClientService clientService) {
        try {
            
            serverRefrence.loginIn(user);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
