/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ServerServices;

/*
 * @author pc
 */
public class AddContactController implements Initializable {

    private ServerServices serverServices;
    private ServerInterface Service;
    
    private User user;
    private boolean flag;
    private String phone;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ListView lvAddContact;
    @FXML
    private TextField tfAddContact;

    ObservableList<String> observable = FXCollections.observableArrayList();//create observablelist for listview

    //Method use to handle button press that submits the 1st user's text to the listview.
   
    public AddContactController(User user,ServerInterface service){
        serverServices=new ServerServices();
        this.user=user;
        this.Service=service;
    }
    
    @FXML
    private void handleAddSubmit(ActionEvent event) {
        //first Button
        flag=serverServices.checkContactExistance(tfAddContact.getText());
        if (flag) {
            phone=tfAddContact.getText();
            observable.add(tfAddContact.getText());//get 1st user's text from his/her textfield and add message to observablelist
            tfAddContact.setText("");//clear 1st user's textfield            
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Add Contact");
            alert.setHeaderText(null);
            alert.setContentText("This Contact don't have account on the Appliction");
            alert.showAndWait();
            tfAddContact.setText("");
        }
    }

    @FXML
    private void handleAddAllSubmit(ActionEvent event){
        List<String> invitedContacts=new ArrayList<String>();
        invitedContacts.add(phone);
        serverServices.SendInvitation(invitedContacts,user.getPhoneNumber());
        try {    
            HomeScreenController controller = new HomeScreenController(user,Service);
            FXMLLoader loader = new FXMLLoader();
            loader.setController(controller);
            Parent root = loader.load(getClass().getResource("/fxml/HomeScreen.fxml").openStream());
            Scene scene = new Scene(root);
            Stage stage = (Stage) AnchorPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(AddContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvAddContact.setItems(observable);//attach the observablelist to the listview
    } 

}
