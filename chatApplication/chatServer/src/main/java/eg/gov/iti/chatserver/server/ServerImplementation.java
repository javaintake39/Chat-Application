
package eg.gov.iti.chatserver.server;

import eg.gov.iti.chatcommon.model.Message;
import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatserver.dao.UserDAO;
import eg.gov.iti.chatserver.daoImplementation.UserDAOImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import eg.gov.iti.chatserver.dao.FriendsDAO;
import eg.gov.iti.chatserver.daoImplementation.FriendsDAOImplementation;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{
    private UserDAO userDAO;
    
    //map to carry phone as key for each online Client  (K,v)->(phone,ClientInterface)
    //online users
    public static Map<String, ClientInterface> clientsMap = new Hashtable<>();  

    public ServerImplementation() throws RemoteException {
        userDAO = new UserDAOImplementation();
    }

    @Override
    public void registerNewUser(User user) throws RemoteException {
      
        userDAO.registerNewUser(user);
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        userDAO.updateUser(user);
    }
    @Override
    public User getUser(String phoneNumber) throws RemoteException {
        return userDAO.getUser(phoneNumber);
    }

    @Override
    public List<User> getUserFriends(User user) {
        return userDAO.getUserFriends(user.getPhoneNumber());
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
            clientsMap.get(message.getTo()).receive(message);
    }

    @Override
    public User login(User user,ClientInterface client) throws RemoteException {
        clientsMap.put(user.getPhoneNumber(), client);
        clientsMap.forEach((phone,clientInt)->{
            try {
                clientInt.loginNotification(user);
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return userDAO.signIn(user);
    }
     @Override
    public void logout(User user) throws RemoteException {
         clientsMap.remove(user.getPhoneNumber());
         clientsMap.forEach((phone,clientInt)->{
            try {
                clientInt.logoutNotification(user);
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }

    @Override
    public void sendAnnouncement(String announcement) throws RemoteException {
        clientsMap.forEach((phone,onlineClient)->{
            try {
                onlineClient.recieveAnnouncement(announcement);
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
      
    }

    @Override
    public void setStatus(User user) throws RemoteException {
        
        
    }

    @Override
    public boolean isMyFriend(String myPhone, String friendContact) throws RemoteException {
        
        FriendsDAO friendsDao = new FriendsDAOImplementation();
        return friendsDao.isMyFriend(myPhone, friendContact);
    }

   
    
}
