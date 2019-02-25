/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import com.jfoenix.controls.JFXButton;
import eg.gov.iti.chatserver.server.ServerImplementation;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author ghazallah
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXButton startButton;
    @FXML
    private JFXButton stopButton;
    private static Registry chatRegistry;
    //public static ServerImplementation server;
    public static boolean serverON = false;
    static boolean firstTimeBind = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (serverON) {
            startButton.setDisable(true);
            stopButton.setDisable(false);
        } else {
            try {
                if (firstTimeBind){
                chatRegistry = LocateRegistry.createRegistry(9800);
                firstTimeBind = false;
                }
                startButton.setDisable(false);
                stopButton.setDisable(true);
            } catch (RemoteException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void onStartClicked(ActionEvent event) {
        try {

            chatRegistry.rebind("chatService", new ServerImplementation());
            System.out.println("Server binding");

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        startButton.setDisable(true);
        stopButton.setDisable(false);
        serverON = true;

    }

    @FXML
    public void onStopClicked(ActionEvent event) {
        try {
            
           // ServerImplementation.announceServerDown();
            chatRegistry = LocateRegistry.getRegistry(9800);
            chatRegistry.unbind("chatService");
            System.out.println("Unbinding the service");
            startButton.setDisable(false);
            stopButton.setDisable(true);
            serverON = false;
            

        } catch (RemoteException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
