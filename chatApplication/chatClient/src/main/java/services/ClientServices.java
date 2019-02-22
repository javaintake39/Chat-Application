/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import eg.gov.iti.chatcommon.model.Message;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import fxmlControllers.HomeScreenController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.scene.control.Alert;

/**
 *
 * @author pc
 */
public class ClientServices extends UnicastRemoteObject implements ClientInterface{
  
     private HomeScreenController controller;

    public ClientServices(HomeScreenController controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    @Override
    public void receive(Message receivedMessage) throws RemoteException {
      // controller.loginBase.textArea.appendText(receivedMessage + "\n");
       controller.recieveMessage(receivedMessage);
       
    }

    @Override
    public void recieveAnnouncement(String message) throws RemoteException {
        
        //announcement from the server
//        Alert alert = new Alert (Alert.AlertType.INFORMATION);
//        alert.setContentText(message);
//        alert.showAndWait();
        controller.recieveAnnoucement(message);
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendStopAnnouncement() throws RemoteException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
    
}
