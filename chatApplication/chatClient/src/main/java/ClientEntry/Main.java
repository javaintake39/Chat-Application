/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientEntry;

import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import fxmlControllers.IpController;
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
public class Main extends Application {

    

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        IpController controller = new IpController();
        loader.setController(controller);
        Parent root = loader.load(getClass().getResource("/fxml/ip.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chater");
//        primaryStage.setScene(new Scene(root));
//        FXMLLoader loader = new FXMLLoader();
//        LoginController loginController = new LoginController(service);
//        loader.setController(loginController);
//        Parent root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
//        primaryStage.setTitle("Chater");      
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setOnCloseRequest(closeEvent->{
//            
//            
//        });
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
