/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.daoImplementation;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatserver.dao.FriendsDAO;
import eg.gov.iti.chatserver.database.DatabseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghazallah
 */
public class FriendsDAOImplementation implements FriendsDAO {

    @Override
    public boolean isMyFriend(String myPhone, String friendPhone) {

        boolean isMyfriend = false;

        String sql = "SELECT contact FROM chatapplication.friends where contact =? AND friendcontact =?";
        Connection connection = DatabseConnection.getConnecion();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, friendPhone);
            statement.setString(2, myPhone);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                isMyfriend = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendsDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isMyfriend;

    }
    
    public static void main(String[] args) {
        FriendsDAOImplementation friends = new FriendsDAOImplementation();
        boolean check = friends.isMyFriend("01002304909", "0111111111");
        System.out.println(check);
    }
}
