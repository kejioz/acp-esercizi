package Manager;

import java.rmi.Remote;
import java.rmi.RemoteException;

import AlertNotification.AlertNotification;

public interface IManager extends Remote {
    
    public void sendNotification(AlertNotification alert) throws RemoteException;

    public void subscribe (int componentID, int porta) throws RemoteException;

}
