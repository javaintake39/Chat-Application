/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;


import com.jfoenix.controls.JFXButton;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import services.ServerServices;

/**
 *
 * @author ahmed
 */
public class ViewInvitationController implements Initializable {
    
    
    @FXML
    private AnchorPane AnchorPane;
    
    @FXML
    private Circle circleImage;

    @FXML
    private Circle photo;

    @FXML
    private Label name;
    
    @FXML
    private JFXButton rejectButton;

    @FXML
    private JFXButton acceptButton;
    
    ServerServices serverServices; 
    ServerInterface service;
    User user;
    List<User> inviting;
    private int Index=0;
    
    public ViewInvitationController(User user,ServerInterface service){
        serverServices=new ServerServices();
        this.user=user;
        this.service=service;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image=new Image("/images/add-friend.png",false);
        circleImage.setFill(new ImagePattern(image));
        inviting=serverServices.viewInvitation(user.getPhoneNumber());
        if(inviting.isEmpty()==true){
           name.setText("No Invitaions"); 
           photo.setVisible(false);
           rejectButton.setVisible(false);
           acceptButton.setVisible(false);
           
        }else{
           name.setText(inviting.get(Index).getName());        
        }
    }    
    @FXML
    private void acceptInvitaion(ActionEvent event){
        serverServices.AcceptInvitation(user.getPhoneNumber(), inviting.get(Index).getPhoneNumber());
        Index++;
        if(Index>=inviting.size()){
            backToHOmeController();
        }       
    }
    @FXML
    private void rejectInvitation(ActionEvent event) {
        serverServices.RejectInvitation(user.getPhoneNumber(), inviting.get(Index).getPhoneNumber());
        Index++;
        if(Index>=inviting.size()){
            backToHOmeController();
        }    
    }
    private void backToHOmeController(){
        HomeScreenController controller = new HomeScreenController(user,service);
         try {   
             FXMLLoader loader = new FXMLLoader();
             loader.setController(controller);
             Parent root = loader.load(getClass().getResource("/fxml/HomeScreen.fxml").openStream());
             Scene scene = new Scene(root);
             Stage stage = (Stage) AnchorPane.getScene().getWindow();
             stage.setScene(scene);
         } catch (IOException ex) {
             ex.printStackTrace();
         }
    }
 
    @FXML
    private void Invitation(ActionEvent event) {
        backToHOmeController();
           
    }
    
    
}
