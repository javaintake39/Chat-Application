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
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class FriendListCustomization extends ListCell<User> {


    private Node parent ;
    private Circle profilePicCircle;
    private Circle statusCircle;
    private Label name;
    private Label status;
    private Image img ;
    private Label msgCount;
   // private MainController mainController;
    public FriendListCustomization()
    {

        //this.mainController = mainController;

        try {
            parent = FXMLLoader.load(getClass().getResource("/fxml/ListItems.fxml"));
            profilePicCircle = (Circle)parent.lookup("#profilePicCircle");
            statusCircle = (Circle)parent.lookup("#statusCircle");
            name = (Label)parent.lookup("#name");
            status = (Label)parent.lookup("#status");

        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);

        Image image2 = null;
        if(!empty)
        {
             ByteArrayInputStream input = new ByteArrayInputStream(item.getPicture());
        BufferedImage image1;
        try {
            image1 = ImageIO.read(input);
             image2 = javafx.embed.swing.SwingFXUtils.toFXImage(image1, null);
        } catch (IOException ex) {
            ex.printStackTrace();
          
        }
            //img = image2;
            profilePicCircle.setFill(new ImagePattern(image2));
            if(item.getStatus_id()==1)
                statusCircle.setFill(Color.GREEN);
            
            else
                statusCircle.setFill(Color.GRAY);
            name.setText(item.getName());
            status.setText(item.getStatus_id()==1?"Available":"busy");
            setGraphic(parent);
       //     parent.setOnMouseClicked(param->{
//                try {
//                    mainController.setSingleChatRoom(item.getUsername());
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            });
        }
        else
        {
            setGraphic(null);
        }


    }


}
