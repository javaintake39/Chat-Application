/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.daoImplementation;

import eg.gov.iti.chatserver.dao.InvitationDAO;
import eg.gov.iti.chatserver.database.DatabseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghazallah
 */
public class InvitationDAOImplementation implements InvitationDAO{

    // not tested yet
    @Override
    public void addFriends(String requesterPhone, List<String> phoneNumbers) {
        
        Connection connection = DatabseConnection.getConnecion();
        try {
            for (String element : phoneNumbers){
                String sql = "insert into Invitation values (?,?)";
                
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, requesterPhone);
                statement.setString(2, element);
                statement.executeUpdate();
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvitationDAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 }
