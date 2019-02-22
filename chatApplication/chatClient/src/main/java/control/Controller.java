//
//package control;
//
//import eg.gov.iti.chatcommon.model.User;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
//import java.nio.channels.SeekableByteChannel;
//import java.nio.file.Files;
//import java.nio.file.InvalidPathException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.scene.Scene;
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
//    private String DirPath=null;
//
//    public Controller() throws RemoteException {
//        clientService = new ClientServices(this);
////        user.setPhoneNumber("11");
////        user.setPassword("ASDFasd01021@");
//      //  user.setPhoneNumber("0111111111");
//        //user.setPassword("ASDasd123@");
//         user.setPhoneNumber("015"); 
//        user.setPassword("123");
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        serverService = new ServerServices(); //binding services
//        homePageBase = new HomePageBase();
//    loginBase = new LoginBase();
//        homeScene = new Scene(homePageBase, 800, 600);
//        loginScene = new Scene(loginBase, 800, 600);
//        homePageBase.btnLogin.setOnAction((ActionEvent event) -> {
//            loginBase.listView.getItems().clear();
//            loginBase.textField.clear();
//            loginBase.textArea.clear();
//            user = serverService.loginIn(user, clientService);
//            DirPath=user.getPhoneNumber();
//           
//                    //creating folder for that user 
//                    Controller.CreateContactDirectory (DirPath);
//                    userFriends = serverService.getUserFriends(user);
//                    //get new users
//                    userFriends.forEach((friend) -> {
//                        loginBase.listView.getItems().add(friend.getName());                     
//                        //create file for each Friend 
//                        Controller.CreateFriendFile(DirPath+"\\"+friend.getPhoneNumber()+".txt");
//
//                    });
//            
//            primaryStage.setScene(loginScene);
//        });
//        loginBase.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldString, newString) ->{
//            currentSelectedFriend = newString.toString();
//            userFriends.forEach((friend) -> {
//                if (friend.getName().equals(newString)) {
//                    currentSelectedFriend = friend.getPhoneNumber();
//                    //getFile for that Friend or create it if it wasn't created
//                    String friendChat=Controller.ReadFreindFile(DirPath+"\\"+friend.getPhoneNumber()+".txt");
//                    loginBase.textArea.setText(friendChat);
//                    
//                }
//            });
//            System.out.println(currentSelectedFriend);
//        });
//        loginBase.textField.setOnKeyPressed((e) -> {
//            if (e.getCode().equals(KeyCode.ENTER)) {
//                
//                serverService.sendMessage(currentSelectedFriend, loginBase.textField.getText());
//                
//                System.out.println("Enter Pressed");
//                
//                String fileContent=loginBase.textField.getText();
//                //write on that friend file 
//                Controller.writeOnFriendFile(fileContent,DirPath+"\\"+currentSelectedFriend+".txt",currentSelectedFriend+"\\"+DirPath+".txt");
//                
//                loginBase.textField.clear();
//                
//                
//            }
//        });
//        loginBase.button.setOnAction((event) -> {
//            serverService.sendMessage(currentSelectedFriend, loginBase.textField.getText());
//            System.out.println("Button Pressed");
//            loginBase.textField.clear();
//        });
//
//        primaryStage.setOnCloseRequest((e) -> {   
//            System.out.println("unregistered");
//                   // serverService.unregister(clientService);
//                 
//        });
//        primaryStage.setTitle("Chat Application");
//        primaryStage.setScene(homeScene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//    
//    public static void CreateContactDirectory (String DirPath){
//        File theDir = new File(DirPath);                
//                if (!theDir.exists()) {
//                    System.out.println("creating directory: " + theDir.getName());
//                    boolean result = false;
//
//                    try{
//                        theDir.mkdir();
//                        result = true;
//                    } 
//                    catch(SecurityException se){
//                        //handle it
//                    }        
//                    if(result) {    
//                        System.out.println("DIR created");  
//                    }
//                }else{
//                    System.out.println("Directory already Exist");
//                }
//    
//    }
//    public static  void CreateFriendFile (String FilePath){
//         try {
//                //File file = new File(DirPath+"\\"+friend.getPhoneNumber());
//                File file = new File(FilePath);
//                /*If file gets created then the createNewFile() 
//                 * method would return true or if the file is 
//                 * already present it would return false
//                 */
//                boolean fvar = file.createNewFile();
//                if (fvar){
//                     System.out.println("File has been created successfully");
//                }
//                else{
//                     System.out.println("File already present at the specified location");
//                }
//           } catch (IOException e) {
//                   System.out.println("Exception Occurred:");
//                   e.printStackTrace();
//            }
//    }
//    public static String ReadFreindFile(String Path){
//        FileInputStream fileInputStream = null;
//         String fileContent="";
//        try {
//            File file = new File(Path);
//            fileInputStream = new FileInputStream(file);
//            byte[] data = new byte[(int) file.length()];
//            fileInputStream.read(data);
//            fileContent = new String(data, "UTF-8");
//            fileInputStream.close();
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileInputStream.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//            return fileContent;
//        }
//        public static void writeOnFriendFile(String message,String filePath1,String filePath2){
//            BufferedWriter writer1=null;
//            BufferedWriter writer2=null;
//        try {
//            System.out.println(filePath1);
//            System.out.println(filePath2);        
//             writer1 = new BufferedWriter(new FileWriter(filePath1, true));
//            writer1.write(message);
//            writer1.newLine();
//            writer2 = new BufferedWriter(new FileWriter(filePath2, true));
//            writer2.write(message);
//            writer2.newLine();
//            writer1.close();
//            writer2.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
//        }
//    }
//    
//
