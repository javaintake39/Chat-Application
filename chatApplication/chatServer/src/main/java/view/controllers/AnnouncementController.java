/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author ghazallah
 */
public class AnnouncementController implements Initializable {

    @FXML
    private JFXTextArea messageArea;
    @FXML
    private JFXButton announceButton;
    ClientInterface clientService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void setOnAnnouncementClicked(ActionEvent event) {
        
          Platform.runLater (()->{
               SettingsController.server.clientsMap.forEach((phoneNumber, onlineClients) -> {

                System.out.println(onlineClients);
                String text = messageArea.getText();
                System.out.println(text);
                try {
                    onlineClients.recieveAnnouncement(text);
                } catch (RemoteException ex) {
                    Logger.getLogger(AnnouncementController.class.getName()).log(Level.SEVERE, null, ex);
                }

                messageArea.clear();

            });
              
          });
           
        
    }
}
    
