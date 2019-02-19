/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.awt.event.MouseEvent;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author ahmed
 */
public class DashboardController implements Initializable {

    public DashboardController() {
    }

    @FXML
    private Pane dashboardPane;
    @FXML
    private JFXButton announcementALL;
    @FXML
    private JFXButton registerNewUser;
    @FXML
    private JFXButton statistics;
    @FXML
    private JFXButton setting;
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }
    
    
    public void viewAnnounce(ActionEvent actionEvent) throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("/fxml/Announcement.fxml"));
            dashboardPane.getChildren().clear();
            dashboardPane.getChildren().add(root);
    }
   
     
     public void viewSettings(ActionEvent actionEvent) throws IOException {
       Node root = FXMLLoader.load(getClass().getResource("/fxml/Setting.fxml"));
        dashboardPane.getChildren().clear();
        dashboardPane.getChildren().add(root);
    }
     
      public void viewStatistics(ActionEvent actionEvent) throws IOException {
      //       Node root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        dashboardPane.getChildren().clear();
//        dashboardPane.getChildren().add(root);

    }
    
      public void viewClient(ActionEvent actionEvent) throws IOException {
        //       Node root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        dashboardPane.getChildren().clear();
//        dashboardPane.getChildren().add(root);
    }

    
    

}
