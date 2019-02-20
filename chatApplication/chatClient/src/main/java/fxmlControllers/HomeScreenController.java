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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import notification.OfflineNotification;
import notification.OnlineNotification;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

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
    @FXML
    Label userName;
    @FXML
    ListView<String> messageContentLV;
    @FXML
    private ToggleButton chatBotBtn;
    @FXML
    private AnchorPane chatAnchorPane;

    @FXML
    ComboBox<String> statusCombo;
    private User user;
    private ServerInterface service;

    List<User> userFriends = new ArrayList<User>();
    String currentSelectedFriend = null;

    /**
     * Initializes the controller class.
     */
    public HomeScreenController(User user, ServerInterface service) {
        this.user = user;
        this.service = service;
        Platform.runLater(() -> {
            friendList.setCellFactory((ListView<User> param) -> {
                return new FriendListCustomization();
            });

        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleCloseAction();

        ObservableList<String> status= FXCollections.observableArrayList("Available","busy","away","offline");

        statusCombo.getItems().addAll(status);

        userName.setText(user.getName());

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
        friendList.getSelectionModel().selectedItemProperty().addListener((observable, oldString, newString) -> {
            currentSelectedFriend = newString.getName();
            userFriends.forEach((friend) -> {
                if (friend.getName().equals(newString.getName())) {
                    currentSelectedFriend = friend.getPhoneNumber();
                }
            });
        });
        txtFieldMsg.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                try {
                    service.sendMessage(currentSelectedFriend, txtFieldMsg.getText());
                } catch (RemoteException ex) {
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Enter Pressed");
                txtFieldMsg.clear();
            }
        });
    }

    @FXML
    private void txtFieldOnKeyPressed(KeyEvent event) {
    }

    @FXML
    private void btnSendEmailAction(ActionEvent event) {
    }

    public void recieveAnnoucement(String announcent) {
        announcementArea.appendText(announcent + "\n");
        System.out.println("recieved announcement from the server");
    }

    public void recieveMessage(String message) {
        Platform.runLater(() -> {
            if (chatBotBtn.isSelected()) {
                String botname = "mybot";
                String path = "/home/ghazallah/Desktop/ab";
                Bot bot = new Bot(botname, path);
                Chat chatSession = new Chat(bot);
                bot.brain.nodeStats();
                String textLine = "";
                textLine = message;
                String request = textLine;
                String response = chatSession.multisentenceRespond(request);
                System.out.println(response);
            }
        });

        messageContentLV.getItems().add(message);
    }

    public void finalizeConnection() {
        try {
            service.logout(user);
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleCloseAction() {

        Platform.runLater(() -> {

            Stage primaryStage = (Stage) chatAnchorPane.getScene().getWindow();
            primaryStage.setOnCloseRequest(closeEvent -> {
                finalizeConnection();
                Runtime.getRuntime().exit(0);
            });
        });

    }
    
    public void loginNotification (User user) {
        try {
            if (service.isMyFriend(this.user.getPhoneNumber(),user.getPhoneNumber())){
               new OnlineNotification(user).start();
            }} catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void logoutNotification (User user){
        try {
            if (service.isMyFriend(this.user.getPhoneNumber(),user.getPhoneNumber())){
                new OfflineNotification(user).start();
            }} catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

}
