/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;



import eg.gov.iti.chatcommon.model.Message;

import eg.gov.iti.chatcommon.model.User;

import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import fxmlControllers.HomeScreenController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author pc
 */
public class ClientServices extends UnicastRemoteObject implements ClientInterface {

    private HomeScreenController controller;

    public ClientServices(HomeScreenController controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    @Override
    public void receive(Message receivedMessage) throws RemoteException {
      // controller.loginBase.textArea.appendText(receivedMessage + "\n");
       controller.recieveMessage(receivedMessage);

    @Override
    public void recieveAnnouncement(String message) throws RemoteException {
        controller.recieveAnnoucement(message);
    }

    @Override
    public void sendStopAnnouncement() throws RemoteException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loginNotification(User user) throws RemoteException {
        controller.loginNotification(user);
    }

    @Override
    public void logoutNotification(User user) throws RemoteException {
        controller.logoutNotification(user);
    }

}
