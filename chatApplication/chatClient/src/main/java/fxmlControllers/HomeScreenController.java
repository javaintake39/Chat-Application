/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import com.jfoenix.controls.JFXTextArea;
import customization.FriendListCustomization;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author ghazallah
 */
public class HomeScreenController implements Initializable {

    @FXML
    private FlowPane editPane;
    @FXML
    private ComboBox<?> fontComboBox;
    @FXML
    private ComboBox<?> fontSizeComboBox;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ToggleButton boldToggleBtn;
    @FXML
    private ToggleButton italicTogglebtn;
    @FXML
    private ToggleButton lineToggleBtn;
    @FXML
    private TextField txtFieldMsg;
    @FXML
    private Button btnSendEmail;
    @FXML
    private Button saveBtn;
    @FXML
    private Image clips;
    @FXML
    private ListView<User> friendList;
    @FXML
    public JFXTextArea announcementArea;
    private User user;
    private ServerInterface service;
  //  private ClientInterface clientService;

    List<User> userFriends = new ArrayList<User>();
    String currentSelectedFriend = null;

    /**
     * Initializes the controller class.
     */
    public HomeScreenController(User user, ServerInterface service) {
        this.user = user;
        System.out.println(user.getEmail() + "in home screen controller");
        System.out.println(service + " in home screen controller");
        this.service = service;
        Platform.runLater(()->{
             friendList.setCellFactory((ListView<User> param)->{
            return new FriendListCustomization();
        });
            
        });
       
        

    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println(user.getName() + user.getCountry());
        
         System.out.println("passed");
        try {
            userFriends = service.getUserFriends(user);
            //get new users
            userFriends.forEach((friend) -> {

                friendList.getItems().add(friend);
            });

        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        friendList.getSelectionModel().selectedItemProperty().addListener((observable, oldString, newString) -> {
            currentSelectedFriend = newString.getName();
            userFriends.forEach((friend) -> {
                if (friend.getName().equals(newString.getName())) {
                    currentSelectedFriend = friend.getPhoneNumber();
                }
            });
        });
    }

    @FXML
    private void txtFieldOnKeyPressed(KeyEvent event) {
    }

    @FXML
    private void btnSendEmailAction(ActionEvent event) {
    }
    
    public void recieveAnnoucement (String announcent){
        announcementArea.appendText(announcent+"\n");
        System.out.println("recieved announcement from the server");
    }

}
