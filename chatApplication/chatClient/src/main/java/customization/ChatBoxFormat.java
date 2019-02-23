/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customization;

/**
 *
 * @author ghazallah
 */
import MyMessage.SingleMessage;
import eg.gov.iti.chatcommon.model.Message;
import fxmlControllers.HomeScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
//import model.ClientObject;
//import view.controller.MainController;

import java.io.IOException;

public class ChatBoxFormat extends ListCell<SingleMessage> {

    private Node usedParent;
    Text msgText;
    Text senderLetter;

    public ChatBoxFormat() {

        try {

            FXMLLoader.load(getClass().getResource("/fxml/LeftChatItem.fxml"));
            FXMLLoader.load(getClass().getResource("/fxml/RightChatItem.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(SingleMessage item, boolean empty) {
        super.updateItem(item, empty);
        try {
            if (!empty) {
                    String userName = HomeScreenController.service.getName(item.getFrom());
                if (item.getFrom().equals(HomeScreenController.user.getPhoneNumber())) {
                    usedParent = getCurrentParent(true);
                } else {
                    usedParent = getCurrentParent(false);
                }
                initComponent(usedParent);
                FontWeight fontWeight = item.isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
                FontPosture fontPosture = item.isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR;
                msgText.setFont(Font.font(item.getFontfamily(), fontWeight, fontPosture, Integer.valueOf(item.getFontsize())));
                msgText.setText(item.getContent());
                msgText.setStyle("-fx-fill: " + item.getColor());
                senderLetter.setText(userName.substring(0, 1).toUpperCase());
                setGraphic(usedParent);
                this.setStyle("-fx-background-color: white");
            } else {
                setGraphic(null);
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private Node getCurrentParent(boolean flag) throws IOException {

        if (flag) {
            return FXMLLoader.load(getClass().getResource("/fxml/RightChatItem.fxml"));
        } else {
            return FXMLLoader.load(getClass().getResource("/fxml/LeftChatItem.fxml"));
        }

    }

    private void initComponent(Node usedParent) {
        msgText = (Text) usedParent.lookup("#msgText");
        senderLetter = (Text) usedParent.lookup("#senderLetter");
    }
}
