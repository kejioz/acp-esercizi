package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Alert.AlertNotification;

public interface IManager extends Remote {
    
    public void subscribe(int idComponente, int porta) throws RemoteException ;

    public void sendNotification(AlertNotification alert) throws RemoteException ;

}
