package magazzino;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Magazzino extends Remote {
    
    public void deposita(int a) throws RemoteException;

    public int preleva() throws RemoteException;

}
