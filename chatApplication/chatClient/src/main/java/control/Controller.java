//package control;
//
//import eg.gov.iti.chatcommon.model.User;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.input.KeyCode;
//import javafx.stage.Stage;
//import services.ClientServices;
//import services.ServerServices;
//import view.HomePageBase;
//import view.LoginBase;
//
//public class Controller extends Application {
//
//    HomePageBase homePageBase = null;
//    public LoginBase loginBase = null;
//
//    public ClientServices clientService;
//
//    public ServerServices serverService;
//
//    Scene homeScene = null;
//    Scene loginScene = null;
//
//    String currentSelectedFriend = null;
//
//    List<User> userFriends = new ArrayList<User>();
//    private User user = new User();
//
//    public Controller() throws RemoteException {
//        clientService = new ClientServices(this);
//        user.setPhoneNumber("0111111111");
//       user.setPassword("ASDasd123@");
////        user.setPhoneNumber("0111111111");
////        user.setPassword("ASDasd123@");
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        serverService = new ServerServices(); //binding servicesahBosnia
//        homePageBase = new HomePageBase();
//    loginBase = new LoginBase();
//        homeScene = new Scene(homePageBase, 800, 600);
//        loginScene = new Scene(loginBase, 800, 600);
//        homePageBase.btnLogin.setOnAction((ActionEvent event) -> {
//            loginBase.listView.getItems().clear();
//            loginBase.textField.clear();
//            loginBase.textArea.clear();
//            user = serverService.loginIn(user, clientService);
//            Runnable task;
//            Platform.runLater(task = new Runnable() {
//
//                @Override
//                public void run() {
//                    userFriends = serverService.getUserFriends(user);
//                    //get new users
//                    userFriends.forEach((friend) -> {
//                        //friend.
//                        
//                        loginBase.listView.getItems().add(friend);
//                    });
//                }
//            });
//            primaryStage.setScene(loginScene);
//        });
//        loginBase.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldString, newString) -> {
//            currentSelectedFriend = newString.getName();
//            userFriends.forEach((friend) -> {
//                if (friend.getName().equals(newString.getName())) {
//                    currentSelectedFriend = friend.getPhoneNumber();
//                }
//            });
//            System.out.println(currentSelectedFriend);
//        });
//        loginBase.textField.setOnKeyPressed((e) -> {
//            if (e.getCode().equals(KeyCode.ENTER)) {
//                serverService.sendMessage(currentSelectedFriend, loginBase.textField.getText());
//                System.out.println("Enter Pressed");
//                loginBase.textField.clear();
//            }
//        });
//        loginBase.button.setOnAction((event) -> {
//            serverService.sendMessage(currentSelectedFriend, loginBase.textField.getText());
//            System.out.println("Button Pressed");
//            loginBase.textField.clear();
//        });
//
//        /*   primaryStage.setOnCloseRequest((e) -> {
//               
//                    System.out.println("unregistered");
//                    serverService.unregister(clientService);
//                 
//            });*/
//        primaryStage.setTitle("Chat Application");
//        primaryStage.setScene(homeScene);
//        primaryStage.show();
//        
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}
