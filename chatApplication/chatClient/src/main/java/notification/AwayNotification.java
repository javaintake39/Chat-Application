/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notification;

import eg.gov.iti.chatcommon.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author ghazallah
 */
  public class AwayNotification extends Thread {
      private User user;

    public AwayNotification(User user) {
        this.user = user;
    }
      
      

        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Notifications notificationBuilder = Notifications.create()
                    .title("notification")
                    .text(user.getName() +" becomes Away")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                        }

                    });
            notificationBuilder.darkStyle();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    notificationBuilder.show();
                }
            });

        }
    }
