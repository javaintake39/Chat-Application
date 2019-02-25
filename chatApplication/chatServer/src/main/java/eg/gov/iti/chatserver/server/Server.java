/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.server;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controllers.DashboardController;
import view.controllers.SettingsController;

/**
 *
 * @author ghazallah
 */
public class Server extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        DashboardController controller = new DashboardController();
        loader.setController(controller);
        Parent root = loader.load(getClass().getResource("/fxml/Dashboard.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(action->{
            if (SettingsController.serverON){
                controller.finalizeConnection();
                
                Runtime.getRuntime().exit(0);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
