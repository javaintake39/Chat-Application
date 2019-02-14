/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.server;

import eg.gov.iti.chatcommon.model.User;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghazallah
 */
public class Server {

    public static void main(String[] args) {
        try {
            RegisterationImplementation server = new RegisterationImplementation();
            Registry chatRegistry = LocateRegistry.createRegistry(9800);
            chatRegistry.rebind("chatService", server);
            System.out.println("Server binding");
           

        } catch (RemoteException ex) {
            ex.printStackTrace();

        }

    }
}
