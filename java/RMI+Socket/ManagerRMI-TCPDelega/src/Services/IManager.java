package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import AlertNotification.*;

public interface IManager extends Remote {
    
    public void sendNotification (AlertNotification alert) throws RemoteException;

    public void subscribe (int idComponente, int porta) throws RemoteException;


}
