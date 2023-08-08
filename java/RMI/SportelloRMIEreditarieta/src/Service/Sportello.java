package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sportello extends Remote{
    
    public boolean serviRichiesta(int idcliente) throws RemoteException;

}
