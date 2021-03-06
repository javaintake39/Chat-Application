/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import com.jfoenix.controls.JFXButton;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ClientServices;
import services.ServerServices;

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
    @FXML
    private AnchorPane rootPaneID;
    @FXML
    private JFXButton connectButton;

    private ServerInterface service;
    private ClientServices clientService;

    private String phoneNumber;

    private ServerServices  serverServices;
        

    /**
     * Initializes the controller class.
     */
    public LoginController(ServerInterface service) {
        this.service = service;

    }
    public LoginController(ServerInterface service,String phoneNumber){
       this(service);
        this.phoneTF.setText(phoneNumber);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Properties prop = new Properties();
        InputStream userParams = null;
        String fileName = "src/main/resources/property/configFile";

         try {
             File file = new File(fileName);
             if (file.exists()){
            userParams = new FileInputStream(fileName);
             try {
            prop.load(userParams);
            String flag = prop.getProperty("flag");
            if (flag.equals("0")) {
                passwordTF.setText(prop.getProperty("Password"));
                phoneTF.setText(prop.getProperty("UserName"));
                //
            }else if (flag.equals("1")){
                phoneTF.setText(prop.getProperty("UserName"));
            }
            
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
             }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        BooleanBinding phoneBindValue = Bindings.createBooleanBinding(() -> {
            if (phoneTF.getText().equals("\\s+") || phoneTF.getText().equals("")) {
                return false;
            }
            return true;
            // check textField1.getText() and return true/false as appropriate
        }, phoneTF.textProperty());

        BooleanBinding passwordBindValue = Bindings.createBooleanBinding(() -> {
            if (passwordTF.getText().equals("\\s+") || passwordTF.getText().equals("")) {
                return false;
            }
            return true;
        }, passwordTF.textProperty());
        connectButton.disableProperty().bind(phoneBindValue.not().or(passwordBindValue.not()));

    }

    @FXML
    private void setOnConnectPressed(ActionEvent event) {
      
            

        try {
            User user = new User();
            user.setPhoneNumber(phoneTF.getText());
            user.setPassword(passwordTF.getText());

            user = service.getUser(phoneTF.getText());
            HomeScreenController controller = new HomeScreenController(user, service);
            clientService = new ClientServices(controller);

            user = service.login(user, clientService);

            if (user != null && user.getPassword().equals(passwordTF.getText())) {
                

                FXMLLoader loader = new FXMLLoader();

                loader.setController(controller);
                Parent root = loader.load(getClass().getResource("/fxml/Home.fxml").openStream());
                Scene scene = new Scene(root);
                Stage stage = (Stage) rootPaneID.getScene().getWindow();
                stage.setScene(scene);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid phone number or password");
                alert.setTitle("Error Login");
                alert.showAndWait();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void setOnSkipPressed(ActionEvent event) {
        Runtime.getRuntime().exit(0);
    }

    @FXML
    private void onRegisterPressed(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();

            RegisterationController registerationController = new RegisterationController(service);
            loader.setController(registerationController);
            Parent root = loader.load(getClass().getResource("/fxml/registeration.fxml").openStream());
            Scene scene = new Scene(root);
            //Stage stage = (Stage) rootPaneID.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
