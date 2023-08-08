package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote{
    
    public void notifyObserver() throws RemoteException ;

    public void notifyReading() throws RemoteException;

    public String getTipo() throws RemoteException;

}
