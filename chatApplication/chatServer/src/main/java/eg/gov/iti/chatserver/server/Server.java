/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.server;

import eg.gov.iti.chatcommon.model.User;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
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

   /* public static void main(String[] args) {
        try {
            ServerImplementation server = new ServerImplementation();
            Registry chatRegistry = LocateRegistry.createRegistry(9800);
            chatRegistry.rebind("chatService", server);
<<<<<<< HEAD
            System.out.println("Server binding");
           
=======
            
>>>>>>> 7b68e3e94066a0a30ce37e71f41f1233d8417bc0

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

    }*/
    public static void main(String[] args) {
        launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
