/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ghazallah
 */
public class MainChatController implements Initializable {

    @FXML
    private Pane chatHeader;
    @FXML
    private Circle profilePicCircle;
    @FXML
    private ComboBox<?> userStatus;
    @FXML
    private TextArea announceArea1;
    @FXML
    private VBox ChatArea;
    @FXML
    private ListView<?> chatBoxListVIew;
    @FXML
    private ComboBox<?> fontList;
    @FXML
    private ImageView sendFileBtn;
    @FXML
    private ImageView bold;
    @FXML
    private ImageView italic;
    @FXML
    private ComboBox<?> sizeList;
    @FXML
    private ColorPicker fontColorPicker;
    @FXML
    private ImageView saveChat;
    @FXML
    private TextField chatField;
    @FXML
    private ImageView sendBtn;
    @FXML
    private ImageView searchBtn;
    @FXML
    private ListView<?> friendsListView;
    @FXML
    private ListView<?> chatGroupsList;
    @FXML
    private ListView<?> reqFriendsListView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void sendBtn(ActionEvent event) {
    }

    @FXML
    private void sendBtn(MouseEvent event) {
    }

    @FXML
    private void searchBtn(MouseEvent event) {
    }
    
}
