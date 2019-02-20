/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.dao;

import java.util.List;

/**
 *
 * @author ghazallah
 */
public interface InvitationDAO {
    public void addFriends (String requesterPhone,List <String> phoneNumbers);
    
}
