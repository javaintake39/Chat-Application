/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ghazallah
 */
public class LoginController implements Initializable {

    @FXML
    private TextField phoneTF;
    @FXML
    private PasswordField passwordTF;

    private ServerInterface service = null;

    /**
     * Initializes the controller class.
     */

    public LoginController() {
    }

    public LoginController(ServerInterface service) {
        this.service = service;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void setOnConnectPressed(ActionEvent event) {
       
        try {
            User user = service.getUser(phoneTF.getText());
             System.out.println(user.getPhoneNumber());
                System.out.println(user.getPassword());
            if (user !=null && user.getPassword().equals(passwordTF.getText())){
                // TODO...
                // when login goto the home page
                System.out.println("Authinticated");
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid phone number or password");
                
                alert.setTitle("Error Login");
                alert.showAndWait();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
