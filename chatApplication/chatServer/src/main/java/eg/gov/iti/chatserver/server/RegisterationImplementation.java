/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatserver.server;

import eg.gov.iti.chatcommon.model.User;
import eg.gov.iti.chatcommon.rmiconnection.RegisterationInterface;
import eg.gov.iti.chatserver.dao.UserDAO;
import eg.gov.iti.chatserver.daoImplementation.UserDAOImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ghazallah
 */
public class RegisterationImplementation extends UnicastRemoteObject implements RegisterationInterface{

    public RegisterationImplementation() throws RemoteException {
    }

    
    @Override
    public void registerNewUser(User user) throws RemoteException {
        UserDAO userDAO = new UserDAOImplementation();
        userDAO.registerNewUser(user);
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        UserDAO userDAO = new UserDAOImplementation();
        userDAO.updateUser(user);
    }


    
    @Override
    public User getUser(String phoneNumber) throws RemoteException {
        UserDAO userDAO = new UserDAOImplementation();
        userDAO.getUser(phoneNumber);
    }
    
}
