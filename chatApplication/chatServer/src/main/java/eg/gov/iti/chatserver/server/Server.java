/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ghazallah
 */
public class Server extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public static void main(String[] args) throws InterruptedException {
//        try {
//            ServerImplementation server = new ServerImplementation();
//            Registry chatRegistry = LocateRegistry.createRegistry(9800);
//            chatRegistry.rebind("chatService", server);
//            System.out.println("Server binding");
//           
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//        }
//
//    }

   
    public static void main(String[] args) {
        launch(args);
    }
}
