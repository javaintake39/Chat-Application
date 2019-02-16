package view;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public  class LoginBase extends AnchorPane {

    public final TextField textField;
    public final Button button;
    public final ListView listView;
    public final ImageView imageView;
    public final TextArea textArea;

    public LoginBase() {

        textField = new TextField();
        button = new Button();
        listView = new ListView();
        imageView = new ImageView();
        textArea = new TextArea();

        setId("AnchorPane");
        setPrefHeight(535.0);
        setPrefWidth(745.0);

        textField.setLayoutX(231.0);
        textField.setLayoutY(484.0);
        textField.setPrefHeight(37.0);
        textField.setPrefWidth(360.0);

        button.setLayoutX(625.0);
        button.setLayoutY(484.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(37.0);
        button.setPrefWidth(91.0);
        button.setText("Send");

        listView.setLayoutY(67.0);
        listView.setPrefHeight(465.0);
        listView.setPrefWidth(214.0);

        imageView.setFitHeight(37.0);
        imageView.setFitWidth(45.0);
        imageView.setLayoutX(14.0);
        imageView.setLayoutY(23.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
//        imageView.setImage(new Image(getClass().getResource("../../../../../Downloads/iconfinder_Avatar_Famous_Characters-06_2612538%20(1).png").toExternalForm()));

        textArea.setLayoutX(231.0);
        textArea.setLayoutY(67.0);
        textArea.setPrefHeight(405.0);
        textArea.setPrefWidth(507.0);

        getChildren().add(textField);
        getChildren().add(button);
        getChildren().add(listView);
        getChildren().add(imageView);
        getChildren().add(textArea);

    }
}
