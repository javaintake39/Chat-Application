/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author ahmed
 */
public class FXMLDocumentController implements Initializable {

    public FXMLDocumentController() {
    }

    @FXML
    private ImageView photoId;
    @FXML
    private Label nameId;
    @FXML
    private Label emailId;
    @FXML
    private Label passwordRegisterId;
    @FXML
    private Label confirmPasswordId;
    @FXML
    private Label genderId;
    @FXML
    private Label dateOfBirthId;
    @FXML
    private Label countryId;
    @FXML
    private DatePicker dateId;
    @FXML
    private ChoiceBox countryChoiceBox;
    @FXML
    private Button registerButton;
    @FXML
    private Label bioId;
    @FXML
    private TextArea bioTxtArea;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField passwordTxtField;
    @FXML
    private TextField confirmPasswordTxtField;
    @FXML
    private TextField phoneId;
//    @FXML
//    private void buttonHandeledEvent(ActionEvent event) {
//        String name = nameId.getText();
//        String email;
//        if (validateEmail(emailId.getText())) {
//            email = emailId.getText();
//        } else {
//            email = null;
//        }
//        
//        
//        if (validatePassword(passwordTxtField.getText())) {
//            String pasword = passwordTxtField.getText();
//            if (passwordTxtField.getText() == confirmPasswordTxtField.getText()) {
//                String connfirm = confirmPasswordTxtField.getText();
//            }
//        }
//  
//    }

    public static boolean validatePassword(String password) {
        if (password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")) {
            return true;
        }
        return false;
    }

    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{11}")) {
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("done");
                
                if(validatePhoneNumber(phoneId.getText()))
                {
                    String phone = phoneId.getText();
                }
                else{
                    System.out.println("invalid phone");
                }
                String name = nameId.getText();
                String email;
                if (validateEmail(emailId.getText())) {
                    email = emailId.getText();
                } else {
                    System.out.println("invlaid email");
                }

                if (validatePassword(passwordTxtField.getText())) {
                    if (passwordTxtField.getText() == confirmPasswordTxtField.getText()) {
                        String connfirm = confirmPasswordTxtField.getText();
                        String pasword = passwordTxtField.getText();
                    }
                } else {
                    System.out.println("invalid password");

                }
            }

        });

    }

}
