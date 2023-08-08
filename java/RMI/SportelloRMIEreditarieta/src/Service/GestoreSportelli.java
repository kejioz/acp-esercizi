package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GestoreSportelli extends Remote{

    public boolean sottoponiRichiesta(int idcliente) throws RemoteException;

    public void sottoscrivi(Sportello s) throws RemoteException;

}
