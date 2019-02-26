/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author ghazallah
 */
public class IpController implements Initializable {

    @FXML
    JFXTextField ipTextField1;
    @FXML
    JFXTextField ipTextField2;
    @FXML
    JFXTextField ipTextField3;
    @FXML
    JFXTextField ipTextField4;
    @FXML 
    private AnchorPane ipAnchorPane;

    private Registry chatRegistry = null;
    public static ServerInterface service = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void setOnConnectPressed(ActionEvent event) throws IOException {
        String ip = ipTextField1.getText() + "." + ipTextField2.getText() + "." + ipTextField3.getText() + "." + ipTextField4.getText();
        try {
            chatRegistry = LocateRegistry.getRegistry(ip, 9800);
            service = (ServerInterface) chatRegistry.lookup("chatService");
            System.out.println(service);
             FXMLLoader loader = new FXMLLoader();
            LoginController loginController = new LoginController(service);
            loader.setController(loginController);
            Parent root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
            Stage stage = (Stage) ipAnchorPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            

        } catch (RemoteException ex) {
            ex.printStackTrace();

        } catch (NotBoundException ex) {
            ex.printStackTrace();

        }
    }
}
