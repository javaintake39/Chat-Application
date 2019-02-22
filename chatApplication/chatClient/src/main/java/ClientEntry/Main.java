/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientEntry;

import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import fxmlControllers.LoginController;
import fxmlControllers.RegisterationController;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ghazallah
 */
public class Main extends Application{
    private Registry chatRegistry = null;
    private ServerInterface service = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            chatRegistry = LocateRegistry.getRegistry("127.0.0.1", 9800);
            service = (ServerInterface) chatRegistry.lookup("chatService");
            System.out.println(service);
            
        } catch (RemoteException ex) {
            ex.printStackTrace();
            
        } catch (NotBoundException ex) {
            ex.printStackTrace();
           
        }
        
        FXMLLoader loader = new FXMLLoader();
        LoginController loginController = new LoginController(service);
        loader.setController(loginController);
        Parent root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
        primaryStage.setTitle("Chater");      
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(closeEvent->{
            
            
        });
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
       
    }
    
}
