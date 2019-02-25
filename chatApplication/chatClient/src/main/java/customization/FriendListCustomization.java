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
import eg.gov.iti.chatcommon.model.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.IOException;
import javafx.scene.Parent;
import javax.imageio.ImageIO;

public class FriendListCustomization extends ListCell<User> {

    private Node parent;
    private Circle profilePicCircle;
    private Circle statusCircle;
    private Label name;
    private Label status;
    private Image img;
    private Label msgCount;

    public FriendListCustomization() {
        try {
            parent = FXMLLoader.load(getClass().getResource("/fxml/ListItems.fxml"));
            profilePicCircle = (Circle) parent.lookup("#profilePicCircle");
            statusCircle = (Circle) parent.lookup("#statusCircle");
            name = (Label) parent.lookup("#name");
            status = (Label) parent.lookup("#status");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);

        Image image2 = null;
        if (!empty) {
            ByteArrayInputStream input = new ByteArrayInputStream(item.getPicture());
            BufferedImage image1;
            try {
                image1 = ImageIO.read(input);
                image2 = javafx.embed.swing.SwingFXUtils.toFXImage(image1, null);
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            profilePicCircle.setFill(new ImagePattern(image2));
            switch (item.getStatus_id()) {
                case 2:
                    statusCircle.setFill(Color.GREEN);
                    status.setText("Available");
                    name.setText(item.getName());
                    break;
                case 1:
                    statusCircle.setFill(Color.BROWN);
                    status.setText("Away");
                    name.setText(item.getName());
                    break;
                case 3:
                    statusCircle.setFill(Color.RED);
                    status.setText("Busy");
                    name.setText(item.getName());
                    break;
                case 4:
                    statusCircle.setFill(Color.GRAY);
                    status.setText("Offline");
                    name.setText(item.getName());

                    break;
                default:
                    break;

            }
            setGraphic(parent);
        } else {
            setGraphic(null);
        }

    }
    
    public Node getParentNode (){
        return parent;
    }

}
