/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ghazallah
 */
public class ChatBox implements Initializable {

    @FXML
    private VBox chatBox;
    @FXML
    private ListView<?> listviewChat;
    @FXML
    private FlowPane editPane;
    @FXML
    private JFXComboBox<?> fontComboBox;
    @FXML
    private JFXComboBox<?> fontSizeComboBox;
    @FXML
    private JFXColorPicker colorPickedCombo;
    @FXML
    private ToggleButton boldToggleBtn;
    @FXML
    private ToggleButton italicTogglebtn;
    @FXML
    private ToggleButton lineToggleBtn;
    @FXML
    private JFXTextField txtFieldMsg;
    @FXML
    private JFXButton btnsave;
    @FXML
    private JFXToggleButton btnChatBoot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSaveClicked(ActionEvent event) {
    }

    @FXML
    private void onAttachmentClicked(ActionEvent event) {
    }
    
}
