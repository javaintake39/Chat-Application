
package eg.gov.iti.chatserver.server;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.ClientInterface;
import eg.gov.iti.chatserver.dao.UserDAO;
import eg.gov.iti.chatserver.daoImplementation.UserDAOImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import eg.gov.iti.chatcommon.rmiconnection.ServerInterface;
import java.util.Hashtable;
import java.util.Map;


public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{
    private UserDAO userDAO;
    //map to carry phone as key for each online Client  (K,v)->(phone,ClientInterface)
    static Map<String, ClientInterface> clientsMap = new Hashtable<>();  

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
    public void sendMessage(String phone, String messageContent) throws RemoteException {
            clientsMap.get(phone).receive(messageContent);
    }

    @Override
    public User loginIn(User user,ClientInterface client) throws RemoteException {
        clientsMap.put(user.getPhoneNumber(), client);
        return userDAO.signIn(user);
    }
    
}
