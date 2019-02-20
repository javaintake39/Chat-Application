/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ChatBoxController implements Initializable {

    @FXML
    private TextField txtFieldMsg;
    @FXML
    private Button btnSendEmail;
    @FXML
    private Image clips1;
    @FXML
    private Button saveBtn;
    @FXML
    private Image clips;
    @FXML
    private FlowPane editPane;
    @FXML
    private ComboBox<?> fontComboBox;
    @FXML
    private ComboBox<?> fontSizeComboBox;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ToggleButton boldToggleBtn;
    @FXML
    private ToggleButton italicTogglebtn;
    @FXML
    private ToggleButton lineToggleBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void txtFieldOnKeyPressed(KeyEvent event) {
    }

    @FXML
    private void btnSendEmailAction(ActionEvent event) {
    }
    
}
