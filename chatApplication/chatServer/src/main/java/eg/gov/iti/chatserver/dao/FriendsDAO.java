/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.dao;

import eg.gov.iti.chatcommon.model.User;

/**
 *
 * @author ghazallah
 */
public interface FriendsDAO {
    
    public boolean isMyFriend (String myPhone,String friendPhone);
    
}
