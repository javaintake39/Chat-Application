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
import customization.FriendListCustomization;
import eg.gov.iti.chatcommon.model.Message;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
    private User user;
    private ServerInterface service;
    //  private ClientInterface clientService;

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
            System.out.println(user.getEmail() + "in home screen controller");
            System.out.println(service + " in home screen controller");
            this.service = service;
            Platform.runLater(() -> {
                friendList.setCellFactory((ListView<User> param) -> {
                    return new FriendListCustomization();
                });
                
            });
        } catch (JAXBException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println(user.getName() + user.getCountry());
        userName.setText(user.getName());

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
            currentSelectedFriend = newString.getPhoneNumber();
            //clear all items on messageList 
            messageContentLV.getItems().clear();
            //Display object for  currentSelectedFriend
            List<SingleMessage> contactMessageList = new HomeScreenController().getContactMessages(user.getPhoneNumber());   //get message Lists for specific contact 
            for (SingleMessage msg : contactMessageList) {
                if (msg.getFrom().equals(currentSelectedFriend) || msg.getTo().equals(currentSelectedFriend)) {
                    Platform.runLater(() -> {
                        messageContentLV.getItems().add(msg.getContent()); //get each friend chat content  
                    });

                }
            }
            //System.out.println(contactMessageList.get(0).getContent());

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
                    newMessage.setBackgroudcolor("#0000");
                    newMessage.setBold(Boolean.FALSE);
                    newMessage.setColor("#0000");
                    newMessage.setFontsize(11);
                    newMessage.setUnderline(Boolean.FALSE);
                    newMessage.setItalic(Boolean.FALSE);
                    newMessage.setFontfamily("Arial");
                    //getDate
                    GregorianCalendar Date = new GregorianCalendar();
                    Date.getTime();
                    XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(Date);
                    newMessage.setDate(date);  //set current Date & time
                    //set values of message
                    Message message=new Message();
                    message.setFrom( newMessage.getFrom());
                    message.setTo(newMessage.getTo());
                    message.setChatType(newMessage.getChattype().value());
                    message.setContent( newMessage.getContent());
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
                    //System.out.println(newMessage.getContent());
                } catch (RemoteException ex) { 
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DatatypeConfigurationException ex) {
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

    public void recieveMessage(Message message) {
        System.out.println(user.getName());
        SingleMessage singleMessage=new SingleMessage(); 
        //set values of singleMessage
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
        Platform.runLater(new Runnable () {
            @Override
            public void run() {
               new HomeScreenController().addNewMessage(singleMessage);
            }

        });
       // messageContentLV.getItems().add(message);
    }

    public List<SingleMessage> getContactMessages(String ContactPhone) {

        try {
            //context = JAXBContext.newInstance("MyMessage");
            Unmarshaller unmarsh = context.createUnmarshaller();
            JAXBElement JAXBSession = (JAXBElement) unmarsh.unmarshal(new File("src\\main\\java\\Session\\Message.xml"));

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
            JAXBElement JAXBSession = (JAXBElement) unmarsh.unmarshal(new File("src\\main\\java\\Session\\Message.xml"));
            messages = (Messages) JAXBSession.getValue(); //root element
            List<SingleMessage> list = messages.getMsg();
            ObjectFactory factory = new ObjectFactory();    
            list.add(newMessage);
            JAXBElement Message = factory.createMyMsg(messages);
            Marshaller marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.marshal(Message, new FileOutputStream("src\\main\\java\\Session\\Message.xml"));

        }catch (PropertyException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
