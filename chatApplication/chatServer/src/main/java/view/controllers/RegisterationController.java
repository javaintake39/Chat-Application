/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

/**
 *
 * @author ghazallah
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import eg.gov.iti.chatserver.dao.UserDAO;
import eg.gov.iti.chatserver.daoImplementation.UserDAOImplementation;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import utilities.Validation;

/**
 *
 * @author pc
 */
public class RegisterationController implements Initializable {

    ObservableList<String> genderList;
    ObservableList<String> countryList;
    private ServerInterface service;
    @FXML
    private AnchorPane registerationAnchorPane;

     UserDAO user;
    public RegisterationController() {
        user=new UserDAOImplementation();
    }

    
    @FXML
    private ImageView photoId;
    @FXML
    private JFXButton choosePictureButton;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXTextField nameTxtField;
    @FXML
    private JFXTextField phoneId;
    @FXML
    private JFXTextField emailTxtField;
    @FXML
    private JFXPasswordField passwordTxtField;
    @FXML
    private JFXPasswordField confirmPasswordTxtField;
    @FXML
    private JFXDatePicker dateOfBirthId;
    @FXML
    private JFXComboBox genderComboBox;
    @FXML
    private JFXComboBox countryComboBox;
    @FXML
    private JFXTextArea bioId;

    private Image imageFileMarsh;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderList = FXCollections.observableArrayList("Male", "Female");
        countryList = FXCollections.observableArrayList("Australia","Bolivia","Bosnia","Brazil","Egypt","USA","Russia","Qatar"
        +"China","Colombia","Croatia","Denmark","France","Germany","Greece");
        genderComboBox.setItems(genderList);
        genderComboBox.setVisibleRowCount(2);
        genderComboBox.setEditable(true);
        countryComboBox.setItems(countryList);
        countryComboBox.setVisibleRowCount(10);
        countryComboBox.setEditable(true);
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
        }
        );

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
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You didn't select image : please select image");
                    alert.showAndWait();
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
                alert.setContentText("Please Enter a country");
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

            
                if (check) {
                    
                    user.registerNewUser(newUser);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Registered Succsessfull");
                    alert.showAndWait();
                    Stage stage = (Stage) registerationAnchorPane.getScene().getWindow();
                    stage.close();
                }

            

        });

    }

}
