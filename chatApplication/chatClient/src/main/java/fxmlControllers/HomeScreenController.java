/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import MyMessage.SingleMessage;
import MyMessage.Messages;
import MyMessage.ObjectFactory;
import MyMessage.TypeOfChat;
import com.jfoenix.controls.JFXTextArea;
import customization.ChatBoxFormat;
import customization.FriendListCustomization;
import eg.gov.iti.chatcommon.model.Message;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import javafx.stage.Stage;
import notification.OfflineNotification;
import notification.OnlineNotification;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import services.ServerServices;

/**
 * FXML Controller class
 *
 * @author ghazallah
 */
public class HomeScreenController implements Initializable {

    @FXML
    private FlowPane editPane;
    @FXML
    private ComboBox<String> fontComboBox;
    @FXML
    private ComboBox<Integer> fontSizeComboBox;
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
    ListView<SingleMessage> messageContentLV;
    @FXML
    private ToggleButton chatBotBtn;
    @FXML
    private AnchorPane chatAnchorPane;
    @FXML
    private ImageView addFriend ;
    @FXML
    ComboBox<String> statusCombo;

    //private User user;
    //private ServerInterface service;
    
    private ServerServices ServerServices;


    public static User user;
    public static ServerInterface service;


    List<User> userFriends = new ArrayList<User>();
    String currentSelectedFriend = null;
    private Messages messages = null;
    private JAXBContext context;
    List<SingleMessage> MessageList = new ArrayList<SingleMessage>();

    /**
     * Initializes the controller class.
     */
    public HomeScreenController() {
        try {
            this.context = JAXBContext.newInstance("MyMessage");
        } catch (JAXBException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HomeScreenController(User user, ServerInterface service) {
        try {
            this.context = JAXBContext.newInstance("MyMessage");
            this.user = user;
            this.service = service;
            Platform.runLater(() -> {
                friendList.setCellFactory((ListView<User> param) -> {
                    return new FriendListCustomization();
                });

            });
            Platform.runLater(() -> {
                messageContentLV.setCellFactory(param -> {
                    return new ChatBoxFormat();
                });

            });
        } catch (JAXBException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userName.setText(user.getName());

        handleCloseAction();

        ObservableList<String> status = FXCollections.observableArrayList("Available", "busy", "away", "offline");

        statusCombo.getItems().addAll(status);

        userName.setText(user.getName());

        try {

            userFriends = service.getUserFriends(user);
            //get new users
            userFriends.forEach((friend) -> {
                friendList.getItems().add(friend);
            });
            //to select first in list
           // friendList.getSelectionModel().selectFirst();
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
       
        friendList.getSelectionModel().selectedItemProperty().addListener((observable, oldString, newString) -> {
            currentSelectedFriend = newString.getPhoneNumber();
            //clear all items on messageList 
            messageContentLV.getItems().clear();
            //Display object for  currentSelectedFriend
            List<SingleMessage> contactMessageList = new HomeScreenController().getContactMessages(user.getPhoneNumber());   //get message Lists for specific contact 
            for (SingleMessage msg : contactMessageList) {
                if (msg.getFrom().equals(currentSelectedFriend) || msg.getTo().equals(currentSelectedFriend)) {
                    Platform.runLater(() -> {
                        messageContentLV.getItems().add(msg); //get each friend chat content  
                    });
                }
            }

            });

        txtFieldMsg.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                try {
                    //add current message to currentSelectedFriend object
                    ObjectFactory factory = new ObjectFactory();
                    SingleMessage newMessage = factory.createSingleMessage();
                    //creating of Single Message Object
                    newMessage.setFrom(user.getPhoneNumber());
                    newMessage.setTo(currentSelectedFriend);
                    newMessage.setChattype(TypeOfChat.ONE_TO_ONE);
                    newMessage.setContent(txtFieldMsg.getText());
                    //  newMessage.setBackgroudcolor("#FFFF");
                    newMessage.setBold(Boolean.FALSE);
                    newMessage.setColor("#A52A2A");
                    newMessage.setFontsize(18);
                    newMessage.setUnderline(Boolean.FALSE);
                    newMessage.setItalic(Boolean.FALSE);
                    newMessage.setFontfamily("Arial");
                    //getDate
                    GregorianCalendar Date = new GregorianCalendar();
                    Date.getTime();
                    XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(Date);
                    newMessage.setDate(date);  //set current Date & time
                    //set values of message
                    Message message = new Message();
                    message.setFrom(newMessage.getFrom());
                    message.setTo(newMessage.getTo());
                    message.setChatType(newMessage.getChattype().value());
                    message.setContent(newMessage.getContent());
                    message.setBackgroundColor(newMessage.getBackgroudcolor());
                    message.setFontColor(newMessage.getColor());
                    message.setFontsSize(newMessage.getFontsize());
                    message.setUnderline(newMessage.isUnderline());
                    message.setBold(newMessage.isBold());
                    message.setItalic(newMessage.isItalic());
                    message.setFontFamily(newMessage.getFontfamily());
                    message.setDate(date);
                    service.sendMessage(message);
                    System.out.println(newMessage.getContent());

                } catch (RemoteException ex) { 

                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                txtFieldMsg.clear();
            }
        });
        
    addFriend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            try {
                System.out.println("Tile pressed ");
                AddContactController controller = new AddContactController(user,service);
                FXMLLoader loader = new FXMLLoader();
                
                loader.setController(controller);
                Parent root = loader.load(getClass().getResource("/fxml/AddContact.fxml").openStream());
                Scene scene = new Scene(root);
                Stage stage = (Stage) chatAnchorPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
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
        Platform.runLater(()->{
             announcementArea.appendText(announcent + "\n");
        });
       
        //System.out.println("recieved announcement from the server");
    }

    public void recieveMessage(Message message){
        System.out.println(user.getName());
        SingleMessage singleMessage = new SingleMessage();
        singleMessage.setBackgroudcolor(message.getBackgroundColor());
        singleMessage.setBold(message.getBold());
        singleMessage.setContent(message.getContent());
        singleMessage.setColor(message.getFontColor());
        singleMessage.setFrom(message.getFrom());
        singleMessage.setTo(message.getTo());
        singleMessage.setDate(message.getDate());
        singleMessage.setUnderline(message.getUnderline());
        singleMessage.setItalic(message.getItalic());
        singleMessage.setFontfamily(message.getFontFamily());
        singleMessage.setFontsize(message.getFontsSize());
        singleMessage.setChattype(TypeOfChat.ONE_TO_ONE);
        //addMessage to xml

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                new HomeScreenController().addNewMessage(singleMessage);
            }
        });

       //messageContentLV.getItems().add(message);

    }

    public List<SingleMessage> getContactMessages(String ContactPhone) {
        try {
            //context = JAXBContext.newInstance("MyMessage");
            Unmarshaller unmarsh = context.createUnmarshaller();
            JAXBElement JAXBSession = (JAXBElement) unmarsh.unmarshal(new File("src/main/java/Session/Message.xml"));

            messages = (Messages) JAXBSession.getValue(); //root element
            List<SingleMessage> list = messages.getMsg();
            for (SingleMessage msg : list) {

                if (msg.getFrom().equals(ContactPhone) || msg.getTo().equals(ContactPhone)) {
                    System.out.println(msg.getContent());
                    MessageList.add(msg);
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return all messge that was send from or to contact
        return MessageList;
    }

    public void addNewMessage(SingleMessage newMessage) {

        try {
            context = JAXBContext.newInstance("MyMessage");
            Unmarshaller unmarsh = context.createUnmarshaller();
            JAXBElement JAXBSession = (JAXBElement) unmarsh.unmarshal(new File("src/main/java/Session/Message.xml"));
            messages = (Messages) JAXBSession.getValue(); //root element
            List<SingleMessage> list = messages.getMsg();
            ObjectFactory factory = new ObjectFactory();
            list.add(newMessage);
            JAXBElement Message = factory.createMyMsg(messages);
            Marshaller marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.marshal(Message, new FileOutputStream("src/main/java/Session/Message.xml"));
        }catch (PropertyException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            if (service.isMyFriend(this.user.getPhoneNumber(), user.getPhoneNumber())) {
                new OnlineNotification(user).start();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    public void logoutNotification(User user) {
        try {

            if (service.isMyFriend(this.user.getPhoneNumber(), user.getPhoneNumber())) {
                new OfflineNotification(user).start();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onSendFileClicked(ActionEvent event) {
        FileChooser sendFile = new FileChooser();
        File selectedFile = sendFile.showOpenDialog((Stage) chatAnchorPane.getScene().getWindow());
        if (selectedFile == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No file selected");
            alert.showAndWait();

        } else {
            // System.out.println(selectedFile.getAbsolutePath());
            File file = new File(selectedFile.getAbsolutePath());
            System.out.println("file "+file);
            try {
                System.out.println("-----first line of try-----");
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myData = new byte[1024 * 1024];
                int myLength = fileInputStream.read(myData);
                
                //Thread sendingThread = new Thread();
                System.out.println("file length = "+myLength);
                while (myLength > 0) {
                    System.out.println("called");
                    service.sendFile(file.getName(), myData, myLength, currentSelectedFriend, user.getPhoneNumber());
                    myLength = fileInputStream.read(myData);
                }

            } catch (IOException ex) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Error sending file");
                    alert.showAndWait();
                });

                ex.printStackTrace();
            }

        }
    }

    public boolean acceptFile(String senderPhone, String recieverPhone) {
        boolean accept = false;
        try {
            String userName = service.getName(senderPhone);
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setContentText(userName + "wants to send you a file");
//            alert.showAndWait();

             

            System.out.println("called this accept method");

            ButtonType acceptButton = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            ButtonType rejectButton = new ButtonType("Reject", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    userName + " wants to send you a file", acceptButton, rejectButton);
            alert.setTitle("Date format warning");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(rejectButton) == acceptButton) {
                accept = true;
                System.out.println("accept = " + accept);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accept;
    }

    public void rejectFile(String phoneNumber) {
        try {
            String name = service.getName(phoneNumber);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText(name + " rejected recieving your file");
            alert.showAndWait();
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void receiveFile(String filePath, byte[] fileData, int len) {
        File file = new File(filePath);
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(fileData, 0, len);
            fileOutputStream.flush();

            fileOutputStream.close();
            System.out.println("receiving");

        } catch (IOException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error receiving file");
            alert.showAndWait();
            ex.printStackTrace();

        }


    }
    
}
