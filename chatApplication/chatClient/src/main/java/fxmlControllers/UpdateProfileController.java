/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import utilities.Validation;

/**
 * FXML Controller class
 *
 * @author Abdelrhman
 */
public class UpdateProfileController implements Initializable {

    @FXML
    private AnchorPane registerationAnchorPane;
    @FXML
    private ImageView photoId;
    @FXML
    private JFXButton choosePictureButton;
    @FXML
    private JFXTextField nameTxtField;
    @FXML
    private JFXTextField phoneId;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXPasswordField passwordTxtField;
    @FXML
    private JFXPasswordField confirmPasswordTxtField;
    @FXML
    private JFXDatePicker dateOfBirthId;
    @FXML
    private JFXTextField emailTxtField;
    @FXML
    private JFXComboBox<String> genderComboBox;
    @FXML
    private JFXComboBox<String> countryComboBox;
    @FXML
    private JFXTextArea bioId;
    User user;
    ServerInterface service;
    ObservableList<String> genderList;
    ObservableList<String> countryList;
     private Image imageFileMarsh;
    UpdateProfileController(User user, ServerInterface service) 
    {
        this.user=user;
        this.service=service;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        genderList = FXCollections.observableArrayList("Male", "Female");
        countryList = FXCollections.observableArrayList("Australia","Bolivia","Bosnia","Brazil","Egypt","USA","Russia","Qatar"
        +"China","Colombia","Croatia","Denmark","France","Germany","Greece");
        genderComboBox.setItems(genderList);
        genderComboBox.setVisibleRowCount(2);
        genderComboBox.setEditable(true);
        countryComboBox.setItems(countryList);
        countryComboBox.setVisibleRowCount(10);
        countryComboBox.setEditable(true);
        countryComboBox.setValue(user.getCountry());
        genderComboBox.setValue(user.getGender());
        nameTxtField.setText(user.getName());
        phoneId.setText(user.getPhoneNumber());
        phoneId.setEditable(false);
        emailTxtField.setText(user.getEmail());
        passwordTxtField.setText(user.getPassword());
        confirmPasswordTxtField.setText(user.getPassword());

        //dateOfBirthId.setValue(LOCAL_DATE(user.getBirthDate()));
        bioId.setText(user.getBio());
        Image image2 = null;
        ByteArrayInputStream input = new ByteArrayInputStream(user.getPicture());
            BufferedImage image1;
            try {
                image1 = ImageIO.read(input);
                image2 = javafx.embed.swing.SwingFXUtils.toFXImage(image1, null);
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            photoId.setImage(image2);
           choosePictureButton.setOnAction((ActionEvent event) -> {
            InputStream stream = null;
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open File Chooser");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
                File selectedImage = fileChooser.showOpenDialog(null);

                if (selectedImage == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You didn't select image : please select image");
                    alert.showAndWait();
                } else {
                    stream = new FileInputStream(selectedImage);
                    imageFileMarsh = new Image(stream);
                    photoId.setImage(imageFileMarsh);
                    Image image = new Image(selectedImage.toURI().toString());
                    photoId.setImage(image);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {

            }
        });
            registerButton.setOnAction((ActionEvent event) -> {
            User newUser = new User();
            boolean check = true;

            if (Validation.validatePhone(phoneId.getText())) {
                newUser.setPhoneNumber(phoneId.getText());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid phone");
                alert.show();
                check = false;
            }

            if (Validation.validateEmail(emailTxtField.getText())) {
                newUser.setEmail(emailTxtField.getText());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Email");
                alert.show();
                check = false;
            }
            if (Validation.validatePassword(passwordTxtField.getText())) {
                newUser.setPassword(passwordTxtField.getText());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose strong password");
                alert.show();
                check = false;
            }
            if (passwordTxtField.getText().equals(confirmPasswordTxtField.getText())) {

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password not matching");
                alert.show();
                check = false;
            }

            try {
                if (imageFileMarsh != null) {
                    BufferedImage userImage = SwingFXUtils.fromFXImage(imageFileMarsh, null);
                    ByteArrayOutputStream s = new ByteArrayOutputStream();
                    ImageIO.write(userImage, "png", s);
                    byte[] imageBytes = s.toByteArray();
                    newUser.setPicture(imageBytes);
                } else {
                    BufferedImage userImage = SwingFXUtils.fromFXImage(photoId.getImage(), null);
                    ByteArrayOutputStream s = new ByteArrayOutputStream();
                    ImageIO.write(userImage, "png", s);
                    byte[] imageBytes = s.toByteArray();
                    newUser.setPicture(imageBytes);
                }
            } catch (IOException ex) {
                Logger.getLogger(RegisterationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!bioId.getText().equals("\\s+") && bioId != null) {
                newUser.setBio(bioId.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("please Enter a BIO");
                alert.showAndWait();
                check = false;
            }
            if (!nameTxtField.getText().equals("\\s+") && nameTxtField != null) {
                newUser.setName(nameTxtField.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("please Enter a username");
                alert.showAndWait();
                check = false;
            }
            if ((String) countryComboBox.getValue() != null) {
                newUser.setCountry((String) countryComboBox.getValue());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("please a country");
                alert.showAndWait();
                check = false;
            }
            if ((String) genderComboBox.getValue() != null) {
                newUser.setGender((String) genderComboBox.getValue());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Enter a Gender");
                alert.showAndWait();
                check = false;
            }

            LocalDate date = dateOfBirthId.getValue();
            if (date != null) {
                newUser.setBirthDate(Date.valueOf(date));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Enter a valid birthdate");
                alert.showAndWait();
                check = false;
            }

            try {
                if (check) {
                    System.out.println(newUser);
                    System.out.println("service " + service);
                    System.out.println(user.getStatus_id());
                    service.updateUser(newUser);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Data Updated Succsessfully");
                    alert.showAndWait();
                    Stage stage = (Stage) registerationAnchorPane.getScene().getWindow();
                    stage.close();
                }

            } catch (RemoteException ex) {
                Logger.getLogger(RegisterationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
       
           
           
           
           
    }    
    
}
