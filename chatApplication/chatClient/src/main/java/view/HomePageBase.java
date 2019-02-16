package view;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HomePageBase extends AnchorPane {

    public final AnchorPane anchorPane;
    public final TextField txtPhone;
    public final PasswordField txtPassword;
    public final Button btnLogin;
    public final Button btnSignUp;

    public HomePageBase() {

        anchorPane = new AnchorPane();
        txtPhone = new TextField();
        txtPassword = new PasswordField();
        btnLogin = new Button();
        btnSignUp = new Button();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(700.0);
        setStyle("-fx-background-color: #586A79;");

        anchorPane.setLayoutX(387.0);
        anchorPane.setLayoutY(75.0);
        anchorPane.setPrefHeight(296.0);
        anchorPane.setPrefWidth(275.0);
        anchorPane.setStyle("-fx-background-color: #655A51;");

        txtPhone.setLayoutX(18.0);
        txtPhone.setLayoutY(119.0);
        txtPhone.setPrefHeight(29.0);
        txtPhone.setPrefWidth(240.0);
        txtPhone.setPromptText("Phone Number");

        txtPassword.setLayoutX(22.0);
        txtPassword.setLayoutY(169.0);
        txtPassword.setPrefHeight(29.0);
        txtPassword.setPrefWidth(240.0);
        txtPassword.setPromptText("Password");

        btnLogin.setLayoutX(22.0);
        btnLogin.setLayoutY(237.0);
        btnLogin.setMnemonicParsing(false);
        btnLogin.setPrefHeight(25.0);
        btnLogin.setPrefWidth(116.0);
        btnLogin.setText("Login");

        btnSignUp.setLayoutX(146.0);
        btnSignUp.setLayoutY(237.0);
        btnSignUp.setMnemonicParsing(false);
        btnSignUp.setPrefHeight(25.0);
        btnSignUp.setPrefWidth(116.0);
        btnSignUp.setText("Sign Up");

        anchorPane.getChildren().add(txtPhone);
        anchorPane.getChildren().add(txtPassword);
        anchorPane.getChildren().add(btnLogin);
        anchorPane.getChildren().add(btnSignUp);
        getChildren().add(anchorPane);
        
        
        

    }
}
