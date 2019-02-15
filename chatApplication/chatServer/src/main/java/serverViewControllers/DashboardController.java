/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverViewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ghazallah
 */
public class DashboardController implements Initializable {

    @FXML
    private Pane dashboardPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onRegisterClicked(ActionEvent event) {
    }

    @FXML
    private void onAnnounceClicked(ActionEvent event) {
        try {
            Node announcementNode = FXMLLoader.load(getClass().getResource("/fxml/announcement.fxml"));
            dashboardPane.getChildren().clear();
            dashboardPane.getChildren().add(announcementNode);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onStatisticsClicked(ActionEvent event) {
    }

    @FXML
    private void onSettingsCliccked(ActionEvent event) {
    }
    
}
