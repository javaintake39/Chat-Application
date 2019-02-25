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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import customization.ChatBoxFormat;
import customization.FriendListCustomization;
import eg.gov.iti.chatcommon.model.Message;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.DirectoryChooser;
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
import javax.imageio.ImageIO;
import notification.OfflineNotification;
import notification.OnlineNotification;
import notification.ReceiveMessageNotification;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import org.alicebot.ab.MagicBooleans;

import services.ServerServices;

/**
 * FXML Controller class
 *
 * @author ghazallah
 */
public class HomeScreenController implements Initializable {

    public void announceServerDown() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Server not found .. try again later");
            alert.showAndWait();
            Runtime.getRuntime().exit(0);
        });

    }

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
    private JFXButton addFriend;
    @FXML
    ComboBox<String> statusCombo;

    @FXML
    private ImageView userImage;

    @FXML
    private JFXButton viewInvitationButton;
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

    //data for file transfere
    private int myLength;
    private boolean check;
    private String userNameSender;
    private String filePlace = "";

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

        Image image2 = null;

        ByteArrayInputStream input = new ByteArrayInputStream(user.getPicture());
        BufferedImage image1;
        try {
            image1 = ImageIO.read(input);
            image2 = javafx.embed.swing.SwingFXUtils.toFXImage(image1, null);
            userImage.setImage(image2);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        userName.setText(user.getName());

        handleCloseAction();

        ObservableList<String> status = FXCollections.observableArrayList("Available", "busy", "away", "offline");

        statusCombo.getItems().addAll(status);
        statusCombo.getSelectionModel().selectFirst();
        user.setStatus_id(2);

        try {
            service.setStatus(user);
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<Integer> messgageFontSize = FXCollections.observableArrayList(10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34);
        fontSizeComboBox.getItems().addAll(messgageFontSize);
        fontSizeComboBox.getSelectionModel().selectFirst();
        colorPicker.setValue(Color.BLACK);

        ObservableList<String> messageFontType = FXCollections.observableArrayList("Arial", "Helvetica",
                "Times New Roman", "Times", "Courier New", "Courier", "Verdana", "Georgia", "Palatino", "Garamond", "Bookman",
                "Comic Sans MS", "Trebuchet MS", "Arial Black", "Impact");
        fontComboBox.getItems().addAll(messageFontType);
        fontComboBox.getSelectionModel().selectFirst();

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

                if (!txtFieldMsg.getText().isEmpty() && txtFieldMsg.getText() != null) {
                    try {
                        User selectedUser = service.getUser(currentSelectedFriend);
                        if (selectedUser.getStatus_id() == 4) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setContentText("Sorry this user is not available right now");
                            alert.showAndWait();

                        } else {
                            handleMessage(txtFieldMsg.getText(), currentSelectedFriend);
                            txtFieldMsg.clear();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        addFriend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    System.out.println("Tile pressed ");
                    AddContactController controller = new AddContactController(user, service);
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

        ////test 
        //  Node node =friendList.getSelectionModel().getSelectedIndices().
    }

    @FXML
    private void btnSendEmailAction(ActionEvent event) {
    }

    public void recieveAnnoucement(String announcent) {
        Platform.runLater(() -> {
            announcementArea.clear();
            announcementArea.appendText("Server :" + announcent + "\n");
        });
    }
    private User senderUser = null;
    private static final boolean TRACE_MODE = false;

    public void recieveMessage(Message message) {
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

        // String messageSound ="/audio.graceful.mp3";
        //  URL url = getClass().getResource("/audio/graceful.mp3");
        File file = new File("src/main/resources/audio/graceful.mp3");
        AudioClip clip = new AudioClip(file.toURI().toString());

        try {
            senderUser = service.getUser(message.getFrom());
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                new HomeScreenController().addNewMessage(singleMessage);

            }
        });

        if (message.getFrom().equals(currentSelectedFriend)) {
            messageContentLV.getItems().add(singleMessage);
        } else {
            Platform.runLater(() -> {
                clip.play();
                new ReceiveMessageNotification(senderUser).start();
            });
        }
        if (chatBotBtn.isSelected()) {
            String resourcesPath = "lib/";
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = message.getContent();
            String request = textLine;
            String response = chatSession.multisentenceRespond(request);
            System.out.println("BOT :" + response);
            handleMessage("Bot" + response, message.getFrom());
        }
    }

    private String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;

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
        } catch (PropertyException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void finalizeConnection() {
        try {
            user.setStatus_id(4);
            service.setStatus(user);
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

    public void loginNotification(User user) {

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
            System.out.println("file = " + file);
            try {
                System.out.println("-----first line of try-----");
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myData = new byte[1024 * 1024];
                myLength = fileInputStream.read(myData);
                //System.out.println("file length = " + myLength);
                String filePlace = service.checkFileStatus(file.getName(), myData, myLength, currentSelectedFriend, user.getPhoneNumber());
                if (filePlace != null) {
                    System.out.println("entered check ..");
                    /////////////////////////////////////////////////////////////
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Runnable runnableTask = () -> {
                        while (myLength > 0) {
                            try {
                                service.sendFile(file.getName(), myData, myLength, currentSelectedFriend, user.getPhoneNumber(), filePlace);
                                myLength = fileInputStream.read(myData);
                            } catch (RemoteException ex) {
                                Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
                    executor.execute(runnableTask);
                    executor.shutdown();

                }
//                if(filePlace==null||filePlace.isEmpty())
//                {
//                    System.out.println("user rejected your file");
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                              Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                              alert.setContentText(currentSelectedFriend+" Rejected your File .");
//                              alert.showAndWait();
//                        }
//                    });
//                }
                //////////////////////////// In Case of User Reject /////////////////////////////
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

    boolean accept;

    public boolean acceptFile(String senderPhone, String recieverPhone) {

        try {
            final CountDownLatch latch = new CountDownLatch(1);
            userNameSender = service.getName(senderPhone);
            Platform.runLater(() -> {
                ButtonType acceptButton = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                ButtonType rejectButton = new ButtonType("Reject", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(AlertType.CONFIRMATION,
                        userNameSender + " wants to send you a file !", acceptButton, rejectButton);
                alert.setTitle("Request to Send File ");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == acceptButton) {
                    accept = true;
                    System.out.println("Accept button Clicked " + accept);
                }
                if (result.get() == rejectButton) {
                    accept = false;
                    System.out.println("Reject button Clicked" + accept);
                }
                latch.countDown();
            });
            latch.await();

        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("your choice = " + accept);
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

    public void receiveFile(String filePath, byte[] fileData, int len, String directory) {
        File file = new File(directory + "/" + filePath);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable runnableTask = () -> {
            try {
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(fileData, 0, len);
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Error receiving file");
                alert.showAndWait();
                ex.printStackTrace();

            }
        };
        executor.execute(runnableTask);
        executor.shutdown();
    }

    public String getFileLocation() {
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(new Runnable() {
            Stage primaryStage = (Stage) chatAnchorPane.getScene().getWindow();

            @Override
            public void run() {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(primaryStage);

                if (selectedDirectory == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("No Directory Choosed");
                    alert.showAndWait();
                } else {
                    filePlace = selectedDirectory.getAbsolutePath();
                    System.out.println("you will save at " + filePlace);
                }
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("file path before return = " + filePlace);
        return filePlace;
    }

    public void handleMessage(String messageContent, String receiverPhone) {
        try {
            boolean isBold = false;
            boolean isItalic = false;
            boolean isUnderLine = false;
            if (boldToggleBtn.isSelected()) {
                isBold = true;
            }
            if (italicTogglebtn.isSelected()) {
                isItalic = true;
            }
            if (lineToggleBtn.isSelected()) {
                isUnderLine = true;
            }

            //add current message to currentSelectedFriend object
            ObjectFactory factory = new ObjectFactory();
            SingleMessage newMessage = factory.createSingleMessage();
            newMessage.setFrom(user.getPhoneNumber());
            newMessage.setTo(receiverPhone);
            newMessage.setChattype(TypeOfChat.ONE_TO_ONE);
            newMessage.setContent(txtFieldMsg.getText());
            newMessage.setBackgroudcolor("#FFFFFF");
            newMessage.setBold(isBold);
            newMessage.setColor(colorPicker.getValue().toString().replace("0x", "#"));
            newMessage.setFontsize(fontSizeComboBox.getValue());
            newMessage.setUnderline(isUnderLine);
            newMessage.setItalic(isItalic);
            newMessage.setFontfamily(fontComboBox.getValue());
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
            //System.out.println(newMessage.getContent());
            messageContentLV.getItems().add(newMessage);
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onLogOutClicked(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        LoginController controller = new LoginController(service, user.getPhoneNumber());
        try {
            Parent root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
            Scene scene = new Scene(root);
            Stage stage = (Stage) chatAnchorPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onUpdateClicked(ActionEvent event) {
        try {
            System.out.println("Update Button Clicked");
            UpdateProfileController controller = new UpdateProfileController(user, service);

            FXMLLoader loader = new FXMLLoader();

            loader.setController(controller);
            Parent root = loader.load(getClass().getResource("/fxml/updateProfile.fxml").openStream());
            Scene scene = new Scene(root);
            Stage stage = (Stage) chatAnchorPane.getScene().getWindow();
            stage.setScene(scene);
        } // End of Update Profile //////////////////////////////////////////////////////////////////
        catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateFriendStatus(User user) {
        Platform.runLater(() -> {
            try {
                //            if (!HomeScreenController.user.getPhoneNumber().equals(user.getPhoneNumber())) {
//                int element = friendList.getItems().indexOf(user);
//                friendList.getItems().remove(user);
//                friendList.getItems().add(user);
//                friendList.refresh();
//            }
                userFriends = service.getUserFriends(HomeScreenController.user);

            } catch (RemoteException ex) {
                Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
            friendList.getItems().clear();
            userFriends.forEach((friend) -> {
                friendList.getItems().add(friend);
            });
            friendList.refresh();
            //friendList.getItems().clear();
        });
    }

    public void setStatus(int status) {
        user.setStatus_id(status);
        try {
            service.setStatus(user);
        } catch (RemoteException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onStatusComboClicked(ActionEvent event) {

        System.out.println("status combo clicked ");
        System.out.println(statusCombo.getValue());
        switch (statusCombo.getValue()) {
            case "Available":
                setStatus(2);
                break;
            case "away":
                setStatus(1);
                break;
            case "busy":
                setStatus(3);
                break;
            case "offline":
                setStatus(4);
                break;
            default:
                break;

        }

    }

}
