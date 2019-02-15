/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 6000);
            serverRefrence = (ServerInterface) registry.lookup("chat");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
     }
    
}
